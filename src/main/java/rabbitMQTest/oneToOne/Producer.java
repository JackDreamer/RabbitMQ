package rabbitMQTest.oneToOne;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import rabbitMQTest.common.SerializeUtils;
import rabbitMQTest.common.User;

/** 
 * @author  �  
 * @date ����ʱ�䣺2017��11��9�� ����2:49:52 
 * @version 1.0
 * ��Ϣ������ 
*/
public class Producer {
	
	/**
	 * �����͡������ճ��������ر������ڷ�����Ϣ 
	 * @param queue ����
	 * @param object ʵ��
	 * @throws IOException
	 * @throws TimeoutException
	 * @throws UnsupportedEncodingException
	 */
	public static void Send(String queue,Serializable object) throws IOException, TimeoutException, UnsupportedEncodingException {
		//�������ӹ���
		ConnectionFactory factory = new ConnectionFactory();
		
		//����RabbitMQ���������
		factory.setHost("localhost");
//		factory.setUsername("rh");
//		factory.setPassword("123456");
//		factory.setPort(12567);
		
		//����һ���µ�����
		Connection connection = factory.newConnection();
		
		//����һ��ͨ��
		Channel channel = connection.createChannel();
		
		/*
		 * ע��queueDeclare��һ��������ʾ�������ơ�
		 * �ڶ�������Ϊ�Ƿ�־û���true��ʾ�ǣ����н��ڷ���������ʱ���棩
		 * ����������Ϊ�Ƿ��Ƕ�ռ���У������߿���ʹ�õ�˽�ж��У��Ͽ����Զ�ɾ����
		 * ���ĸ�����Ϊ�����������߿ͻ������ӶϿ�ʱ�Ƿ��Զ�ɾ������
		 * ���������Ϊ���е���������
		 */
		//����һ������
		channel.queueDeclare(queue, true, false, false, null);
				
//		String messge = "Hello RabbitMQ!";
		
		/*
		 * ע��basicPublish��һ������Ϊ����������
		 * �ڶ�������Ϊ����ӳ���·��key
		 * ����������Ϊ��Ϣ����������
		 * ���ĸ�����Ϊ������Ϣ������
		 */		
		//���͵�������
		channel.basicPublish("", queue, null, SerializeUtils.serialize(object));
		
		System.out.println("Producer Send " + " " + SerializeUtils.serialize(object) );
		
		//�ر�ͨ��������
		channel.close();
		connection.close();
	}
	
	public static void main(String[] args) throws IOException, TimeoutException {
		//��Ϣʵ��
		User user = new User();
		user.setName("�");
		user.setAge(17);
		user.setPassword("123456");

		//����
		String queue = "hello";
		Send(queue,user);
		
		
	}


}
