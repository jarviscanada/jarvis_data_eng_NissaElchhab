
# Introduction: Core Java / Grep
The Grep project aims to implement a simple `grep`-like app in Java.
Just like Linux `grep`, the app scans all the files within a directory and its sub-directories (recursively), searching for a text pattern within that collection of files, then outputs matched lines to a file.

The app takes three positional arguments on the commandline:
- a regexPattern: a special text string for describing a search pattern, restricted for now to Java [https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html]Regex definition
- a sourceDirectory: root directory path to find the collection of files to search into
- an outputFile: output file name

Technologies used:
------------------
- Core Java 8 (version 1.8): the [https://www.jetbrains.com/lp/devecosystem-2019/java/#:~:text=Although%20Java%2010%20and%2011,still%20the%20most%20used%20version.]most used Java version.
- Openjdk-8: FOSS and reference build of Oracle Java
- Logging libraries: Slf4j is favoured as a logger interface, Log4j2 for implementation
- Testing library: JUnit5 for unit test
- Project/lifescyle management: maven
- IDE: IntelliJ Idea Ultimate + vim extension + custom configuration with Google Java Code Style
- Container technology: docker, containerd
- Docker image hosting: will be hosted on docker hub
- Docker image creation: created by docker compose 
- Docker base image: openjdk8-alpine from dockerhub.io
- Defining and/or using interfaces will be preferred in this project, for maintainability
- Using exceptions will be preferred as error handling mechanism
- Using two different versions of the JavaGrep interface (one procedural style, one functional/stream oriented) is preferred to handle different scenarios of eager vs lazy processing and their impact on performance vs. memory consumption


# Quick Start

From local installation:
------------------------
`java -jar [GREP_JAR_DIR/]grep.jar {REGEX_PATTERN} {SOURCE_DIR} {OUTPUT_FILE}`

From docker image:
-------------------
```
docker run --rm \
-v {YOUR_ROOT_DATA_DIR}:/data -v {YOUR_OUTPUT_FILE_DIR}:/out jrvs/grep \
{REGEX_PATTERN} {SOURCE_DIR} {OUTPUT_FILE}
```

Examples:
---------

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
-------------------------------
The benefit of implementing this interface is that it is straightforward, easy to understand (at this stage) and delivers the best performance out of Java 8 JVM on lower cores, non multi-threaded and simple applications (add citations and references).

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
On the performance side, more generally, the most expensive function is the regex pattern match. As Java 8 regex is based on Nondeterministic Finite Automata (NFA), many references, including this one [https://en.wikipedia.org/wiki/Nondeterministic_finite_automaton#:~:text=Keep%20a%20set,(s).] points to an O(n) complexity, considering that the regex matching algorith is the same, and the pattern is constant.
The outer looping through the lines of the files' collection brings it to O(n^2), n being the number of characters
The potential cost of the procedural interface would be O(n) memory growth, n being the total number of characters

(30-60 words)
Discuss the memory issue and how would you fix it
stream uses lazy operations

## 2. A functional + stream oriented interface
------------------------------------------
A functional+stream has many benefits: 
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
The stream and lambda features of Java 8 can be more costly, as lambads and function references are compiled at runtime [The actual type does not exist at compile time and will be created on demand at runtime]
This might show as a greater impact for apps similar to `grep` as it is usually invoked once, thus making the lambda and stream invokation expensive.
The perfromance of lambas is not expected to be better than the procedural implementation on mono-core cpus. however, lambdas would shine on multicore or parallel architectures.
However, the greatet benefit of the stream and lambdas combo is expected to be lesser memory need and delaying/averting `OutOfMemoryException` as space complexity is depedent on the maximum available RSS and buffersize, making it O(1) for a given configuration.

(30-60 words)
Discuss the memory issue and how would you fix it
stream uses lazy operations

## Other implementation considerations
--------------------------------------
In order to make the code cleaner and easier to update, this project will add a `GetOpts` class to handle command-line arguments.
- This will make the code cleaner and encapsulate managing the cli arguments concern into its own class.
- One of the easy features that can be added this way is validation of the arguments
- Another simple feature update through this class could be adding a variable number of arguments, and named argument instead of, or besides, positional arguments.

# Test

How did you test your application manually? (e.g. prepare sample data, run some test cases manually, compare result)
Junit tests
verificatiom samples
todo use output from original grep as sample

# Deployment
How you dockerize your app for easier distribution?

use uber jar

# Improvement
List three things you can improve in this project.
## User stories, examples and specifications:





