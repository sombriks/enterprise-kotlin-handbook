package project010;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Adder {

    public final int value;

    @Autowired
    public Adder(@Value("${adder.value}") int value) {
        this.value = value;
    }
}
