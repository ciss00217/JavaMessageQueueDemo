package tw.com.consumer;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.rabbitmq.jms.admin.RMQConnectionFactory;

public class ConsumerMessage {
	private final static String TEST_QUEUE_NAME = "KevinTestQueue1";
	
	private MessageConsumer messageConsumer;
	private Connection connection;
	private Destination destination;
	

	public void start(Session session, Destination destination,Connection connection) {

		try {
			connection.start();
			
			ConsumerMessageListener consumerMessageListener = new ConsumerMessageListener();

			MessageConsumer messageConsumer = session.createConsumer(destination);

			messageConsumer.setMessageListener(consumerMessageListener);
		} catch (Exception e) {

		}
	}
	
	public void createConsumer(Session session, Destination destination,Connection connection) {

		try {
			connection.start();
			
			ConsumerMessageListener consumerMessageListener = new ConsumerMessageListener();

			MessageConsumer messageConsumer = session.createConsumer(destination);

			messageConsumer.setMessageListener(consumerMessageListener);
		} catch (Exception e) {

		}
	}
	
	
	public class ConsumerMessageListener extends Thread implements MessageListener {

		public void onMessage(Message message) {

			TextMessage textMsg = (TextMessage) message;

			try {
				System.out.println("消息內容是：" + textMsg.getText());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			try {
	            connectionFactory = new ActiveMQConnectionFactory(AMQ_USER, AMQ_PASS, url);
	            connection = connectionFactory.createConnection();
	            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	            queue = session.createQueue(QUEUE_NAME);
	            consumer = session.createConsumer(queue);
	            consumer.setMessageListener(this);
	            connection.start();
	        } catch (NamingException nex) {
	            log.error("Error in run()", nex);
	        } catch (JMSException jex) {
	            log.error("Error in run()", jex);
	        }

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

			messageConsumer.setMessageListener(consumerMessageListener);

		} catch (Exception e) {

			System.out.println("ERROR:" + e.getMessage());
		}

	}

}


