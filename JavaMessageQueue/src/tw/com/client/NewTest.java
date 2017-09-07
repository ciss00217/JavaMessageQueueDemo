package tw.com.client;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class NewTest {
	private  final  static String TASK_QUEUE_NAME ="KevinTestQueue1";
	public static void main(String[] args) throws IOException, TimeoutException {
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


	        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
	        //	      分发消息
	        for(int i = 0 ; i < 5; i++){
	            String message = "Hello World! " + i;
	            channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
	            System.out.println(" Send '" + message + "'");
	        }
	        channel.close();
	        connection.close();
	    }
	}


