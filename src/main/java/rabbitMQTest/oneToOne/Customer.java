package rabbitMQTest.oneToOne;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import rabbitMQTest.common.SerializeUtils;
import rabbitMQTest.common.User;

/**
 * @author �
 * @date ����ʱ�䣺2017��11��9�� ����3:20:17
 * @version 1.0
 */
public class Customer {

	public static final String QUEUE_NAME = "hello";

	public static void Recv() throws IOException, TimeoutException {
		// �������ӹ���
		ConnectionFactory factory = new ConnectionFactory();

		// ����Rabbitmq�ĵ�ַ
		factory.setHost("localhost");

		// ����һ���µ�����
		Connection connection = factory.newConnection();

		// ����һ��ͨ��
		Channel channel = connection.createChannel();

		// ����Ҫ��ע�Ķ���
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		System.out.println("Customer Waitting Receved message!");

		// DefaultConsumer��ʵ����Consumer�ӿڣ�ͨ������һ��Ƶ����
		// ���߷�����������Ҫ�Ǹ�Ƶ������Ϣ�����Ƶ��������Ϣ���ͻ�ִ�лص�����handleDelivery
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
//				String message = new String(body, "UTF-8");
				User user = (User) SerializeUtils.deSerialize(body);
				System.out.println("Customer Received " + user.toString() + "");
			}
		};
		//�Զ��ظ�����Ӧ�� -- RabbitMQ�е���Ϣȷ�ϻ���
        channel.basicConsume(QUEUE_NAME, false, consumer);
	}
	
	public static void main(String[] args) throws IOException, TimeoutException {

		Recv();
	}


}
