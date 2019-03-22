package com.kingdee.service;

import java.util.Date;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

@Component
public class ConsumerService {
	
	@RabbitListener(queues = "fanout_queue_a")
	public void fanoutA(Channel channel, Message message) throws Exception {
		System.out.println("消费者收到消息 ：" + new String(message.getBody()) + " 收到时间 ： " + new Date());
		try {
			if ("1".equals(new String(message.getBody()) )) {
				channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
				System.out.println("消费者消费消息成功");
			} else {
				 channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
				 System.out.println("消费者消费消息失败");
			}
		   
		} catch (Exception e) {
			System.out.println("消费者消费消息失败");
			//消息重新回到队列中
			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
		}
	}
	
	@RabbitListener(queues = "fanout_queue_b")
	public void fanoutB(Channel channel, Message message) throws Exception {
		System.out.println("消费者收到消息 ：" + new String(message.getBody()) + " 收到时间 ： " + new Date());
		try {
			if ("1".equals(new String(message.getBody()) )) {
				channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
				System.out.println("消费者消费消息成功");
			} else {
				 channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
				 System.out.println("消费者消费消息失败");
			}
		   
		} catch (Exception e) {
			System.out.println("消费者消费消息失败");
			//消息重新回到队列中
			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
		}
	}
	
	@RabbitListener(queues = "direct_queue")
	public void direct(Channel channel, Message message) throws Exception {
		System.out.println("消费者收到消息 ：" + new String(message.getBody()) + " 收到时间 ： " + new Date());
		try {
			if ("1".equals(new String(message.getBody()) )) {
				channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
				System.out.println("消费者消费消息成功");
			} else {
				 channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
				 System.out.println("消费者消费消息失败");
			}
		   
		} catch (Exception e) {
			System.out.println("消费者消费消息失败");
			//消息重新回到队列中
			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
		}
	}
	
	@RabbitListener(queues = "topic_queue")
	public void topic(Channel channel, Message message) throws Exception {
		System.out.println("消费者收到消息 ：" + new String(message.getBody()) + " 收到时间 ： " + new Date());
		try {
			if ("1".equals(new String(message.getBody()) )) {
				channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
				System.out.println("消费者消费消息成功");
			} else {
				 channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
				 System.out.println("消费者消费消息失败");
			}
		   
		} catch (Exception e) {
			System.out.println("消费者消费消息失败");
			//消息重新回到队列中
			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
		}
	}

	@RabbitListener(queues = "dead_queue")
	public void dead(Channel channel, Message message) throws Exception {
		System.out.println("消费者从死信队列收到消息 ：" + new String(message.getBody()) + " 收到时间 ： " + new Date());
		try {
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
			System.out.println("消费者死信队列消费消息成功");
		} catch (Exception e) {
			System.out.println("消费者死信队列消费消息失败");
			//消息重新回到队列中
			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
		}
		
	}

}
