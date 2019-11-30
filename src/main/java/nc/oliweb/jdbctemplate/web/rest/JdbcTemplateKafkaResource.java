package nc.oliweb.jdbctemplate.web.rest;

import nc.oliweb.jdbctemplate.service.JdbcTemplateKafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jdbc-template-kafka")
public class JdbcTemplateKafkaResource {

    private final Logger log = LoggerFactory.getLogger(JdbcTemplateKafkaResource.class);

    private JdbcTemplateKafkaProducer kafkaProducer;

    public JdbcTemplateKafkaResource(JdbcTemplateKafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping("/publish")
    public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
        log.debug("REST request to send to Kafka topic the message : {}", message);
        this.kafkaProducer.send(message);
    }
}
