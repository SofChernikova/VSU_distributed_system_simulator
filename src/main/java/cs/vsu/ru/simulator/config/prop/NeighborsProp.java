package cs.vsu.ru.simulator.config.prop;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "neighbor")
public class NeighborsProp {

    private List<List<Integer>> neighbors;
}
