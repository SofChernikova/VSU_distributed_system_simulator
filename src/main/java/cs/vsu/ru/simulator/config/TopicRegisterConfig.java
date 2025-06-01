package cs.vsu.ru.simulator.config;

import cs.vsu.ru.simulator.service.utils.KafkaListenerCreator;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;

import java.util.List;

import static cs.vsu.ru.simulator.dictionary.KafkaConstants.PROCESS_PREFIX;
import static java.util.Objects.nonNull;

@Configuration
@RequiredArgsConstructor
public class TopicRegisterConfig {

    private final KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
    private final KafkaListenerContainerFactory kafkaListenerContainerFactory;

    private final KafkaListenerCreator kafkaListenerCreator;

    @Value("${kafka.topics.indexes}")
    private List<Integer> indexes;


    @PostConstruct
    public void init() {
        for (int i = 0; i < indexes.size(); i += 2) {
            int index1 = indexes.get(i);
            int index2 = (i + 1 < indexes.size()) ? indexes.get(i + 1) : -1;

            var listener = kafkaListenerCreator.createKafkaListenerEndpoint(
                    PROCESS_PREFIX + index1,
                    nonNull(index2) ? PROCESS_PREFIX + index2 : "");
            kafkaListenerEndpointRegistry.registerListenerContainer(
                    listener,
                    kafkaListenerContainerFactory,
                    true);

            //в сообщениях либо переключать функцию либо пересылать сообщение дальше соседям +
        }
    }
}
