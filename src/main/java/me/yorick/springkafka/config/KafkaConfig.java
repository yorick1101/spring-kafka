package me.yorick.springkafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {


	/*
	 * Spring create topics based on this type of bean via KafkaAdmin
	 *  
	 * NewTopic(String name, int numPartitions, short replicationFactor)
	 * - replicationFactor: 
	 *    Kafka replicates the log for each topic's partitions across a configurable number of servers
	 *    The total number of replicas including the leader constitute the replication factor
	 */
	@Bean
    public NewTopic HelloTopic() {
         return TopicBuilder.name("hello").replicas(1).partitions(1).build();
    }
	
	
}
