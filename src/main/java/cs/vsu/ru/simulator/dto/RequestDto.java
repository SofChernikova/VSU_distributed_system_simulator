package cs.vsu.ru.simulator.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RequestDto {

    private String message;
    private boolean sendToNeighbors;
}
