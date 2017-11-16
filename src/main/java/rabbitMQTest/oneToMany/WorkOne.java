package rabbitMQTest.oneToMany;

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
 * @date ����ʱ�䣺2017��11��9�� ����5:52:45 
 * @version 1.0 
*/
public class WorkOne {
	
	public static final String QUEUE_NAME = "task_queue";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		
		final ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		final Channel channel = connection.createChannel();
		
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		System.out.println("WorkOne Watting fro Message!");
		
		channel.basicQos(1);//ÿ�δӶ��л�ȡ������
		
		final Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("Worker1  Received '" + message + "'");

                doWork(message);
          
                System.out.println("Worker1 Done");
                channel.basicAck(envelope.getDeliveryTag(),false);
                }
            
        };
        boolean autoAck=false;
        //��Ϣ�������ȷ��
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);      
	}
	
	private static void doWork(String task) {
        try {
            Thread.sleep(1000); // ��ͣ1����
        } catch (InterruptedException _ignored) {
            Thread.currentThread().interrupt();
        }
    }
}
