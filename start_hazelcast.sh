#!/bin/bash


echo "Setting CLASSPATH."
export CLASSPATH=./target/classes:./hazelcast.xml:./lib/hazelcast-all-3.5.2.jar
echo ">>>> Running"
java com.hazelcast.console.ConsoleApp

