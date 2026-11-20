#!/usr/bin/env bash
set -euo pipefail
base="$(cd "$(dirname "$0")" && pwd)"
for folder in "$base"/0[1-3]-*; do
    mkdir -p "$folder/build"
    javac -encoding UTF-8 --release 11 -d "$folder/build" "$folder/Main.java" "$folder/TestCases.java"
    java -cp "$folder/build" TestCases
done
