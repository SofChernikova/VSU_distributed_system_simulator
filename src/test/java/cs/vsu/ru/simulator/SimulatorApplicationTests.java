package cs.vsu.ru.simulator;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class SimulatorApplicationTests {

	@Test
	void contextLoads() {
		var message = "--all --start 0 --division 4";//.split("--");
//		ое выражение для поиска флага --start и числа после него

		String regex = "(--start\\s+)\\d+";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(message);

		// Замена найденного числа на новое значение
		String modifiedArguments = matcher.replaceAll("$1" + 5);
		System.out.println(modifiedArguments);
	}

}
