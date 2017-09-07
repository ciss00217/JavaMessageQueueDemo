package tw.com.client;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rabbitmq.jms.admin.RMQConnectionFactory;

import tw.com.consumer.ConsumerMessageListener11;

public class JavaMessageService11 {
	private final static String QUEUE_NAME = "RobinQueue";
	private final static String TEST_QUEUE_NAME = "Kevin222";
	private static final Log logger = LogFactory.getLog(JavaMessageService.class);
	private static final String USERNAME = "admin";
	private static final String PASSWORD = "password";
	private static final String HOSTNAME = "192.168.112.199:5672";
	private static final int PORTNAME = 5672;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		try {

			ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

			RMQConnectionFactory rMQConnectionFactory = (RMQConnectionFactory) context.getBean("jmsConnectionFactory");

			Connection connection = rMQConnectionFactory.createConnection();
			
			connection.start();

			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			Destination destination = session.createQueue(TEST_QUEUE_NAME);

			MessageConsumer messageConsumer = session.createConsumer(destination);

			ConsumerMessageListener11 consumerMessageListener = new ConsumerMessageListener11();

			messageConsumer.setMessageListener(consumerMessageListener);

		/*	
			MessageProducer producer = session.createProducer(destination);
			TextMessage textMessage = session.createTextMessage();
			textMessage.setText("Hello!JMS!");
			producer.send(textMessage);*/

			// cache-modeCHANNEL

			System.out.println("執行成功");
		} catch (Exception e) {

			System.out.println("ERROR:" + e.getMessage());
		}

	}

}
