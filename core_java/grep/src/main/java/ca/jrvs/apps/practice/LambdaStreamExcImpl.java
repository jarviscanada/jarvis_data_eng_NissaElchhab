package ca.jrvs.apps.practice;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LambdaStreamExcImpl implements LambdaStreamExc {

  /**
   * Create a  string stream from array note: an arbitrary number of values will be stored in an
   * array
   *
   * @param strings
   * @return
   */
  @Override
  public Stream<String> createStrStream(String... strings) {
    return Arrays.stream(strings);
  }

  /**
   * Convert all strings to upper case Please use createStrStream
   *
   * @param strings
   * @return
   */
  @Override
  public Stream<String> toUpperCase(String... strings) {
    return Arrays.stream(strings).map(String::toUpperCase);
  }

  /**
   * filter out strings that contain the pattern ex. filter(stringStream, "a") will return a stream
   * which no element contains "a"
   *
   * @param stringStream
   * @param pattern
   * @return
   */
  @Override
  public Stream<String> filter(Stream<String> stringStream, String pattern) {
    return stringStream.filter(s -> !s.contains(pattern));
  }

  /**
   * create an IntStream from an array
   *
   * @param arr
   * @return
   */
  @Override
  public IntStream createIntStream(int[] arr) {
    return Arrays.stream(arr);
  }

  /**
   * Convert a Stream to List
   *
   * @param stream
   * @return
   */
  @Override
  public <E> List<E> toList(Stream<E> stream) {
    return stream.collect(Collectors.toList());
  }

  /**
   * Convert an IntStream to List
   *
   * @param intstream
   * @return
   */
  @Override
  public List<Integer> toList(IntStream intstream) {
    return this.toList(intstream.boxed());
  }

  /**
   * Create an IntStream range from start to end inclusive
   *
   * @param start
   * @param end
   * @return
   */
  @Override
  public IntStream createIntStream(int start, int end) {

    return IntStream.rangeClosed(start,end);
  }

  /**
   * convert an IntStream to a DoubleStream and compute square root of each element
   *
   * @param intStream
   * @return
   */
  @Override
  public DoubleStream squareRootIntStream(IntStream intStream) {

    return intStream.asDoubleStream().map(Math::sqrt);
  }

  /**
   * filter out all even numbers and get odd numbers from an IntStream
   *
   * @param intStream
   * @return
   */
  @Override
  public IntStream getOdd(IntStream intStream) {
    return intStream.filter(i->(i%2)!=0);
  }

  /**
   * Return a lambda function that prints a message with a prefix and a suffix This can be useful to
   * format logs - functional interface: bit.ly/2pTXRwM bit.ly/33onFig - lambda syntax
   * <p>
   * LambdaStreamExc lse = new LambdaStreamImpl() Consumer<String> printer =
   * lse.getLambdaPrinter("start>", "<end") printer.accept("message body") sout: start>message
   * body<end
   *
   * @param prefix
   * @param suffix
   * @return
   */
  @Override
  public Consumer<String> getLambdaPrinter(String prefix, String suffix) {
    return null;
  }

  /**
   * Print each message with a given printer Please use `getLambdaPrinter` method
   * <p>
   * e.g. String[] messages = {"a","b", "c"}; lse.printMessages(messages,
   * lse.getLambdaPrinter("msg:", "!") );
   * <p>
   * sout: msg:a! msg:b! msg:c!
   *
   * @param messages
   * @param printer
   */
  @Override
  public void printMessages(String[] messages, Consumer<String> printer) {

  }

  /**
   * Print all odd number from a intStream. Please use `createIntStream` and `getLambdaPrinter`
   * methods
   * <p>
   * e.g. lse.printOdd(lse.createIntStream(0, 5), lse.getLambdaPrinter("odd number:", "!"));
   * <p>
   * sout: odd number:1! odd number:3! odd number:5!
   *
   * @param intStream
   * @param printer
   */
  @Override
  public void printOdd(IntStream intStream, Consumer<String> printer) {

  }

  /**
   * Square each number from the input. Please write two solutions and compare difference - using
   * flatMap
   *
   * @param ints
   * @return
   */
  @Override
  public Stream<Integer> squareIntStream(int[] ints) {
    return null;
  }
}
