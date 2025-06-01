package cs.vsu.ru.simulator.config;

import cs.vsu.ru.simulator.config.prop.AlgorithmsProp;
import cs.vsu.ru.simulator.config.prop.NeighborsProp;
import cs.vsu.ru.simulator.service.listener.KafkaTemplateListener;
import cs.vsu.ru.simulator.function.Functions;
import cs.vsu.ru.simulator.service.producer.MessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class ProcessConfig {

    private final NeighborsProp neighbors;

    private final AlgorithmsProp algorithms;

    private final Functions functions;

//    private final MessageProducer producer;

    /**
     * Получаем соседей процессов
     */
    @Bean
    public List<List<Integer>> neighbors() {
        return neighbors.getNeighbors();
    }

    /**
     * Алгоритм работы функции процесса
     */
    @Bean
    public List<String> algorithms() {
        return algorithms.getAlgorithms();
    }

    /**
     * Настройка процесса
     * */
//    @Bean
//    public KafkaTemplateListener kafkaTemplateListener(){
//        return new KafkaTemplateListener(functions, producer, algorithms());
//    }

}
