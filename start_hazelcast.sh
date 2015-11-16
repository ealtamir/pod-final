#!/bin/bash


echo "Setting CLASSPATH."
export CLASSPATH=./target/classes:./hazelcast.xml:./lib/hazelcast-all-3.5.2.jar:./out/artifacts/imdb_parser_jar/imdb_parser.jar
echo ">>>> Running"
java com.hazelcast.console.ConsoleApp

