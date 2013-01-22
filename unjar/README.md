Unjar Performance Test
======================

# How to  run

1. compile using javac

    $mkdir perfunjarfs

    $javac -classpath ${HAOOP_HOME}/hadoop-core-1.0.4.jar -d perfunjarfs  PerfunJarFS.java


2. generate a Jar file

    $jar -cvf perfunjarfs.jar -C perfunjarfs/ .

3. Run it

    $java -classpath $HADOOP_HOME/hadoop-core-1.0.4.jar:perfunjarfs.jar:$HADOOP_HOME/lib/log4j-1.2.15.jar:$HADOOP_HOME/lib/commons-logging-1.1.1.jar:$HADOOP_HOME/lib/*.jar:$HADOOP_HOME/lib/commons-configuration-1.6.jar:$HADOOP_HOME/lib/commons-lang-2.4.jar  com.lingcc.hadoop.performance.PerfunJarFS mahout-examples-0.7-job.jar ./


# For the input
  we recommand using the mahout-examples-0.7-job.jar which contain 18000 files.


# The testresult:

1. Local Unjar
    about 10s

2. HDFS Unjar
    about 3min

