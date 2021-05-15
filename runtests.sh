#!/bin/bash
set -x
git config --global user.email "action@github.com"
git config --global user.name "GitHub Action"

JVMVER=$1
TESTS=(jsp velocity freemarker thymeleaf mustache jade pebble handlebars jtwig scalate httl chunk htmlFlow trimou rocker ickenham rythm groovy liqp kotlinx)
> result-$1.txt

mvn -version
mvn -B spring-boot:run &
JPID=$!
sleep 80 # waiting for spring boot to start

for template in "${TESTS[@]}"; do
  result=`ab -q -n 1000 -c 10 http://localhost:8080/$template | grep "Time taken for tests"`
  echo "$template $result" >> result-$1.txt
done
kill -9 $JPID
git config pull.rebase true
sleep $[ ( $JVMVER % 20 ) + 1 ]s


sonuc=`cat result-$1.txt`
date=`date`
javaver=`java -version`

cat > index.md <<EOL

<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
<script>
    (adsbygoogle = window.adsbygoogle || []).push({
         google_ad_client: "ca-pub-7118095690658891",
         enable_page_level_ads: true
    });
</script>

## Spring template engine performance tests

Runs performance test(ab -q -n 1000 -c 10 http://localhost:8080/$template) from [Github Actions](https://github.com/ozkanpakdil/spring-comparing-template-engines/actions) and updates here.

### Results from $date
results taken from jvm :$javaver
\`\`\`
$sonuc
\`\`\`

EOL
exit 0