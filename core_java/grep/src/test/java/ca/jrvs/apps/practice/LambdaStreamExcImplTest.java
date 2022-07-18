package ca.jrvs.apps.practice;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
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
  int[] oddInts = {1, 5, 7, 13, 17, 19, 23, 29, 31, 37, 59};
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
  void testCreateIntStreamFromInts() {
    Builder intStreamBuilder = IntStream.builder();
    intStreamBuilder.accept(ints[0]);
    intStreamBuilder.accept(ints[1]);
    intStreamBuilder.accept(ints[2]);
    intStreamBuilder.accept(ints[3]);
    intStreamBuilder.accept(ints[4]);
    intStreamBuilder.accept(ints[5]);
    assertArrayEquals( intStreamBuilder.build().toArray(), lse.createIntStream(ints).toArray());
  }

  @Test
  void testToList() {
    List<Object> anObjectList = anObjectStream.collect(Collectors.toList());
    assertIterableEquals(anObjectList, lse.toList(anObjectStream));
  }

  @Test
  void testCreateIntStreamFromInterval() {
    Builder intStreamBuilder = IntStream.builder();
    int to = new Random().nextInt();
    int from = new Random().nextInt(to);
    for (int i = from; i <= to; ++i) {
      intStreamBuilder.add(i);
    }
    assertArrayEquals(intStreamBuilder.build().toArray(),lse.createIntStream(from, to).toArray() );
  }

  @Test
  void testSquareRootIntStream() {
    Builder intStreamBuilder = IntStream.builder();
    int to = new Random().nextInt();
    int from = new Random().nextInt(to);
    for (int i = from; i <= to; ++i) {
      intStreamBuilder.add(i);
    }
    double[] doubles = intStreamBuilder.build().asDoubleStream().toArray();
    double[] sqrts = Arrays.stream(doubles).map(Math::sqrt).toArray();

    assertArrayEquals(sqrts, lse.squareRootIntStream(lse.createIntStream(from, to)).toArray());
  }

  @Test
  void testGetOdd() {
    assertArrayEquals(oddInts, lse.getOdd(Arrays.stream(oddInts)).toArray());
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