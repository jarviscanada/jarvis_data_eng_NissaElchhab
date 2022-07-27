
# Introduction: Core Java / Grep
The Grep Project aims to implement a simple `grep`-like app in Java.
The Project will produce a Java artifact that can be run from the command line with as close of an experience and results as the Linux's `grep` (our reference for this project is the [GNU Grep](https://www.gnu.org/software/grep "GNU Grep documentation").

Just like Linux's [`grep`](https://manpages.debian.org/bullseye/grep/grep.1.en.html "man grep"), the app scans all the files within a directory and its sub-directories (recursively), searching for a text pattern within that collection of files, then outputs matched lines to a file.

The app takes three positional arguments on the commandline:

- a regexPattern: a special text string for describing a search pattern, restricted for now to [Java Regex](https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html "Oracle Java 8 Regex Pattern Class documentation") definition
- a sourceDirectory: root directory path to find the collection of files to search into
- an outputFile: output file name

### Technologies used:

- Core Java 8 (version 1.8): the [most](https://www.jetbrains.com/lp/devecosystem-2019/java/#:~:text=Although%20Java%2010%20and%2011,still%20the%20most%20used%20version. "JetBrains Developer Survey 2019") used Java version.
- Openjdk-8: FOSS and reference build of Oracle Java
- Logging libraries: The Simple Logging Facade For Java, or [Slf4j](https://www.slf4j.org) is used as a logger interface/api, while Log4j2 is used for the logger implementation
- Testing library: JUnit5 for unit tests
- Project/lifescyle management: maven
- IDE: IntelliJ Idea Ultimate + vim extension + custom configuration with Google Java Code Style
- Container technology: docker, containerd
- Docker image hosting: will be hosted on docker hub
- Docker image creation: created by docker build 
- Docker base image: openjdk8-alpine, an official image from dockerhub.io
- Defining and/or using interfaces will be preferred in this project, for maintainability
- Using exceptions will be preferred as error handling mechanism
- Using two different versions of the JavaGrep interface (one procedural style, one functional/stream oriented) is preferred to handle different scenarios of eager vs. lazy processing and their impact on performance vs. memory consumption


# Quick Start

### From local installation:

`java -jar [GREP_JAR_DIR/]grep.jar {REGEX_PATTERN} {SOURCE_DIR} {OUTPUT_FILE}`

### From docker image:

```
docker run --rm \
-v {YOUR_ROOT_DATA_DIR}:/data -v {YOUR_OUTPUT_FILE_DIR}:/out jrvs/grep \
{REGEX_PATTERN} {SOURCE_DIR} {OUTPUT_FILE}
```

### Examples:


`java -jar grep.jar  ./my_source_dir ./out/my_output_file.txt`

`java -jar grep.jar  ./my_source_dir # To Stdout`

`java -jar grep.jar  ./my_source_dir > ./out/my_output_file.txt  # Redirect from Stdout`

```
docker run --rm \
-v $(pwd)/data:/data -v $(pwd)/out:/out \
${docker_user}/grep .*Tristan.*Isolde.* /data /out/grep_out.txt
```

# Implemenation
The project defines 2 different interfaces for the same `grep`-like process, to explore the cost/benefit of each.
The 2 interfaces and their basic implementations are:

## 1. A procedural oriented interface
The benefit of implementing this interface is that it is straightforward, mature, easy to understand (at this stage) and delivers the best performance out of Java 8 JVM on lower cores, non multi-threaded and simple applications.

### Pseudocode
procedural pseudocode:

```
matchedLines = []
for file in listFilesRecursively(rootDir)
  for line in readLines(file)
      if containsPattern(line)
        matchedLines.add(line)
writeToFile(matchedLines)
```

### Performance Issue
On the performance side, more generally, the most expensive function is the regex pattern match. As Java 8 regex is based on Nondeterministic Finite Automata (NFA), many references, including this [one](https://en.wikipedia.org/wiki/Nondeterministic_finite_automaton#:~:text=Keep%20a%20set,(s).) point to an O(nxm) complexity, considering that the regex matching algorith is the same, and the pattern is constant.
The outer looping through the lines of the files' collection brings it to O(n^2), n being the number of characters
The potential cost of the procedural interface would be O(n) memory growth, n being the total number of characters

As we plan to run our app in a container, we aim to make the app use as little memory as possible.
However, this could be a challenge, as procedural style makes most if not all operations _eager_ instead of lazy

## 2. A functional + stream oriented interface
A functional+stream paradigm brings many benefits: 
- in many cases, it is concise and expressive.
- the intended logic is usually clearer, especially when using Function References and an expressive naming scheme.
- it can be more easily optimized for parallelization thanks to the functional paradigm (so side effects particularily)

### Pseudocode
functional+stream pseudocode v1:

```
listFiles(rootPath).forEach(path -> 
        matchesLinesStream.accept( readLines(path));
    
 matchesLinesStream
        .map(stringStream -> stringStream.filter(this::containsPattern))
        .forEach(stringStream -> writeToFile(stringStream))
```


### Performance Issue
The stream and lambda features of Java 8 can be more costly, as lambads and function references are [compiled at runtime](https://blogs.oracle.com/javamagazine/post/behind-the-scenes-how-do-lambda-expressions-really-work-in-java#:~:text=The%20actual%20type%20does%20not%20exist%20at%20compile%20time%20and%20will%20be%20created%20on%20demand%20at%20runtime "The actual type does not exist at compile time and will be created on demand at runtime")

This might show as a greater impact for apps similar to `grep` as it is usually invoked once, thus making the lambda and stream invokation expensive.
The perfromance of lambas is not expected to be better than the procedural implementation on mono-core cpus. However, lambdas would shine on multicore or parallel architectures.

However, the greatet benefit of the stream and lambdas combo is expected to be lesser memory need and delaying/averting an [`OutOfMemoryError exception`](https://docs.oracle.com/javase/8/docs/technotes/guides/troubleshoot/memleaks002.html "Oracle Documentation: Understand the OutOfMemoryError Exception") as space complexity is depedent on the maximum available heap and buffersize but constant, making it O(1) for a given configuration.

As opposed to the procedural approach, the combination of streams and lambdas make the _lazy_ evaluation more memory efficient. This can be further combined with using streams through the whole chain of functions, including writing to `outputFile`

## Other implementation considerations
In order to make the code cleaner and easier to update, this project will add a `GetOpts` class to handle command-line arguments.
- This will make the code cleaner and encapsulate managing the commandline arguments concern into its own class.
- One of the easy features that can be added this way is validation of the arguments
- Another simple feature update through this class could be adding a variable number of arguments, and named argument instead of, or besides, positional arguments.

# Test
The Grep Project uses, for now, 2 types of testing:

- Junit5 testing framework for unit tests
- manually running a collection of patterns through Linux `grep`, collecting the results, then comparing these known correct results with app output.

# Deployment
For deployement, we will first need to:
1. choose a base JDK image on which we will layer our build: [openjdk:8-alpine](https://hub.docker.com/layers/openjdk/library/openjdk/8-alpine/images/sha256-a3562aa0b991a80cfe8172847c8be6dbf6e46340b759c2b782f8b8be45342717?context=explore). It is a lightweight and official image from dockerhub.
2. then create a Dockerfile
```
FROM openjdk:8-alpine
COPY target/grep*.jar /usr/local/app/grep/lib/grep.jar
ENTRYPOINT ["java","-jar","/usr/local/app/grep/lib/grep.jar"]
```
3. create an uber-jar to simplify the current and future classpath dependency issues.
  This is done through `maven shade` plugin https://maven.apache.org/plugins/maven-shade-plugin/, then running:
  `mvn clean verify compile package`

4. This allows to build a local uber-jar, against which we can run our Dockerfile:
  `docker build -t ${YOUR_DOCKER_REPO_LOGIN}/grep`
  
5. then push the new artifact to dockerhub.io:
  
  `docker push ${YOUR_DOCKER_REPO_LOGIN}/grep`

5. The artifact is now available on docker hub through:
  `docker pull ${YOUR_DOCKER_REPO_LOGIN}/grep`

# Improvement
A few immediate improvements come to mind:
1. Getopts: can assign `outputFile` to `STDOUT` in case the last argument is missing/optional
2. Getopts: add an optional named arguments
4. Getopts: Add validations for input arguments
5. JavaGrep implementation: Add reasonnable lower and upper limits to filesystem parameters, such as filesize, number of files, directory depth
6. JavaGrep implementations: Improve exception handling
7. JavaGrep procedural implementation: improve memory usage
8. JavaGrep functional implementation: simplify and stream line the stream
9. JavaGrep interfaces: unify as much as possible the procedural and functional interfaces



Nissa Elchhab





