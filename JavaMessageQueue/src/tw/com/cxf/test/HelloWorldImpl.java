package tw.com.cxf.test;

import javax.jws.WebService;

@WebService
public class HelloWorldImpl implements HelloWorld{
	@Override
	public String sayHi(String text) {
		   System.out.println(" sayHello is called.");         
	        return "我正在測試中:" + text +" !";
	}
}
