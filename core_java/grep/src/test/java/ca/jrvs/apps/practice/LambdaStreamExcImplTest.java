package ca.jrvs.apps.practice;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.IntStream.Builder;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;

class LambdaStreamExcImplTest {

  String[] strings = {"s0", "sa1", "sss2", "astr3", "STR4", "Str5A", "6"};
  int[] ints = {0, 10, 21, 33, 45, 57};
  Stream<Object> anObjectStream = Stream.of(strings);
  LambdaStreamExc lse = new LambdaStreamExcImpl();

  @BeforeEach
  void setUp() {

  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void testCreateStrStream() {
    Stream<String> ss = lse.createStrStream(strings);
    assertArrayEquals(strings, ss.toArray());
  }

  @Test
  void testToUpperCase() {
    Stream<String> ss = lse.createStrStream(strings);
    assertArrayEquals(Arrays.stream(strings).map(String::toUpperCase).toArray(),
        lse.toUpperCase(strings).toArray());
  }

  @Test
  void testFilter() {
    String[] stringsMinusA = (String[]) Arrays.stream(strings).filter(s -> !s.contains("a"))
        .toArray();
    assertArrayEquals(stringsMinusA, lse.filter(lse.createStrStream(strings), "a").toArray());

  }

  @Test
  void testCreateIntStream() {
    Builder intStreamBuilder = IntStream.builder();
    intStreamBuilder.accept(ints[0]);
    intStreamBuilder.accept(ints[1]);
    intStreamBuilder.accept(ints[2]);
    intStreamBuilder.accept(ints[3]);
    intStreamBuilder.accept(ints[4]);
    intStreamBuilder.accept(ints[5]);
    assertArrayEquals(lse.createIntStream(ints).toArray(), intStreamBuilder.build().toArray());
  }

  @Test
  void testToList() {
    List<Object> anObjectList = anObjectStream.collect(Collectors.toList());
    assertIterableEquals(anObjectList, lse.toList(anObjectStream));
  }

  @Test
  void testSquareRootIntStream() {
  }

  @Test
  void testGetOdd() {
  }

  @Test
  void testGetLambdaPrinter() {
  }

  @Test
  void testPrintMessages() {
  }

  @Test
  void testPrintOdd() {
  }

  @Test
  void testSquareIntStream() {
  }
}