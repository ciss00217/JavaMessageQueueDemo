package tw.com.client;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rabbitmq.jms.admin.RMQConnectionFactory;
import com.sun.jndi.fscontext.RefFSContextFactory;

import tw.com.consumer.ConsumerMessageListener;

public class JavaMessageService {
	private final static String QUEUE_NAME = "RobinQueue";
	private final static String TEST_QUEUE_NAME = "Kevin1";
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

			Destination destination = (Destination) context.getBean("jmsDestination");
			
			MessageConsumer messageConsumer = session.createConsumer(destination);

			ConsumerMessageListener consumerMessageListener = new ConsumerMessageListener();

			messageConsumer.setMessageListener(consumerMessageListener);

			
			MessageProducer producer = session.createProducer(destination);
			TextMessage textMessage = session.createTextMessage();
			textMessage.setText("Hello!JMS!");
			producer.send(textMessage);

			// cache-modeCHANNEL

			System.out.println("執行成功");
		} catch (Exception e) {

			System.out.println("ERROR:" + e.getMessage());
		}

	}

}
