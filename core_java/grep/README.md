# Core Java: Grep

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

