package cs.vsu.ru.simulator;

import cs.vsu.ru.simulator.config.prop.AlgorithmsProp;
import cs.vsu.ru.simulator.config.prop.NeighborsProp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class PropertyTest {

    @Autowired
    private AlgorithmsProp algorithmsProp;

    @Autowired
    private NeighborsProp neighborsProp;

    @Test
    void test() {
        assertNotNull(algorithmsProp.getAlgorithms());

        assertNotNull(neighborsProp.getNeighbors());
        System.out.println();
    }
}
