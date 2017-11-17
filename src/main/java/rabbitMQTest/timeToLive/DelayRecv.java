package rabbitMQTest.timeToLive;

import java.io.IOException;

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
 * @author  �  
 * @date ����ʱ�䣺2017��11��17�� ����4:15:24 
 * @version 1.0 
 * ��ʱ��Ϣ������
*/
public class DelayRecv {

	/** 
     * �������в�����consumer���ڴ���ת����������ʱ��Ϣ 
     * @throws Exception 
     */  
    public static void delayRecv() throws Exception{  
    	ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
        channel.exchangeDeclare("exchange-direct", "direct");  
        String queueName=channel.queueDeclare().getQueue();  
        channel.queueBind(queueName, "exchange-direct", "routing-delay");
        
        System.out.println("DelayRecv waitting for ...");
        Consumer consumer=new DefaultConsumer(channel){  
            @Override  
            public void handleDelivery(String consumerTag,Envelope envelope,AMQP.BasicProperties properties,byte[] body) throws IOException{  
            	User user = (User)SerializeUtils.deSerialize(body);  
                System.out.println(envelope.getRoutingKey()+":Received :'"+user.toString()+"' done");  
//              channel.basicAck(envelope.getDeliveryTag(), false);  
            }  
        };  
        //�ر��Զ�Ӧ����ƣ�Ĭ�Ͽ�������ʱ����Ҫ�ֶ����� 
        channel.basicConsume(queueName, true, consumer);  
    }  
      
    public static void main(String[] args) throws Exception {  
        delayRecv();  
    }  
}
