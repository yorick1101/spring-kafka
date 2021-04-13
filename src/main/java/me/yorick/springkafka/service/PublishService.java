package me.yorick.springkafka.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Profile("publisher")
@Slf4j
public class PublishService {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	@Autowired
	private NewTopic topic;
	
	
	@Scheduled(cron = "*/10 * * * * ?")
	public void publishTime() {
		String current = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		log.info("push {} {}", topic.name(), current);
		kafkaTemplate.send(topic.name(), current);
	}
	
	
}
