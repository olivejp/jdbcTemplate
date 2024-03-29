package nc.oliweb.jdbctemplate.service;

import nc.oliweb.jdbctemplate.config.KafkaProperties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.thymeleaf.engine.IterationStatusVar;

@Service
public class JdbcTemplateKafkaProducer {

    private final Logger log = LoggerFactory.getLogger(JdbcTemplateKafkaProducer.class);

    private static final String TOPIC = "topic_jdbctemplate";

    private KafkaProperties kafkaProperties;

    private KafkaProducer<String, String> kafkaProducer;

    private TestService testService;

    public JdbcTemplateKafkaProducer(KafkaProperties kafkaProperties, TestService test) {
        this.kafkaProperties = kafkaProperties;
        this.testService = test;
    }

    public void init() {
        log.info("Kafka producer initializing...");
        kafkaProducer = new KafkaProducer<>(kafkaProperties.getProducerProps());
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
        log.info("Kafka producer initialized");
    }

    public void send(String message) {
        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, message);
        try {
            kafkaProducer.send(record);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void shutdown() {
        log.info("Shutdown Kafka producer");
        kafkaProducer.close();
    }
}
