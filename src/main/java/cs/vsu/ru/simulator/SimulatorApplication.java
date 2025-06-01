package cs.vsu.ru.simulator;

import cs.vsu.ru.simulator.config.prop.AlgorithmsProp;
import cs.vsu.ru.simulator.config.prop.NeighborsProp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class SimulatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimulatorApplication.class, args);
	}

}
