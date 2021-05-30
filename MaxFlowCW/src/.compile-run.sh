#!/bin/bash

./build.sh
java -cp out/ Main -file '../benchmarks/bridge_1.txt'
