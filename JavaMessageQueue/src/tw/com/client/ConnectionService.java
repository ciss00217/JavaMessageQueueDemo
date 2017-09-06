package tw.com.client;

import java.lang.reflect.InvocationTargetException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

public class ConnectionService {

	private final String implementation;
	private final String userName;
	private final String password;
	private final String url;
	private final String topicName;

	public ConnectionService(String implementation, String userName, String password, String url, String topicName) {
		this.implementation = implementation;
		this.userName = userName;
		this.password = password;
		this.url = url;
		this.topicName = topicName;
	}
	


	public ConnectionFactory  getConnectionFactory() throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, JMSException{
		
		return createAndGetConnection(this.implementation,this.userName,this.password,this.url);
	}
	
	@SuppressWarnings("unused")
	private static ConnectionFactory  createAndGetConnection(String implementation, String userName, String password,
			String url) throws JMSException, ClassNotFoundException, InstantiationException, IllegalAccessException,
					InvocationTargetException, NoSuchMethodException {

		@SuppressWarnings("unchecked")
		Class<ConnectionFactory> clazz = (Class<ConnectionFactory>) ConnectionService.class.getClassLoader()
				.loadClass(implementation);

		ConnectionFactory connectionFactory = clazz.getConstructor(String.class, String.class, String.class)
				.newInstance(userName, password, url);

		return connectionFactory;
	}

}
