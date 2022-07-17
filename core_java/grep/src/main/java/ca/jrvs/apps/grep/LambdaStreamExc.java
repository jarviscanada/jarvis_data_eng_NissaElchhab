package ca.jrvs.apps.grep;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public interface LambdaStreamExc {

  /**
   * Create a  string stream from array
   * note: an arbitrary number of values will be stored in an array
   * @param strings
   * @return
   */
  Stream<String> createStrStream(String... strings);

  /**
   * Convert all strings to upper case
   * Please use createStrStream
   * @param strings
   * @return
   */
  Stream<String> toUpperCase(String... strings);

  /**
   * filter out strings that contain the pattern
   * ex.
   * filter(stringStream, "a") will return a stream which no element contains "a"
   * @param stringStream
   * @param pattern
   * @return
   */
  Stream<String> filter(Stream<String> stringStream, String pattern);

  /**
   * create an IntStream from an array
   * @param arr
   * @return
   */
  IntStream createIntStream(int[] arr);

  /**
   * Convert a Stream to List
   * @param stream
   * @return
   * @param <E>
   */
  <E> List<E> toList(Stream<E> stream);

  /**
   * Convert an IntStream to List
   * @param intstream
   * @return
   */
  List<Integer> toList(IntStream intstream);

  /**
   * Create an IntStream range from start to end inclusive
   * @param start
   * @param end
   * @return
   */
  IntStream createIntStream(int start, int end);

  /**
   * convert an IntStream to a DoubleStream
   * and compute square root of each element
   * @param intStream
   * @return
   */
  DoubleStream squareRootIntStream(IntStream intStream);

  /**
   * filter out all even numbers and get odd numbers from an IntStream
   * @param intStream
   * @return
   */
  IntStream getOdd(IntStream intStream);

  /**
   * Return a lambda function that prints a message with a prefix and a suffix
   * This can be useful to format logs
   * - functional interface:
   * bit.ly/2pTXRwM
   * bit.ly/33onFig
   * - lambda syntax
   *
   * LambdaStreamExc lse = new LambdaStreamImpl()
   * Consumer<String> printer = lse.getLambdaPrinter("start>", "<end")
   * printer.accept("message body")
   * sout:
   * start>message body<end
   *
   * @param prefix
   * @param suffix
   * @return
   */
  Consumer<String> getLambdaPrinter(String prefix, String suffix);

  /**
   * Print each message with a given printer
   * Please use `getLambdaPrinter` method
   *
   * e.g.
   * String[] messages = {"a","b", "c"};
   * lse.printMessages(messages, lse.getLambdaPrinter("msg:", "!") );
   *
   * sout:
   * msg:a!
   * msg:b!
   * msg:c!
   *
   * @param messages
   * @param printer
   */
  void printMessages(String[] messages, Consumer<String> printer);

  /**
   * Print all odd number from a intStream.
   * Please use `createIntStream` and `getLambdaPrinter` methods
   *
   * e.g.
   * lse.printOdd(lse.createIntStream(0, 5), lse.getLambdaPrinter("odd number:", "!"));
   *
   * sout:
   * odd number:1!
   * odd number:3!
   * odd number:5!
   *
   * @param intStream
   * @param printer
   */
  void printOdd(IntStream intStream, Consumer<String> printer);

  /**
   * Square each number from the input.
   * Please write two solutions and compare difference
   *   - using flatMap
   *
   * @param ints
   * @return
   */
  Stream<Integer> squareIntStream(int[] ints);
}
