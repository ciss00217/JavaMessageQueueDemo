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
	private final static String TEST_QUEUE_NAME = "KevinReceive";

	private MessageConsumer messageConsumer;
	private Connection connection;
	private Destination destination;
	private ConnectionFactory ConnectionFactory;
	private ApplicationContext context;
	private Session session;
	private QueueBrowser queueBrowser;
	private Enumeration enumeration;

	public ConsumerMessageListener(MessageConsumer messageConsumer) {
		this.messageConsumer = messageConsumer;
	}

	public void onMessage(Message message) {

		TextMessage textMsg = (TextMessage) message;
		try {
			logger.debug("消息內容是：" + textMsg.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	private String poll() {
		String pollText = "";

		try {
			Message message = messageConsumer.receive();

			if (message instanceof TextMessage) {
				TextMessage m = (TextMessage) message;
				pollText = m.getText();
			}

		} catch (JMSException e) {
			logger.debug("receive error" + e.getMessage());
			/*try {
				session.rollback();
			} catch (JMSException e1) {
				logger.debug("rollback error" + e.getMessage());
			}*/
		}

		return pollText;
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
		
				System.out.println("Listener");
				init();

				queueBrowser = session.createBrowser((Queue) destination);

				enumeration = queueBrowser.getEnumeration();

				if (enumeration.hasMoreElements()) {
					Object msg = enumeration.nextElement();
					if (msg instanceof TextMessage) {
						TextMessage m = (TextMessage) msg;
						String text = m.getText();
						System.out.println("peek:" + text);
						logger.debug("peek:" + text);
					}

					String pollText = poll();
					System.out.println("poll:" + pollText);
					//logger.debug("poll:" + pollText);

				} else {
					try {
						sleep(3000);
					} catch (InterruptedException e) {
						System.out.println("sleep： error:" + e.getMessage());
						//logger.debug("sleep： error:" + e.getMessage());
					}

				}

			} catch (JMSException jex) {
				logger.debug("Error in run()", jex);
			}

		}

	}

}