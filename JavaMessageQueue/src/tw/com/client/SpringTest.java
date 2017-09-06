package tw.com.client;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.rabbitmq.jms.admin.RMQConnectionFactory;

public class SpringTest {

	public static void main(String[] args) throws NamingException, JMSException {

		// 設定 JNDI 屬性
		Properties properties = new Properties();

		properties.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.rabbitmq.jms.admin.RMQConnectionFactory");
		properties.setProperty("username", "admin");
		properties.setProperty("password", "password");
		properties.setProperty("host", "192.168.112.199");
		properties.setProperty("port", "5672");

		// 建立 Context
		Context ctx = new InitialContext(properties);

		RMQConnectionFactory rMQConnectionFactory = (RMQConnectionFactory) ctx.lookup("RMQConnectionFactory");
		
		Connection connection = rMQConnectionFactory.createConnection();
		
		
		connection.start();
	
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		Destination destination = session.createQueue("Kevin1");
		
		MessageProducer producer = session.createProducer(destination);
		TextMessage textMessage = session.createTextMessage();
		textMessage.setText("Hello!JMS!");
		producer.send(textMessage);

	}

}
