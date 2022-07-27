
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
- Defining and/or using interfaces will be preferred in this project, for maintainability
- Using exceptions will be preferred as error handling mechanism
- Using two different versions of the JavaGrep interface (one procedural style, one functional/stream oriented) is preferred to handle different scenarios of eager vs lazy processing and their impact on performance vs. memory consumption
- Logging libraries: Slf4j is favoured as a logger interface, Log4j2 for implementation
- Testing library: JUnit5
- Project/lifescyle management: maven
- IDE: IntelliJ Idea Ultimate + vim extension + custom configuration with Google Java Code Style
- Container technology: docker, containerd
- Docker image hosting: will be hosted on docker hub
- Docker image creation: created by docker compose 
- Docker base image: openjdk8-alpine from dockerhub.io

# Quick Start

From local installation:
------------------------
`java -jar [GREP_JAR_DIR/]grep.jar {REGEX_PATTERN} {SOURCE_DIR} {OUTPUT_FILE}`

From docker image:
-------------------
`docker run --rm \
-v {YOUR_ROOT_DATA_DIR}:/data -v {YOUR_OUTPUT_FILE_DIR}:/out jrvs/grep \
{REGEX_PATTERN} {SOURCE_DIR} {OUTPUT_FILE}`

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

#Implemenation
2 implemetations one stream for memory constrained and one procedural for perf
add interface to handle command line similar
## Pseudocode
write `process` method pseudocode.
procedural:

matchedLines = []
for file in listFilesRecursively(rootDir)
  for line in readLines(file)
      if containsPattern(line)
        matchedLines.add(line)
writeToFile(matchedLines)

## Performance Issue
(30-60 words)
Discuss the memory issue and how would you fix it
stream uses lazy operations

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





