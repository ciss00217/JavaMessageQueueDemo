package tw.com.client;

public class HelloWorld {
	private String message;

	public void getMessage() {
		System.out.println("你的訊息: " + message);
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
