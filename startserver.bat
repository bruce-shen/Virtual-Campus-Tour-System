javac *.java
rmic ServerImpl
start rmiregistry
java -Djava.security.policy=Policy.policy ServerImpl