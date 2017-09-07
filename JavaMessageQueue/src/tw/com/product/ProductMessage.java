package tw.com.product;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rabbitmq.jms.admin.RMQConnectionFactory;

import tw.com.client.JavaMessageService;

public class ProductMessage {
	private final static String TEST_QUEUE_NAME = "Ian";


	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		try {

			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

			RMQConnectionFactory rMQConnectionFactory = (RMQConnectionFactory) context.getBean("jmsConnectionFactory");

			Connection connection = rMQConnectionFactory.createConnection();

			connection.start();

			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			Destination destination = session.createQueue(TEST_QUEUE_NAME);

			MessageProducer producer = session.createProducer(destination);

			TextMessage textMessage = session.createTextMessage();
			for (int i = 0; i < 10; i++) {
				textMessage.setText("Hello!JMS!" + i);
				producer.send(textMessage);
			}
			// cache-modeCHANNEL

			System.out.println("執行成功");
		} catch (Exception e) {

			System.out.println("ERROR:" + e.getMessage());
		}

	}

}
