package tw.com.client;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

public class MyConnectionFactory implements ConnectionFactory {
	

	@Override
	public Connection createConnection() throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Connection createConnection(String userName, String password) throws JMSException {
		// TODO Auto-generated method stub
		return null;
	}

}
