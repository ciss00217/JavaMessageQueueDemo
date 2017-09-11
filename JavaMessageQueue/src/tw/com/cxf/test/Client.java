package tw.com.cxf.test;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

public class Client {
	public static void main(String[] args) {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(HelloWorld.class);
		factory.setAddress("http://localhost:8888/ws/HelloWorld");
		HelloWorld helloworld = (HelloWorld) factory.create();
		System.out.println(helloworld.sayHi("kongxx"));
		System.exit(0);
	}
}
