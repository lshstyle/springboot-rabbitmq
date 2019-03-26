package com.code.service;

import java.util.Date;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@PostConstruct
	public void init() {
		rabbitTemplate.setConfirmCallback(this);
		rabbitTemplate.setReturnCallback(this);
	}

	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		if (ack) {
			System.out.println("消息发送成功:" + correlationData);
		} else {
			System.out.println("消息发送失败:" + cause);
		}

	}

	@Override
	public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
		System.out.println(message.getMessageProperties().getCorrelationIdString() + " 发送失败");

	}

	public void fanout(String msg) {
		for (int i = 0; i < 1000; i++) {
			System.out.println("生产者发送消息：" + msg + " 发送时间：" + new Date());
			CorrelationData cd = new CorrelationData(UUID.randomUUID().toString());
			this.rabbitTemplate.convertAndSend("fanout_exchange", "", msg + i, cd);
		}
	}

	public void deadFanout(String msg) {
		System.out.println("生产者发送消息：" + msg + " 发送时间：" + new Date());

		CorrelationData cd = new CorrelationData(UUID.randomUUID().toString());
		this.rabbitTemplate.convertAndSend("dead_fanout_exchange", "", msg, cd);
	}

	public void topic(String msg) {
		System.out.println("生产者发送消息：" + msg + " 发送时间：" + new Date());

		CorrelationData cd = new CorrelationData(UUID.randomUUID().toString());
		this.rabbitTemplate.convertAndSend("topic_exchange", "topic.1", msg, cd);
	}

	public void direct(String msg) {
		System.out.println("生产者发送消息：" + msg + " 发送时间：" + new Date());

		CorrelationData cd = new CorrelationData(UUID.randomUUID().toString());
		this.rabbitTemplate.convertAndSend("direct_exchange", "direct_bind_key", msg, cd);
	}

}
