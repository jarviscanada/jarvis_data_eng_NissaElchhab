package ca.jrvs.apps.grep;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaGrepStreamImpl implements JavaGrepStream {

  /* TODO: basic assumed constants for validation
  OS dependent.env. variables. Assuming JavaGrep is Linux only for now
  MAX_REGEX_LENGTH
  MAX_PATH_LENGTH
  MIN_PATH_LENGTH
  */
  public static final int MAX_REGEX_LENGTH;
  public static final int MAX_PATH_LENGTH;
  public static final int MIN_PATH_LENGTH;
  private static final Logger logger = LoggerFactory.getLogger(JavaGrepImpl.class);

  static {
    MAX_REGEX_LENGTH = Byte.MAX_VALUE * 2;
    MAX_PATH_LENGTH = Short.MAX_VALUE * 2;
    MIN_PATH_LENGTH = 1;
  }

  private String regex;
  private Pattern pattern;
  private String rootPath;
  private String outFile;

  /**
   * Top level search workflow
   *
   * @throws IOException
   */
  @Override
  public void process() throws IOException {
    this.pattern = Pattern.compile(this.regex);
    Builder<Stream<String>> matchesLinesStream = Stream.builder();
    listFiles(rootPath).forEach(path -> {
      logger.debug("File: " + path.toString() + " " + rootPath + "\n");

      try {
        matchesLinesStream.accept(
            readLines(path));
      } catch (IOException e) {
        logger.error("IOException with path:" + path);
        throw new RuntimeException("IOException with path:", e);
      }
    });

    matchesLinesStream.build()
        .map(stringStream -> stringStream.filter(this::containsPattern))
        .forEach(stringStream -> {
          try {
            writeToFile(stringStream);
          } catch (IOException e) {
            logger.error("IOException with stringStream");
            throw new RuntimeException("IOException with stringStream", e);
          }
        });
  }

  /**
   * Traverse a given directory and returns all files
   *
   * @param rootDir input directory
   * @return files under the rootDir
   * @throws IOException on file system errors
   */
  @Override
  public Stream<Path> listFiles(String rootDir) throws IOException {
    Stream<Path> files;
    try {
      files = Files.walk(Paths.get(rootDir), FileVisitOption.FOLLOW_LINKS)
          .filter(Files::isRegularFile);
    } catch (IOException e) {
      logger.error("IOException during root directory walk");
      throw new RuntimeException("IOException during root directory walk", e);
    }

    return files;

  }

  /**
   * Read a file and returns all the lines
   * <p>
   * TODO explain FileReader, BufferedReader, and character encoding
   *
   * @param inputFilePath file to be read
   * @return lines
   * @throws IllegalArgumentException if an inputFile is not a File
   */
  @Override
  public Stream<String> readLines(Path inputFilePath) throws IllegalArgumentException, IOException {
    return Files.newBufferedReader(inputFilePath).lines();
  }

  /**
   * check if a line contains the regex pattern (passed by user)
   *
   * @param line input string
   * @return true if there is a match
   */
  @Override
  public boolean containsPattern(String line) {
    Matcher matcher = pattern.matcher(line);
    return matcher.matches();
  }

  /**
   * Write lines to a file
   * TODO: Explore: FileOutputStream, OutputStreamWriter, BufferedWriter
   *
   * @param lines regex matched lines
   * @throws IOException if write failed
   */
  @Override
  public void writeToFile(Stream<String> lines) throws IOException {
    try (BufferedWriter br = new BufferedWriter(new FileWriter(this.outFile))) {
      lines.forEach(line -> {
        try {
          br.write(line);
          br.newLine();
        } catch (IOException e) {
          logger.error("IOException while writing line: " + line);
          throw new RuntimeException("IOException while writing line", e);
        }
      });

    } catch (IOException e) {
      logger.error("IOException while writing to file");
      throw new IOException("IO error while writing to file: `" + this.outFile + "`");
    }
  }

  @Override
  public String getRootPath() {
    return rootPath; // TODO QUESTION: favour this.x or not , inheritance question
  }

  @Override
  public void setRootPath(String rootPath) {
    if (rootPath == null) {
      throw new IllegalArgumentException("`rootPath` can't be null");
    } else if (rootPath.length() < MIN_PATH_LENGTH && rootPath.length() >= MAX_PATH_LENGTH) {
      logger.error("rootPath `" + rootPath.toString() + "` is out of length boundaries`");
      throw new IllegalArgumentException("`rootPath` can't be out of length boundaries");
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
      throw new IllegalArgumentException("`regex` can't be null");
    } else if (regex.length() >= MAX_REGEX_LENGTH) {
      logger.error("regex argument: `" + regex.toString() + "`");
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
      throw new IllegalArgumentException("`outFile` can't be null");
    } else if (outFile.length() < MIN_PATH_LENGTH && outFile.length() >= MAX_PATH_LENGTH) {
      logger.error("outFile  argument: `" + outFile.toString() + "`");
      throw new IllegalArgumentException("`outFile` can't be out of length boundaries");
    } else {
      this.outFile = outFile;
    }
  }

  /**
   * Returns a string representation of the object. In general, the {@code toString} method returns
   * a string that "textually represents" this object. The result should be a concise but
   * informative representation that is easy for a person to read. It is recommended that all
   * subclasses override this method.
   * <p>
   * The {@code toString} method for class {@code Object} returns a string consisting of the name of
   * the class of which the object is an instance, the at-sign character `{@code @}', and the
   * unsigned hexadecimal representation of the hash code of the object. In other words, this method
   * returns a string equal to the value of:
   * <blockquote>
   * <pre>
   * getClass().getName() + '@' + Integer.toHexString(hashCode())
   * </pre></blockquote>
   *
   * @return a string representation of the object.
   */
  @Override
  public String toString() {
    return "Class#" + this.hashCode() + " ClassName:" + this.getClass().getCanonicalName() +
        " regex=" + this.regex + " (Pattern: " + this.pattern
        + ") rootPath=" + this.rootPath + " outfile=" + this.outFile + "\n";
  }
}
