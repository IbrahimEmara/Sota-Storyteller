# SOTAStories

Instructions:

To run on SOTA command line:

1.Will need Maven installed on command line, https://maven.apache.org/download.cgi It is enough to download one of the binary folders.

2. Go to root folder and run : 
mvn clean install
mvn compile
mvn dependency:copy-dependencies

3. Copy all jars in the target/dependency folder into the lib folder

4. Will need Google Cloud free trial subscription, with Google Speech enabled after subscribing, follow this link: https://cloud.google.com/docs/authentication/getting-started

5. Set environment variable with command: export GOOGLE_APPLICATION_CREDENTIALS="/home/user/Downloads/service-account-file.json" //replace json file with your json file

6. Go to target/classes folder and run:
chmod +x java_run.sh
./java_run.sh Main


To run on Eclipse: 
1. Import this project as a Maven project (may need to download m2e)

2. Will need Google Cloud free trial subscription, with Google Speech enabled after subscribing, follow this link: https://cloud.google.com/docs/authentication/getting-started

3. Set GOOGLE_APPLICATION_CREDENTIALS environment variable on eclipse by right clicking on Main class then select Run As -> Run Configurations -> Environment.

4.Run Main class as a Java Project

To transfer to SOTA:

Follow this page to find out how to connect with SSH: https://translate.google.com/translate?act=url&depth=1&hl=en&ie=UTF8&prev=_t&rurl=translate.google.com&sl=ja&sp=nmt4&tl=en&u=http://www.vstone.co.jp/sotamanual/index.php%3F%25E3%2583%258D%25E3%2583%2583%25E3%2583%2588%25E3%2583%25AF%25E3%2583%25BC%25E3%2582%25AF%25E7%25B5%258C%25E7%2594%25B1%25E3%2581%25A7%25E3%2583%25AD%25E3%2582%25B0%25E3%2582%25A4%25E3%2583%25B3%25E3%2581%2599%25E3%2582%258B

Also, navigate to "Try Progamming in Java" in the Menu to set up Ant on Eclipse.


