package ca.jrvs.apps.grep;

import java.util.List;
import java.util.Map;

public interface Getopts {
    /**
     * validates argument list
     * @param argv
     * @return true if all arguments are valid
     */
    boolean isValid(String[] argv);

    /**
     *
     * @param argv
     * @param argvList
     */
    void parsePositionalArgs(String[] argv,  List<String> argvList);

    /**
     * parses then assigns names to a Map<K,V>
     * @param argv command line arguments' list
     * @param argvMap Map<argumentName, argumentValue>
     */
    void parsePositionalArgs(String[] argv,  Map<String, String> argvMap);

    /**
     * returns next arg from the positional args
     * @return
     */
    String getArg();

    /**
     * returns argument value associated with argument's key
     * @param argKey
     * @return
     */
    String getArg(String argKey);

}
