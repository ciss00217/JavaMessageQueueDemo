package tw.com.client;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.AMQP.Queue.DeclareOk;
import com.rabbitmq.client.Channel;

public class Send {
	private  final  static String QUEUE_NAME ="RobinQueue";
	public static void main(String[] args) throws IOException, TimeoutException {
		// TODO Auto-generated method stub

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
		
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
	       
		DeclareOk declareOk=channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		String message = "Hello World!";
		channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
		System.out.println(" [x] Sent '" + message + "'");
		String queueName=declareOk.getQueue();
		int msSize=declareOk.getMessageCount();
		System.out.println("QueueName:"+queueName);
		System.out.println("msSize:"+msSize);
		
		

	}

}
