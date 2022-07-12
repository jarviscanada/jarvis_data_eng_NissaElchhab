package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JavaGrepImpl implements JavaGrep {

  /* TODO: basic assumed constants for validation
  OS dependent.env. variables. Assuming JavaGrep is Linux only for now
  MAX_REGEX_LENGTH
  MAX_PATH_LENGTH
  MIN_PATH_LENGTH
  */
  public static final int MAX_REGEX_LENGTH;
  public static final int MAX_PATH_LENGTH;
  public static final int MIN_PATH_LENGTH;

  static {
    MAX_REGEX_LENGTH = Byte.MAX_VALUE * 2;
    MAX_PATH_LENGTH = Short.MAX_VALUE * 2;
    MIN_PATH_LENGTH = 1;
  }

  private String regex;
  private String rootPath;
  private String outFile;

  /**
   * Top level search workflow
   *
   * @throws IOException
   */
  @Override
  public void process() throws IOException {

/*
matchedLines = []
for file in listFilesRecursively(rootDir)
  for line in readLines(file)
      if containsPattern(line)
        matchedLines.add(line)
writeToFile(matchedLines)
 */

  }

  /**
   * Traverse a given directory and returns all files
   *
   * @param rootDir input directory
   * @return files under the rootDir
   */
  @Override
  public List<File> listFiles(String rootDir) {
    return null;
  }

  /**
   * Read a file and returns all the lines
   * <p>
   * TODO explain FileReader, BufferedReader, and character encoding
   *
   * @param inputFile file to be read
   * @return lines
   * @throws IllegalArgumentException if an inputFile is not a File
   */
  @Override
  public List<String> readLines(File inputFile) throws IllegalArgumentException {
    return null;
  }

  /**
   * check if a line contains the regex pattern (passed by user)
   *
   * @param line input string
   * @return true if there is a match
   */
  @Override
  public boolean containsPattern(String line) {
    return false;
  }

  /**
   * Write lines to a file
   * TODO: Explore: FileOutputStream, OutputStreamWriter, BufferedWriter
   *
   * @param lines regex matched lines
   * @throws IOException if write failed
   */
  @Override
  public void writeToFile(List<String> lines) throws IOException {

  }

  @Override
  public String getRootPath() {
    return rootPath; // TODO QUESTION: favour this.x or not , inheritance question
  }

  @Override
  public void setRootPath(String rootPath) {
    if (rootPath.length() >= MIN_PATH_LENGTH && rootPath.length() < MAX_PATH_LENGTH) {
      this.rootPath = rootPath;
    } else {
      // log
      throw new IllegalArgumentException("`rootPath` is out of length boundaries");
    }
  }

  @Override
  public String getRegex() {
    return regex;
  }

  @Override
  public void setRegex(String regex) {
    if (regex.length() >= 0 && regex.length() < MAX_REGEX_LENGTH) {
      this.regex = regex;
    } else {
      // log
      throw new IllegalArgumentException("`regex` is out of length boundaries");
    }
  }

  @Override
  public String getOutfile() {
    return outFile;
  }

  @Override
  public void setOutFile(String outFile) {
    if (outFile.length() >= MIN_PATH_LENGTH && outFile.length() < MAX_PATH_LENGTH) {
      this.outFile = outFile;
    } else {
      // log
      throw new IllegalArgumentException("`outFile` is out of length boundaries");
    }
  }
}
