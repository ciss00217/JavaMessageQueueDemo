package test;

import com.rabbitmq.client.ConnectionFactory;

public class FirstGradle {

	private static final String RMQConnectionFactory = null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hello Gradle");
		
		String userName = "admin";
		String password = "password";
		String virtualHost = "";
		String hostName = "192.168.112.199";
		int portNumber = 5672;

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(hostName);
		factory.setPort(portNumber);
		factory.setUsername(userName);
		factory.setPassword(password);
		
		
	}

}
