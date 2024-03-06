package project010;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

  public static void main(String[] args) {
    ApplicationContext context = new AnnotationConfigApplicationContext("project010");
    // we don't instantiate a Counter, we ask spring for one and it will do the
    // heavy-lift for us.
    Counter c = context.getBean(Counter.class);
    c.increment();
    c.increment();
    System.out.println(c.getValue());
    String greet = context.getBean("special-string", String.class);
    System.out.println(greet);
  }
}
