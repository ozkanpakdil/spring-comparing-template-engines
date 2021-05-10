#!/bin/bash
set -x
JVMVER=$1
TESTS=(jsp velocity freemarker thymeleaf mustache jade pebble handlebars jtwig scalate httl chunk htmlFlow trimou rocker ickenham rythm groovy liqp kotlinx)
> result-$1.txt

mvn spring-boot:run > log.log & 
JPID=$!

for ip in "${TESTS[@]}"; do
  result=`ab -q -n 1000 -c 10 http://localhost:8080/$ip | grep "Time taken for tests"`
  echo "$ip $result" >> result-$1.txt
done
kill -9 $JPID
git pull
sleep $[ ( $RANDOM % 20 ) + 1 ]s
exit 0