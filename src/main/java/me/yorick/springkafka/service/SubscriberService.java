package me.yorick.springkafka.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Profile("subscriber")
@Slf4j
public class SubscriberService {

	@KafkaListener(id = "456", topics = "hello")
	public void listen(ConsumerRecord<String, String> record,  Acknowledgment ack) {
		log.info("456 record {}", record);
	}
	
	//id is the group id except idIsGroup() is set to false
	@KafkaListener(id = "123", topics = "hello")
	public void listen(@Payload String  message,
	        //only sent if producer is spring-inegration-kafka @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
	        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
	        @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
	        @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long ts,
	        //avilable if ack mode is manual Acknowledgment ack
	        Acknowledgment ack
	        ) {
		
		log.info("123 {}", message);
		
		
		/*    Acknowledgment is only can be used when the configuration is as below
		 *    
		 *    consumer:
         *       enable-auto-commit: false
         *    listener:
         *       ack-mode: MANUAL 
         *       
         *    The testing result shows:
         *    ack.nack(100); will cause the listener repeatedly process same event
         *    
         *    if don't invoke ack(), the message will keep coming and processing by the listener.
         *    But while restarting, it will receive messages which are not acked again.
		 */
		
		ack.acknowledge();
	    
	}
	
	
}
