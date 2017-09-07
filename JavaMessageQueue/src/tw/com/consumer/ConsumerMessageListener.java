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
import javax.jms.TextMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rabbitmq.jms.admin.RMQConnectionFactory;

public class ConsumerMessageListener extends Thread implements MessageListener {
	private static final Logger logger = LogManager.getLogger(ConsumerMessageListener.class);
	private final static String TEST_QUEUE_NAME = "Kevin";

	private MessageConsumer messageConsumer;
	private Connection connection;
	private Destination destination;
	private ConnectionFactory ConnectionFactory;
	private ApplicationContext context;
	private Session session;
	private QueueBrowser queueBrowser;
	private Enumeration enumeration;

	public void onMessage(Message message) {

		TextMessage textMsg = (TextMessage) message;
		try {
			logger.debug("消息內容是：" + textMsg.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	private String poll() {

		return null;
	}

	
	private void init() throws JMSException {

		if (context == null) {
			context = new ClassPathXmlApplicationContext("Beans.xml");
		}
		if (ConnectionFactory == null) {
			ConnectionFactory = (RMQConnectionFactory) context.getBean("jmsConnectionFactory");
		}
		if (connection == null) {
			connection = ConnectionFactory.createConnection();
		}
		connection.start();

		if (session == null) {
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		}
		if (destination == null) {
			destination = session.createQueue(TEST_QUEUE_NAME);
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				logger.debug("Listener");

				init();

				queueBrowser = session.createBrowser((Queue) destination);

				enumeration = queueBrowser.getEnumeration();

				if (enumeration.hasMoreElements()) {
					Object msg = enumeration.nextElement();
					if (msg instanceof TextMessage) {
						TextMessage m = (TextMessage) msg;
						String text = m.getText();
						logger.debug("peek:" + text);
					}
					
					
					
				} else {
					try {
						sleep(3000);
					} catch (InterruptedException e) {
						logger.debug("sleep： error:" + e.getMessage());
					}

				}

			} catch (JMSException jex) {
				logger.debug("Error in run()", jex);
			}

		}

	}

}