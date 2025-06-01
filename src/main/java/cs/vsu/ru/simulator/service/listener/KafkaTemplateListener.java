package cs.vsu.ru.simulator.service.listener;

import cs.vsu.ru.simulator.dto.RequestDto;
import cs.vsu.ru.simulator.function.Functions;
import cs.vsu.ru.simulator.service.producer.MessageProducer;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

import static cs.vsu.ru.simulator.service.utils.StringUtils.replaceStart;

@Component
@RequiredArgsConstructor
public class KafkaTemplateListener implements MessageListener<String, String> {

    private final Functions functions;
    private final MessageProducer producer;
    private final List<String> algorithms;

    /**
     * Если в качестве сообщения строка передается "switch", то вызывается метод из класса Functions
     * иначе выводится сообщение из топика
     */
    @Override
    public void onMessage(ConsumerRecord<String, String> data) {
        ///"--all --start 0 --division 4" пример кругового сообщения
        var message = data.value().split("--");

        Optional.ofNullable(message[1])
                .map(String::trim)
                .filter(m -> m.equals("all"))
                .ifPresentOrElse(action -> {
                    var start = Double.parseDouble(message[2].split(" ")[1]);

                    var func = message[3].split(" ");
                    var variable = Integer.parseInt(func[1]);

                    try {
                        var method = Functions.class.getMethod(func[0], double.class, double.class);
                        var result = (double) method.invoke(functions, start, variable);

                        System.out.println("Method %s vas invoked: ".formatted(func[0]) + " result: " + result);

                        var processId = Integer.parseInt(data.topic().split("-")[1]);
                        if (processId < algorithms.size()) {
                            System.out.printf("Message goes to the next process: %s%n", processId);

                            producer.sendMessage(processId + 1, new RequestDto()
                                    .setMessage(replaceStart(data.value(), result)));
                        } else {
                            System.out.println("Received final result: " + result);
                        }

                    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }, () -> {
                    if (data.value().equals("switch")) {
                        var processId = data.topic().split("-")[1]; //из названия топика получаем id процесса

                        Optional.ofNullable(algorithms.get(Integer.parseInt(processId)))
                                .ifPresent(funcName -> {
                                    try {
                                        var method = Functions.class.getMethod(funcName);
                                        System.out.println("Method %s vas invoked: ".formatted(funcName) + method.invoke(functions));
                                    } catch (NoSuchMethodException | IllegalAccessException |
                                             InvocationTargetException e) {
                                        throw new RuntimeException(e);
                                    }
                                });
                    } else {
                        System.out.println("RECORD PROCESSING FROM TOPIC %s: ".formatted(data.topic()) + data.value());
                    }
                });
    }
}
