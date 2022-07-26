
# Introduction: Core Java / Grep
Implement a simple grep app in Java. The app searches for a text pattern recursively in a given directory, and output matched lines to a file. The app takes three arguments:
core java 8
openjdk-8
focus on a well defined interfaces
uses exceptions
procedural
functional
logging libraries: Slf4j, Log4j2
testing using JUnit5
maven
IntelliJ Idea Ultimate
docker hub, docker compose
docker base image openjdk8-alpine
Google Java coding standards

(50-100 words)
Discuss the design of each app. What does the app do? What technologies have you used? (e.g. core java, libraries, lambda, IDE, docker, etc..)

# Quick Start
How to use your apps? 

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

Usage: `grepapp`  regex rootPath outFile
- regex: a special text string for describing a search pattern
- rootPath: root directory path
- outFile: output file name

Similar to:
    `egrep -r {regex} {rootPath} > {outFile}`

## Examples:

```
regex=".*Romeo.*Juliet.*"
rootPath="./data"
outFile="grepapp_outfile.txt"
egrep -r ${regex_pattern} ${src_dir} > outFile
./data/txt/shakespeare.txt:    Is father, mother, Tybalt, Romeo, Juliet,
./data/txt/shakespeare.txt:Enter Romeo and Juliet aloft, at the Window.
./data/txt/shakespeare.txt:    And Romeo dead; and Juliet, dead before,
./data/txt/shakespeare.txt:    Romeo, there dead, was husband to that Juliet;
```
### As a .jar file
```
#Download jar file
wget -O grep-demo.jar https://github.com/jarviscanada/jarvis_data_eng_demo/raw/feature/grep_demo_jar/core_java/grep/target/grep-1.0-SNAPSHOT.jar
#Run the grep app
outfile=grep_$(date +%F_%T).txt
java -jar grep-demo.jar ${regex_pattern} ${src_dir} ./out/${outfile}
#verify 
cat out/$outfile
```
### As a docker image
```
#Approach 2: Docker image
outfile=grep_$(date +%F_%T).txt
docker run --rm \
-v `pwd`/data:/data -v `pwd`/out:/out jrvs/grep \
${regex_pattern} ${src_dir} /out/${outfile}
#verify 
cat out/$outfile
```

