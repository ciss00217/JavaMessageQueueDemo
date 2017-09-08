package tw.com.consumer;

import java.util.Enumeration;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import tw.com.jms.util.Util;

public class ConsumerMessageListener extends Thread implements MessageListener {
	private static final Logger logger = LogManager.getLogger(ConsumerMessageListener.class);

	private MessageConsumer messageConsumer;
	private Connection connection;
	private Destination destination;
	private ConnectionFactory connectionFactory;
	private ApplicationContext context;
	private Session session;
	private QueueBrowser queueBrowser;
	private Enumeration enumeration;

	public ConsumerMessageListener(MessageConsumer messageConsumer) {
		try {
			init();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.messageConsumer = messageConsumer;
	}

	@Override
	public void onMessage(Message message) {

		String text = "";
		try {
			text = Util.convertMsg(message);
		} catch (JMSException e) {
			e.printStackTrace();
		}
		logger.debug("消息內容:" + text);
	}

	private String poll() {
		String pollText = "";

		try {
			Message message = messageConsumer.receive();

			pollText = Util.convertMsg(message);

		} catch (Exception e) {
			logger.debug("receive error" + e.getMessage());

			/*
			 * try { session.rollback(); } catch (JMSException e1) {
			 * logger.debug("rollback error" + e.getMessage()); }
			 */
		}

		return pollText;
	}

	private void init() throws JMSException {

		if (context == null) {
			context = new ClassPathXmlApplicationContext("Beans.xml");
		}
		if (connectionFactory == null) {
			connectionFactory = (ConnectionFactory) context.getBean("jmsConnectionFactory");
		}
		if (connection == null) {
			connection = connectionFactory.createConnection();
			connection.start();
		}

		if (session == null) {
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		}

		if (destination == null) {
			destination = (Destination) context.getBean("jmsDestination");
		}

	}

	@Override
	public void run() {
		String text;

		while (true) {
			try {
				logger.debug("Listener");

				queueBrowser = session.createBrowser((Queue) destination);

				enumeration = queueBrowser.getEnumeration();

				if (enumeration.hasMoreElements()) {
					Message message = (Message) enumeration.nextElement();

					text = Util.convertMsg(message);
					logger.debug("peek:" + text);

					String pollText = poll();
					logger.debug("poll:" + pollText);

				} else {
					try {
						sleep(3000);
					} catch (InterruptedException e) {
						logger.debug("sleep： error:" + e.getMessage());
					}

				}

			} catch (JMSException jex) {
				logger.debug("Error in run()", jex);
			} catch (NullPointerException e2) {

				try {
					init();
				} catch (JMSException e) {
					e.printStackTrace();
					logger.debug("Error JMSException reStart", e.getMessage());
				}

				logger.debug("NullPointerException reinit error:", e2.getMessage());
			}

		}

	}

}