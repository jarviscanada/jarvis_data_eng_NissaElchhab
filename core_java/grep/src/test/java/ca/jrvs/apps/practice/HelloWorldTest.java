package ca.jrvs.apps.practice;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HelloWorldTest {

  @DisplayName("Tests HelloWorld and JUnit5 install")
  @Test
  void testHelloWorld() {
    org.junit.jupiter.api.Assertions.assertEquals(true, true);
  }
}
