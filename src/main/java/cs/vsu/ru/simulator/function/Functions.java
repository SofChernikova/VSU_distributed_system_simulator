package cs.vsu.ru.simulator.function;

import jakarta.annotation.Nullable;
import org.springframework.stereotype.Component;

import java.util.Random;

import static java.util.Objects.nonNull;

@Component
public class Functions {
    private final Random random = new Random();

    public double multiple(@Nullable double a, @Nullable double b) {
        if (nonNull(a) && nonNull(b)) {
            return a * b;
        }

        return (random.nextDouble() * 100) * (random.nextDouble() * 100);
    }

    public double addition(@Nullable double a, @Nullable double b) {
        if (nonNull(a) && nonNull(b)) {
            return a + b;
        }

       return (random.nextDouble() * 100) + (random.nextDouble() * 100);
    }

    public double division(@Nullable double a, @Nullable double b) {
        if (nonNull(a) && nonNull(b) && b != 0) {
            return a / b;
        }
        return (random.nextDouble() * 100) / (random.nextDouble() * 99 + 1);
    }

    public double subtraction(@Nullable double a, @Nullable double b) {
        if (nonNull(a) && nonNull(b)) {
            return a - b;
        }
        return(random.nextDouble() * 100) - (random.nextDouble() * 100);
    }
}
