package com.kingdee.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class ProductConfig {

	private static final String DEAD_QUEUE_KEY = "dead_queue";
	
	private static final String DEAD_LETTER_EXCHANGE_KEY = "dead_exchange";

	private static final String DEAD_LETTER_ROUTING_KEY = "dead_routing";
	
	private static final String DEAD_FANOUT_QUEUE_KEY = "dead_fanout_queue";

	private static final String DEAD_FANOUT_EXCHANGE_KEY = "dead_fanout_exchange";
	
	private static final String FANOUT_QUEUE_A_KEY = "fanout_queue_a";
	
	private static final String FANOUT_QUEUE_B_KEY = "fanout_queue_b";
	
	private static final String FANOUT_EXCHANGE_KEY = "fanout_exchange";
	
	private static final String TOPIC_QUEUE_KEY = "topic_queue";
	
	private static final String TOPIC_EXCHANGE_KEY = "topic_exchange";
	
	private static final String TOPIC_BINDING_KEY = "topic.#";
	
	private static final String DIRECT_QUEUE_KEY = "direct_queue";
	
	private static final String DIRECT_EXCHANGE_KEY = "direct_exchange";
	
	private static final String DIRECT_BINDING_KEY = "direct_bind_key";
	
	// 死信队列
	@Bean("deadLetterQueue")
	public Queue deadLetterQueue() {
		return QueueBuilder.durable(DEAD_QUEUE_KEY).build();
	}

	// 死信广播队列(备用)
	@Bean("deadFanoutQueue")
	public Queue fanoutQueue() {
		return QueueBuilder.durable(DEAD_FANOUT_QUEUE_KEY)
				.withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE_KEY)
				.withArgument("x-dead-letter-routing-key", DEAD_LETTER_ROUTING_KEY)
				.withArgument("x-message-ttl", 30000)
				.build();
	}
	
	//广播队列A
	@Bean("fanoutQueueA")
	public Queue fanoutQueueA() {
		return QueueBuilder.durable(FANOUT_QUEUE_A_KEY).build();
	}
	
	//广播队列B
	@Bean("fanoutQueueB")
	public Queue fanoutQueueB() {
		return QueueBuilder.durable(FANOUT_QUEUE_B_KEY).build();
	}
	
	
	//topic队列
	@Bean("topicQueue")
	public Queue topicQueue() {
		return QueueBuilder.durable(TOPIC_QUEUE_KEY).build();
	}
	
	//direct队列
	@Bean("directQueue")
	public Queue directQueue() {
		return QueueBuilder.durable(DIRECT_QUEUE_KEY).build();
	}

	// 广播交换机
	@Bean("deadFanoutExchange")
	public Exchange deadFanoutExchange() {
		return new FanoutExchange(DEAD_FANOUT_EXCHANGE_KEY);
	}
	
	//死信广播交换机
	@Bean("fanoutExchange")
	public Exchange fanoutExchange() {
		return new FanoutExchange(FANOUT_EXCHANGE_KEY);
	}
	
	//direct交换机
	@Bean("directExchange")
	public Exchange directExchange() {
		return new DirectExchange(DIRECT_EXCHANGE_KEY);
	}
	
	//topic交换机
	@Bean("topicExchange")
	public Exchange topicExchange() {
		return new TopicExchange(TOPIC_EXCHANGE_KEY);
	}

	// 死信交换机
	@Bean("deadLetterExchange")
	public Exchange deadLetterExchange() {
		return ExchangeBuilder.directExchange(DEAD_LETTER_EXCHANGE_KEY).durable(true).build();
	}

	//死信队列绑定
	@Bean
	public Binding deadLetterBinding(@Qualifier("deadLetterQueue") Queue queue, @Qualifier("deadLetterExchange") DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(DEAD_LETTER_ROUTING_KEY);
		//return new Binding(DEAD_QUEUE_KEY, Binding.DestinationType.QUEUE, DEAD_LETTER_EXCHANGE_KEY, DEAD_LETTER_ROUTING_KEY, null);
	}

	//进入死信队列前绑定
	@Bean
	public Binding deadFanoutBinding(@Qualifier("deadFanoutQueue") Queue queue, @Qualifier("deadFanoutExchange") FanoutExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange);
	}
	
	//direct队列绑定
	@Bean
	public Binding directBinding(@Qualifier("directQueue") Queue queue, @Qualifier("directExchange") DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(DIRECT_BINDING_KEY);
	}
	
	//topic队列绑定
	@Bean
	public Binding topicBinding(@Qualifier("topicQueue") Queue queue, @Qualifier("topicExchange") TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(TOPIC_BINDING_KEY);
	}
	
	//fanout队列绑定
	@Bean
	public Binding fanoutABinding(@Qualifier("fanoutQueueA") Queue queue, @Qualifier("fanoutExchange") FanoutExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange);
	}
	
	@Bean
	public Binding fanoutBBinding(@Qualifier("fanoutQueueB") Queue queue, @Qualifier("fanoutExchange") FanoutExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange);
	}

}
