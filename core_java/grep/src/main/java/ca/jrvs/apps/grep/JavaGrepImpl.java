package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
  public List<File> listFiles(String rootDir) throws IOException {
    List<File> files = Collections.emptyList();
    files = Files.walk(Paths.get(rootDir), Integer.MAX_VALUE, FileVisitOption.FOLLOW_LINKS)
        .filter(Files::isRegularFile)
        .map(Path::toFile)
        .collect(Collectors.toList());
    return files;
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
    if (!(inputFile instanceof File)) {
      throw new IllegalArgumentException("`inputFile` not a `File`");
    }
    List<String> lines = Collections.emptyList();

    return lines;
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
    if (rootPath == null) {
      // log
      throw new IllegalArgumentException("`rootPath` can't be null");
    } else if (rootPath.length() < MIN_PATH_LENGTH && rootPath.length() >= MAX_PATH_LENGTH) {
      // log
      throw new IllegalArgumentException("`rootPath` can't be  out of length boundaries");
    } else {
      this.rootPath = rootPath;
    }
  }

  @Override
  public String getRegex() {
    return regex;
  }

  @Override
  public void setRegex(String regex) {
    if (regex == null) {
      // log
      throw new IllegalArgumentException("`regex` can't be null");
    } else if (regex.length() >= MAX_REGEX_LENGTH) {
      // log
      throw new IllegalArgumentException("`regex` can't be out of length boundaries");
    } else {
      this.regex = regex;
    }
  }

  @Override
  public String getOutfile() {
    return outFile;
  }

  @Override
  public void setOutFile(String outFile) {
    if (outFile == null) {
      // log
      throw new IllegalArgumentException("`outFile` can't be null");
    } else if (outFile.length() < MIN_PATH_LENGTH && outFile.length() >= MAX_PATH_LENGTH) {
      // log
      throw new IllegalArgumentException("`outFile` can't be out of length boundaries");
    } else {
      this.outFile = outFile;
    }
  }
}
