classpath=".:/home/root/SOTAStories/lib/*:/home/vstone/lib/*\
:/home/vstone/vstonemagic/*\
:/usr/local/share/OpenCV/java/*\
"
OPTION="-Dfile.encoding=UTF8 -Djava.library.path=/usr/local/share/OpenCV/java/"
# echo "java -javaagent:/home/root/LeChefuMaven/lib/jetty-alpn-agent-2.0.0.jar -classpath $classpath $OPTION $1"
echo "java -classpath $classpath $OPTION $1"
java -Xbootclasspath/p:/home/root/SOTAStories/lib/alpn-boot-8.1.3.v20150130.jar -classpath "$classpath" $OPTION $1 -javaagent:"/home/root/SOTAStories/lib/jetty-alpn-agent-2.0.0.jar" $OPTION $1

