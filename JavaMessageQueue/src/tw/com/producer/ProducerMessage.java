package tw.com.producer;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ProducerMessage {
	private static final Logger logger = LogManager.getLogger(ProducerMessage.class);

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		try {

			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

			Destination rmqDestination = (Destination) context.getBean("jmsDestination");

			ConnectionFactory connectionFactory = (ConnectionFactory) context.getBean("jmsConnectionFactory");

			Connection connection = connectionFactory.createConnection();

			connection.start();

			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			MessageProducer producer = session.createProducer(rmqDestination);

			TextMessage textMessage = session.createTextMessage();

			for (int i = 1; i <= 100; i++) {
				textMessage.setText("Hello!JMS!" + i);
				producer.send(textMessage);
			}

			logger.debug("執行成功");
		} catch (Exception e) {
			logger.debug("ERROR:" + e.getMessage());
		}

	}

}
