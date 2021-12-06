package hu.progmatic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelloWorldTest {
  private HelloWorld helloWorld;

  @BeforeEach
  void setUp() {
    helloWorld = new HelloWorld();
  }

  @Test
  void getHello() {
    assertEquals("Hello world!", helloWorld.getHelloWorld());
  }
}