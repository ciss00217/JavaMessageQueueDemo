package tw.com.consumer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class ConsumerMessageListener implements MessageListener {

	public void onMessage(Message message) {
		// 這裡我們知道生產者發送的就是一個純文本消息，所以這裡可以直接進行強制轉換，或者直接把onMessage方法的參數改成Message的子類TextMessage
		TextMessage textMsg = (TextMessage) message;

		try {
			System.out.println("消息內容是：" + textMsg.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}