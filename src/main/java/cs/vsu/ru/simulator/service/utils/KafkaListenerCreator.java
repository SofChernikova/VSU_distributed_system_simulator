package cs.vsu.ru.simulator.service.utils;

import cs.vsu.ru.simulator.service.listener.KafkaTemplateListener;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.config.KafkaListenerEndpoint;
import org.springframework.kafka.config.MethodKafkaListenerEndpoint;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KafkaListenerCreator {
    private static final String kafkaListenerId = "kafkaListenerId-";
    static AtomicLong endpointIdIndex = new AtomicLong(1);

   private final KafkaTemplateListener kafkaTemplateListener;

    public KafkaListenerEndpoint createKafkaListenerEndpoint(String... topic) {
        var kafkaListenerEndpoint = createDefaultMethodKafkaListenerEndpoint(topic);
        kafkaListenerEndpoint.setBean(kafkaTemplateListener);
        try {
            kafkaListenerEndpoint.setMethod(KafkaTemplateListener.class.getMethod("onMessage", ConsumerRecord.class));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Attempt to call a non-existent method " + e);
        }
        return kafkaListenerEndpoint;
    }

    private MethodKafkaListenerEndpoint<String, String> createDefaultMethodKafkaListenerEndpoint(String... topic) {
        var groupId = Arrays.asList(topic).stream()
                .collect(Collectors.joining("-"));

        MethodKafkaListenerEndpoint<String, String> kafkaListenerEndpoint = new MethodKafkaListenerEndpoint<>();
        kafkaListenerEndpoint.setId(generateListenerId());
        kafkaListenerEndpoint.setGroupId(groupId);
        kafkaListenerEndpoint.setAutoStartup(true);
        kafkaListenerEndpoint.setTopics(topic);
        kafkaListenerEndpoint.setMessageHandlerMethodFactory(new DefaultMessageHandlerMethodFactory());
        return kafkaListenerEndpoint;
    }

    private String generateListenerId() {
        return kafkaListenerId + endpointIdIndex.getAndIncrement();
    }
}