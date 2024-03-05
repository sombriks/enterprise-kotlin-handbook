package project010;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Subtracter {

  public final int value;

  @Autowired
  public Subtracter(@Value("${subtracter.value}") int value) {
    this.value = value;
  }
}
