package rabbitMQTest.publicSubscribe;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/** 
 * @author  �  
 * @date ����ʱ�䣺2017��11��14�� ����7:31:42 
 * @version 1.0 
*/
public class PublicshSub {
	
	private static final String EXCHANGE_NAME = "logs";
	/**
	 * @param args
	 * @throws IOException
	 * @throws TimeoutException
	 * ������Ϣ��һ����Ϊ��logs����exchange�ϣ�ʹ�á�fanout����ʽ���ͣ����㲥��Ϣ������Ҫʹ��queue�����Ͷ˲���Ҫ����˭���ա�
	 */
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");//fanout��ʾ�ַ������е������ߵõ�ͬ���Ķ�����Ϣ
		//�ַ���Ϣ
		for(int i =0 ; i < 100000; i++){
			String message = "hello world" + i;
			channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
			System.out.println("Send message : " + message);
		}
		channel.close();
		connection.close();
	}

}
