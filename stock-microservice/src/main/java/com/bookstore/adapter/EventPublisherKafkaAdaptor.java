package com.bookstore.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.bookstore.stock.application.business.events.StockEvent;
import com.bookstore.stock.infra.EventPublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EventPublisherKafkaAdaptor implements EventPublisher {
	private static final Logger logger = LoggerFactory.getLogger(EventPublisherKafkaAdaptor.class);
	@Value("${employee.events.topic}")
	private String topicName;

	private KafkaTemplate<String, String> kafkaTemplate;
	private ObjectMapper mapper;

	
	public EventPublisherKafkaAdaptor(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper mapper) {
		this.kafkaTemplate = kafkaTemplate;
		this.mapper = mapper;
	}


	@Override
	public void publishEvent(StockEvent businessEvent) {
		try {
			var eventAsJson = mapper.writeValueAsString(businessEvent);
			kafkaTemplate.send(topicName, eventAsJson);
		} catch (JsonProcessingException e) {
			logger.error("Error while converting the event to json: {}",
					e.getMessage());
		}
		
	}

}
