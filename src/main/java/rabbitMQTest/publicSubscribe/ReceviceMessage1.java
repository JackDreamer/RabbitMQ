package rabbitMQTest.publicSubscribe;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/** 
 * @author  �  
 * @date ����ʱ�䣺2017��11��14�� ����7:42:14 
 * @version 1.0 
*/
public class ReceviceMessage1 {
	
	private static final String EXCHANGE_NAME = "logs";
	
	/**
	 * @param args
	 * @throws IOException
	 * @throws TimeoutException
	 * 
	 * 1��������Ϊ��logs����exchange�ģ���ʽΪ"fanout"���ͷ��Ͷ�һ����
	 * 2��channel.queueDeclare().getQueue();�����õ�һ��������Ƶ�Queue����queue������Ϊnon-durable��exclusive��auto-delete�ģ�����queue�󶨵������exchange�Ͻ�����Ϣ��
     * 3��ע��binding queue��ʱ��channel.queueBind()�ĵ���������Routing keyΪ�գ������е���Ϣ�����ա�������ֵ��Ϊ�գ���exchange typeΪ��fanout����ʽ�¸�ֵ�����ԣ� 
	 */
	public static void main(String[] args) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		
		//����һ������
		String queueName = channel.queueDeclare().getQueue();
		//�󶨽�����
		channel.queueBind(queueName, EXCHANGE_NAME, "");

		System.out.println("ReceviceMessage1 waitting for messages!");

		Consumer consumer = new DefaultConsumer(channel) {
	            @Override
	            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
	                String message = new String(body, "UTF-8");
	                System.out.println(" ReceviceMessage1 '" + message + "'");
	            }
	        };
		
        channel.basicConsume(queueName, true, consumer);//���л��Զ�ɾ��
	}
	
}
