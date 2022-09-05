package ca.jrvs.apps.twitter.controller;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ArgsParser {

  private static final Logger logger = LoggerFactory.getLogger(ArgsParser.class);
  public final String NO_NEXT_ARGUMENT = "";
  private static int nextArgPos;
  private static final Map<String, String> help = new HashMap<>();

  public String separators;
  private int expectedArgc;
  // TODO change to enums, Maps, and namedArguments as well
  // TODO validation constraints should be associated with the parameter/enum class
  private Deque<Deque<String>> positionalArgs;

  static {
    help.put("POST", "Usage: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"");
    help.put("SHOW", "Usage: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"");
    help.put("DELETE", "Usage: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"");
    help.put("HELP", "Usage: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"");
  }

  public ArgsParser() {
  }


  public void configure(String[] argv, int expectedArgc, String separators) {
    this.separators = separators;
    this.expectedArgc = expectedArgc;
    this.positionalArgs = expand(argv);
    nextArgPos = 0;
  }

  public void configure(String[] argv, int expectedArgc) {
    configure(argv, expectedArgc, " "); // empty space
  }

  /**
   * validates argument list
   *
   * @param argv argument vector
   * @return true if arguments are  valid
   */
  public void validate(String[] argv) {
    switch (argv[0].toUpperCase()) {
      case "POST": {
        if (argv.length != expectedArgc) {
          throw new IllegalArgumentException(help.get("POST"));
        }
        if (!argv[2].contains(separators)) {
          throw new IllegalArgumentException(help.get("POST"));
        }
        break;
      }

      case "SHOW": {
        if (argv.length != expectedArgc) {
          throw new IllegalArgumentException(help.get("SHOW"));
        }
        if (!argv[2].contains(separators)) {
          throw new IllegalArgumentException(help.get("SHOW"));
        }

        break;
      }

      case "DELETE": {
        if (argv.length != expectedArgc) {
          throw new IllegalArgumentException(help.get("DELETE"));
        }
        if (!argv[2].contains(separators)) {
          throw new IllegalArgumentException(help.get("DELETE"));
        }
        break;
      }
      default:
        throw new IllegalArgumentException(help.get("HELP"));
    } //switch
  }

  /**
   * returns next arg from the positional args
   *
   * @return
   */
  String getArg() {
    if (positionalArgs.peek() != null && positionalArgs.peek().size() == 1) {
      logger.debug(" positionalArgs: " + positionalArgs.peek());
      return positionalArgs.pollFirst().pollFirst();
    }

    if (positionalArgs.peek() != null && positionalArgs.peek().size() == 2) {
      logger.debug(" positionalArgs: " + positionalArgs.peek());
      return positionalArgs.peekFirst().pollFirst();
    }

    logger.warn("Reached the end of positional arguments: No Next argument.");
    return NO_NEXT_ARGUMENT;

  }


  private Deque<Deque<String>> expand(String[] argv) {
    int indexOfFirstArgument = 1; // to skip argv[0] i.e the command
    Deque<Deque<String>> positionalArgv = new ArrayDeque<>();
    for (int i = 1; i < argv.length; ++i) {
      if (argv[i].contains(separators)) {
        Deque<String> subArgv = new ArrayDeque<>(Arrays.asList(argv[i].split(separators)));
        positionalArgv.add(subArgv);
      } else {
        positionalArgv.add(new ArrayDeque<>(Collections.singletonList(argv[i])));
      }
    }
    return positionalArgv;
  }


  public static int getNextArgPos() {
    return nextArgPos;
  }

  public static void setNextArgPos(int nextArgPos) {
    ArgsParser.nextArgPos = nextArgPos;
  }


  // getters, setters

  public int getExpectedArgc() {
    return expectedArgc;
  }

  public void setExpectedArgc(int expectedArgc) {
    this.expectedArgc = expectedArgc;
  }


  public String getSeparators() {
    return separators;
  }

  public void setSeparators(String separators) {
    this.separators = separators;
  }


  public Deque<Deque<String>> getPositionalArgs() {
    return positionalArgs;
  }

  public void setPositionalArgs(Deque<Deque<String>> positionalArgs) {
    this.positionalArgs = positionalArgs;
  }
}
