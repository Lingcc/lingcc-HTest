#!/bin/bash

## This script trys to deliver conf and jar to a list of host.
## unfinished yet

TOOL_HOME=""

case $1 in
hadoop )
	TOOL_HOME=$HADOOP_HOME
	;;

hbase )
	TOOL_HOME=$HBASE_HOME
	;;


hive )
	TOOL_HOME=$HIVE_HOME
	;;

pig )
	TOOL_HOME=$PIG_HOME
	;;


mahout )
	TOOL_HOME=$MAHOUT_HOME
	;;


sqoop )
	TOOL_HOME=$SQOOP_HOME
	;;

* )
	echo "unknown option"
	exit 1
	;;
esac


case $2 in

conf )
	scp -r $TOOL_HOME/conf/ root@hbase62:$TOOL_HOME/
	scp -r $TOOL_HOME/conf/ root@hbase76:$TOOL_HOME/
	scp -r $TOOL_HOME/conf/ root@hbase80:$TOOL_HOME/
	;;
jar )
	scp $TOOL_HOME/*.jar root@hbase62:$TOOL_HOME/
	scp $TOOL_HOME/*.jar root@hbase76:$TOOL_HOME/
	scp $TOOL_HOME/*.jar root@hbase80:$TOOL_HOME/
	;;
* )
	echo "unknown option"
	exit 1
	;;
esac
