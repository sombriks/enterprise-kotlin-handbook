package project010;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = {
        Config.class, Counter.class, Adder.class, Subtracter.class
})
public class MainTest {

    @Autowired
    private Counter counter;

    @Autowired
    @Qualifier("special-string")
    private String greet;

    @Test
    public void shouldCount() {
        counter.increment();
        counter.decrement();
        counter.increment();
        counter.increment();
        Assertions.assertEquals(4, counter.getValue());
    }

    @Test
    public void shouldGreet() {
        Assertions.assertEquals("hello from Test SPRING!", greet);
    }

}
