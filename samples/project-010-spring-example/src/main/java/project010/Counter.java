package project010;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Counter {

  private int value;
  private final Adder adder;
  private final Subtracter subtracter;

  @Autowired
  public Counter(Adder adder, Subtracter subtracter) {
    this.adder = adder;
    this.subtracter = subtracter;
  }

  public void increment() {
    value += adder.value;
  }

  public void decrement() {
    value -= subtracter.value;
  }

  public int getValue() {
    return value;
  }
}
