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

}
