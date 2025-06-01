package cs.vsu.ru.simulator.service.producer;

import cs.vsu.ru.simulator.dto.RequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static cs.vsu.ru.simulator.dictionary.KafkaConstants.PROCESS_PREFIX;


@Service
@RequiredArgsConstructor
public class MessageProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final List<List<Integer>> neighbors;

    /**
     * Вызов главного процесса
     * Если флаг sendToNeighbors = true, то отправляет сообщение соседям
     * */
    public void sendMessage(int processId, RequestDto requestDto) {
        var topic = PROCESS_PREFIX + processId;
        kafkaTemplate.send(topic, requestDto.getMessage());

        System.out.printf("Sent message to main topic %s: %s%n", topic, requestDto.getMessage());

        if (requestDto.isSendToNeighbors()) {
            Optional.ofNullable(neighbors.get(processId))
                    .ifPresent(neighbors -> neighbors.forEach(neighbor -> {
                        var neighborTopic = PROCESS_PREFIX + neighbor;
                        kafkaTemplate.send(neighborTopic, requestDto.getMessage());

                        System.out.printf("Sent message to neighbor topic %s: %s%n", neighborTopic, requestDto.getMessage());
                    }));
        }
    }
}
