#!/bin/bash

java -cp bin:antlr-runtime-4.9.3.jar Compiler
clang -o code lib/builtin.ll output.ll -m32

./code