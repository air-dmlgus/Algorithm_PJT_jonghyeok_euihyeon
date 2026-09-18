#!/usr/bin/env sh
set -eu
[ -f target/yumyum-coach.jar ] || mvn clean test package
exec java -jar target/yumyum-coach.jar
