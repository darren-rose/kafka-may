package com.example.kafkaproducer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.stereotype.Component;

import java.util.Optional;

@EnableKafkaStreams
@SpringBootApplication
public class KafkaProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaProducerApplication.class, args);
	}

}

@Slf4j
@Component
class Processor {

	@Autowired
	public void process(StreamsBuilder builder) {

		KStream<String, String> inputStream = builder.stream("topic-x");

		KStream<String, String> games = inputStream.map((s, line) -> {
			Optional<Game> game = toGame(line);
			if (game.isPresent()) {
				return new KeyValue<>(game.get().getId(), game.get().getName());
			} else {
				return new KeyValue<>("0", "Game 0");
			}
		});

		games.groupByKey().count().toStream().to("topic-y");

	}

	public Optional<Game> toGame(String line) {
		String[] fields = line.replaceAll("\"", "").split(",");
		log.info("fields {}, {}, {}", fields[0], fields[1], fields[2]);
		try {
//			Long id = Long.parseLong(fields[0]);
			String id = fields[0];
			String name = fields[1];
			Behaviour behaviour = Behaviour.valueOf(fields[2]);
			return Optional.of(new Game(id, name, behaviour));
		} catch (Exception e) {
			log.warn(e.getMessage());
			return Optional.empty();
		}
	}

}
