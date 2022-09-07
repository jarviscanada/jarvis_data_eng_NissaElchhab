package ca.jrvs.apps.twitter.controller;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import org.assertj.core.api.Assertions;
import org.junit.After;
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
    argsParser = new ArgsParser();
  }

  @After
  public void tearDown() {
    argsParser = null;
  }

  @Test
  public void configure() {
    // post
    goodArgv = new String[]{"post", "text tweet here", "14.14:10.10"};
    badArgv = new String[]{"post", "text tweet here", "14.14,10:10"};
    goodArgvDeque = new ArrayDeque<>();
    //    goodArgvDeque.add(new ArrayDeque<>(Arrays.asList("post"))); SKIPPED
    goodArgvDeque.add(new ArrayDeque<>(Arrays.asList("text tweet here")));
    goodArgvDeque.add(new ArrayDeque<>(Arrays.asList("14.14", "10.10")));

    argsParser.configure(goodArgv, 2, ":");
    logger.debug(argsParser.getPositionalArgs().peekLast().peekLast());
    Assertions.assertThat(argsParser.getPositionalArgs().peekLast().peekLast())
        .isEqualTo(goodArgvDeque.peekLast().peekLast());
    Assertions.assertThat(argsParser.getExpectedMinArgc()).isEqualTo(2);
    Assertions.assertThat(argsParser.getSeparators()).isEqualTo(":");
    Assertions.assertThat(argsParser.getPositionalArgs().size()).isEqualTo(2);
    Assertions.assertThat(argsParser.getPositionalArgs().peekLast().size()).isEqualTo(2);

  }

  @Test
  public void getArg() {
    // post
    goodArgv = new String[]{"post", "text tweet here", "14.14:10.10"};
    badArgv = new String[]{"post", "text tweet here", "14.14,10:10"};
    goodArgvDeque = new ArrayDeque<>();
    //    goodArgvDeque.add(new ArrayDeque<>(Arrays.asList("post"))); SKIPPED
    goodArgvDeque.add(new ArrayDeque<>(Arrays.asList("text tweet here")));
    goodArgvDeque.add(new ArrayDeque<>(Arrays.asList("14.14", "10.10")));

    argsParser.configure(goodArgv, 2, ":");
    Assertions.assertThat(argsParser.getArg()).isEqualTo("text tweet here");
    Assertions.assertThat(argsParser.getArg()).isEqualTo("14.14");
    Assertions.assertThat(argsParser.getArg()).isEqualTo("10.10");
  }

  @Test
  public void validate() {
  }

  @Test
  public void getArgAsStringArray() {
    //show
    goodArgv = new String[]{"show", "1234567890", "[text,favorited,id,id_str]"};
    badArgv = new String[]{"show", "NaN1234567890", "[text,favorited, id,whatisthis]"};
    goodArgvDeque = new ArrayDeque<>();
    //    goodArgvDeque.add(new ArrayDeque<>(Arrays.asList("show"))); SKIPPED
    goodArgvDeque.add(new ArrayDeque<>(Arrays.asList("1234567890")));
    goodArgvDeque.add(new ArrayDeque<>(Arrays.asList("[text,favorited,id,id_str]")));

    argsParser.configure(goodArgv, 1, 2, ":");
    Assertions.assertThat(argsParser.getArg()).isEqualTo("1234567890");
    String[] fields = argsParser.getArgAsStringArray();
    Assertions.assertThat(fields.length).isEqualTo(4);
    Assertions.assertThat(fields[0]).isEqualTo("text");
    Assertions.assertThat(fields[3]).isEqualTo("id_str");
  }
}