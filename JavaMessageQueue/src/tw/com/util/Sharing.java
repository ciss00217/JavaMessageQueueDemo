package tw.com.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import tw.com.client.JavaMessageService;

public class Sharing {
	static final String QUEUE_NAME = "RobinQueue";
	static final String TEST_QUEUE_NAME = "Kevin1";
	static final Log logger = LogFactory.getLog(JavaMessageService.class);
	static final String USERNAME = "admin";
	static final String PASSWORD = "password";
	static final String HOSTNAME = "192.168.112.199:5672";
	static final int PORTNAME = 5672;
}
