package cs.vsu.ru.simulator.controller;

import cs.vsu.ru.simulator.dto.RequestDto;
import cs.vsu.ru.simulator.service.producer.MessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageProducer messageProducer;

    /**
     * @param processId - id процесса (топика)
     * @param requestDto - dto,
     *                  dto.message - сообщение {строка или switch, если switch, то происходит вызов алгоритма}
     *                  dto.sendToNeighbors - флаг отправки сообщения соседям
     * */
    @PostMapping("/{processId}")
    public void sendMessage(@PathVariable int processId, @RequestBody RequestDto requestDto) {
        messageProducer.sendMessage(processId, requestDto);
    }
}