package ca.jrvs.apps.grep;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface JavaGrepStream {

  /**
   * Top level search workflow
   *
   * @throws IOException
   */
  void process() throws IOException;

  /**
   * Traverse a given directory and returns all files
   *
   * @param rootDir input directory
   * @return files under the rootDir
   * @throws IOException on file system errors
   */
  Stream<Path> listFiles(String rootDir) throws IOException;

  /**
   * Read a file and returns all the lines
   * <p>
   * TODO explain FileReader, BufferedReader, and character encoding
   *
   * @param inputFile file to be read
   * @return lines
   * @throws IllegalArgumentException if an inputFile is not a File
   */
  Stream<String> readLines(Path inputFilePath) throws IllegalArgumentException, IOException;

  /**
   * check if a line contains the regex pattern (passed by user)
   *
   * @param line input string
   * @return true if there is a match
   */
  boolean containsPattern(String line);

  /**
   * Write lines to a file
   * TODO: Explore: FileOutputStream, OutputStreamWriter, BufferedWriter
   *
   * @param lines regex matched lines
   * @throws IOException if write failed
   */
  void writeToFile(Stream<String> lines) throws IOException;

  String getRootPath();

  void setRootPath(String rootPath);

  String getRegex();

  void setRegex(String regex);

  String getOutfile();

  void setOutFile(String outFile);

}
