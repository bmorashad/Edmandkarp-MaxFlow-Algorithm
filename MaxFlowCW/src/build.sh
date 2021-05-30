#!/bin/bash
javac -sourcepath ./ -d out/ -classpath ./graph/networkflow/maxflow/*.java
javac -d out Main.java
