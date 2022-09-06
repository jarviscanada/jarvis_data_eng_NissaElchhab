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

  public static final int MAX_ARGUMENTS_COUNT = Integer.MAX_VALUE;
  private static final Logger logger = LoggerFactory.getLogger(ArgsParser.class);
  private static final Map<String, String> help = new HashMap<>();

  static {
    help.put("POST", "Usage: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"");
    help.put("SHOW", "Usage: TwitterCLIApp show tweet_id [field1,fields2]");
    help.put("DELETE", "Usage: TwitterCLIApp delete [id1,id2,..]");
    help.put("HELP", "Usage: TwitterCLIApp post|show|delete [options]");
  }

  private final String NO_NEXT_ARGUMENT = "";
  private final String ARRAY_OPEN_DELIM = "[";
  private final String ARRAY_CLOSE_DELIM = "]";
  private final String ARRAY_ELEM_DELIM = ",";

  private String separators;
  private int expectedMinArgc;
  private int expectedMaxArgc;
  // TODO change to enums, Maps, and namedArguments as well
  // TODO validation constraints should be associated with the parameter/enum class
  private Deque<Deque<String>> positionalArgs;


  public ArgsParser() {
  }

  public void configure(String[] argv, int expectedMinArgc, int expectedMaxArgc,
      String separators) {
    if (expectedMinArgc > expectedMaxArgc || argv.length < expectedMinArgc + 1
        || argv.length > expectedMaxArgc + 1) { // arv[0] is the command argument
      throw new IllegalArgumentException(
          "Unexpected number of parameters.\nHelp:\n" + help.get("HELP"));
    }
    this.separators = separators;
    this.expectedMinArgc = expectedMinArgc;
    this.expectedMaxArgc = expectedMaxArgc;
    this.positionalArgs = expand(argv);
  }

  public void configure(String[] argv, int expectedMinArgc, String separators) {
    configure(argv, expectedMinArgc, expectedMinArgc, separators);
  }

  public void configure(String[] argv, int expectedMinArgc) {
    configure(argv, expectedMinArgc, expectedMinArgc, " "); // empty space
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

  /**
   * returns next arg from the positional args
   *
   * @return
   */
  String[] getArgAsStringArray() {
    String str = getArg().trim(); // expected shape: "[s1,s2,...,sn]" NOTE NO SPACES for now
    if (str.startsWith(ARRAY_OPEN_DELIM) && str.endsWith(ARRAY_CLOSE_DELIM)) {
      str = str.substring(1, str.length() - 1); // chomp both [ and ]
    } else {
      throw new IllegalArgumentException(
          "Wrong format for array of fields.\n"
              + " Likely missing opening or closing bracket(s).\n" + help.get("SHOW"));
    }
    return str.split(ARRAY_ELEM_DELIM);
  }


  private Deque<Deque<String>> expand(String[] argv) {
    int indexOfFirstArgument = 1; // to skip argv[0] i.e the command
    Deque<Deque<String>> positionalArgv = new ArrayDeque<>();
    for (int i = indexOfFirstArgument; i < argv.length; ++i) {
      if (argv[i].contains(separators)) {
        Deque<String> subArgv = new ArrayDeque<>(Arrays.asList(argv[i].split(separators)));
        positionalArgv.add(subArgv);
      } else {
        positionalArgv.add(new ArrayDeque<>(Collections.singletonList(argv[i])));
      }
    }
    return positionalArgv;
  }


  public boolean hasOptionalArguments() {
    return expectedMinArgc != expectedMaxArgc;
  }

  public String[] arrayify() {
    return null;
  }

  private boolean isArgumentCountValid() {
    return false;
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
        if (argv.length != expectedMinArgc + 1) {
          throw new IllegalArgumentException(help.get("POST"));
        }
        if (!argv[2].contains(separators)) {
          throw new IllegalArgumentException(help.get("POST"));
        }
        break;
      }

      case "SHOW": {
        if (argv.length != expectedMinArgc + 1) {
          throw new IllegalArgumentException(help.get("SHOW"));
        }
        if (!argv[2].contains(separators)) {
          throw new IllegalArgumentException(help.get("SHOW"));
        }

        break;
      }

      case "DELETE": {
        if (argv.length != expectedMinArgc + 1) {
          throw new IllegalArgumentException(help.get("DELETE"));
        }
        if (!argv[2].contains(separators)) {
          throw new IllegalArgumentException(help.get("DELETE"));
        }
        break;
      }
      default:
        throw new IllegalArgumentException("Unrecognizable command.\nHelp:\n" + help.get("HELP"));
    } //switch
  }

  // getters, setters

  public int getExpectedMinArgc() {
    return expectedMinArgc;
  }

  public void setExpectedMinArgc(int expectedMinArgc) {
    this.expectedMinArgc = expectedMinArgc;
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
