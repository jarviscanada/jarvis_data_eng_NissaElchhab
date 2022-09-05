package ca.jrvs.apps.twitter.controller;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArgsParserTest {

  public static Logger logger = LoggerFactory.getLogger(ArgsParserTest.class);

  Deque<Deque<String>> goodArgvDeque;
  private ArgsParser argsParser;
  private String[] goodArgv;
  private String[] badArgv;

  @Before
  public void setUp() throws Exception {
    goodArgv = new String[]{"post", "text tweet here", "14.14:10.10"};
    badArgv = new String[]{"post", "text tweet here", "14.14,10:10"};
    goodArgvDeque = new ArrayDeque<>();
//    goodArgvDeque.add(new ArrayDeque<>(Arrays.asList("post"))); SKIPPED
    goodArgvDeque.add(new ArrayDeque<>(Arrays.asList("text tweet here")));
    goodArgvDeque.add(new ArrayDeque<>(Arrays.asList("14.14", "10.10")));

    argsParser = new ArgsParser();
  }

  @Test
  public void configure() {
    argsParser.configure(goodArgv, 3, ":");
    logger.debug(argsParser.getPositionalArgs().peekLast().peekLast());
    Assertions.assertThat(argsParser.getPositionalArgs().peekLast().peekLast())
        .isEqualTo(goodArgvDeque.peekLast().peekLast());
    Assertions.assertThat(argsParser.getExpectedArgc()).isEqualTo(3);
    Assertions.assertThat(argsParser.getSeparators()).isEqualTo(":");
    Assertions.assertThat(argsParser.getPositionalArgs().size()).isEqualTo(2);
    Assertions.assertThat(argsParser.getPositionalArgs().peekLast().size()).isEqualTo(2);

  }

  @Test
  public void parse() {
    argsParser.configure(goodArgv, 3, ":");
    Assertions.assertThat(argsParser.getArg()).isEqualTo("text tweet here");
    Assertions.assertThat(argsParser.getArg()).isEqualTo("14.14");
    Assertions.assertThat(argsParser.getArg()).isEqualTo("10.10");
  }

  @Test
  public void validate() {
  }

  @Test
  public void getArg() {
  }
}