package rabbitMQTest.routingKey;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/** 
 * @author  �  
 * @date ����ʱ�䣺2017��11��15�� ����2:27:35 
 * @version 1.0 
*/
public class RoutingSendDrirect {

	public static final String EXCHANGE_NAME = "direct_log";
	//·�ɹؼ���
	public static final String [] routingKeys = new String[]{"debug", "error", "info", "warning"};
	
	public static void main(String[] args) throws IOException, TimeoutException {
		
		ConnectionFactory factory  = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		//����������
		channel.exchangeDeclare(EXCHANGE_NAME, "direct");
		
		for(String routingKey : routingKeys){
			
			for(int i=0; i < 100; i ++){
				
				String message = "hello direct " + routingKey;
				channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes());
				System.out.println("RoutingSendDrirect Send Message: " + message);
			}
		}
		
		channel.close();
		connection.close();
	}
}
