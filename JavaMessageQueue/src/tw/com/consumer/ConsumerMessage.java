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

public class ConsumerMessage {
	private static final Logger logger = LogManager.getLogger(ConsumerMessage.class);

	public void start(Session session, Destination destination, Connection connection) {

		try {

			MessageConsumer messageConsumer = session.createConsumer(destination);

			ConsumerMessageListener consumerMessageListener = new ConsumerMessageListener(messageConsumer);

			connection.start();
			consumerMessageListener.start();

		} catch (Exception e) {

			logger.debug("ERROR:" + e.getMessage());
		}
	}

	public void createConsumer(Session session, Destination destination, Connection connection) {

		try {

			MessageConsumer messageConsumer = session.createConsumer(destination);
			ConsumerMessageListener consumerMessageListener = new ConsumerMessageListener(messageConsumer);

			messageConsumer.setMessageListener(consumerMessageListener);
			connection.start();
		} catch (Exception e) {

		}
	}

	public static void main(String[] args) throws Exception {
		try {

			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

			ConnectionFactory jmsConnectionFactory = (ConnectionFactory) context.getBean("jmsConnectionFactory");

			Destination jmsDestination = (Destination) context.getBean("jmsDestination");

			Connection connection = jmsConnectionFactory.createConnection();

			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			MessageConsumer messageConsumer = session.createConsumer(jmsDestination);

			ConsumerMessageListener consumerMessageListener = new ConsumerMessageListener(messageConsumer);

			connection.start();
			consumerMessageListener.start();

		} catch (Exception e) {

			logger.debug("ERROR:" + e.getMessage());
		}

	}

}
