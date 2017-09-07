package tw.com.consumer;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rabbitmq.jms.admin.RMQConnectionFactory;

public class ConsumerMessage {
	private static final Logger logger = LogManager.getLogger(ConsumerMessage.class);
	private final static String TEST_QUEUE_NAME = "Kevin";

	public void start(Session session, Destination destination, Connection connection) {

		try {
			connection.start();

			ConsumerMessageListener11 consumerMessageListener = new ConsumerMessageListener11();

			MessageConsumer messageConsumer = session.createConsumer(destination);

			messageConsumer.setMessageListener(consumerMessageListener);
		} catch (Exception e) {

		}
	}

	public void createConsumer(Session session, Destination destination, Connection connection) {

		try {
			connection.start();

			ConsumerMessageListener consumerMessageListener = new ConsumerMessageListener();

			MessageConsumer messageConsumer = session.createConsumer(destination);

			messageConsumer.setMessageListener(consumerMessageListener);
		} catch (Exception e) {

		}
	}

	public static void main(String[] args) throws Exception {
		try {

			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

			ConnectionFactory rMQConnectionFactory = (RMQConnectionFactory) context.getBean("jmsConnectionFactory");

			Connection connection = rMQConnectionFactory.createConnection();

			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			Destination destination = session.createQueue(TEST_QUEUE_NAME);

			MessageConsumer messageConsumer = session.createConsumer(destination);

			ConsumerMessageListener consumerMessageListener = new ConsumerMessageListener();

			
			//  messageConsumer.setMessageListener(consumerMessageListener);
			connection.start();
			messageConsumer.setMessageListener(consumerMessageListener);
			

		} catch (Exception e) {

			System.out.println("ERROR:" + e.getMessage());
		}

	}

}
