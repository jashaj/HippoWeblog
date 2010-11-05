MAVEN_OPTS="-Xms128m -Xmx256m -XX:PermSize=128m" 
export MAVEN_OPTS
nohup mvn clean jetty:run-war -DskipTests -Drepo.path=/home/jasha/repos/repo003 &
