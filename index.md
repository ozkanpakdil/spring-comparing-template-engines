## Spring template engine performance tests
### taken at Fri Nov  4 12:30:25 UTC 2022

lower is the better

|Engine Name | Seconds|
|------------|--------|
|jsp | 6.012|
|velocity | 4.504|
|freemarker | 4.701|
|thymeleaf | 9.942|
|mustache | 4.884|
|jade | 215.379|
|pebble | 6.057|
|handlebars | 24.967|
|scalate | 10.475|
|httl | 5.098|
|chunk | 4.735|
|htmlFlow | 3.464|
|trimou | 3.817|
|rocker | 3.754|
|ickenham | 7.301|
|rythm | 4.854|
|groovy | 1131.989|
|liqp | 10.059|
|kotlinx | 4.889|

results taken from mvn and jvm :Apache Maven 3.8.6 (84538c9988a25aec085021c365c560670ad80f63)
Maven home: /usr/share/apache-maven-3.8.6
Java version: 17.0.4.1, vendor: Eclipse Adoptium, runtime: /usr/lib/jvm/temurin-17-jdk-amd64
Default locale: en, platform encoding: UTF-8
OS name: "linux", version: "5.15.0-1022-azure", arch: "amd64", family: "unix"

Runs performance test(ab -q -n 10000 -c 10 http://localhost:8080/TEMPLATE) from [Github Actions](https://github.com/ozkanpakdil/spring-comparing-template-engines/actions) and updates here.

If you are planning to use any template engine from the list, choose wisely, lowest is the best performance. 

<div id="disqus_thread"></div>
<script type="text/javascript">
    /* * * CONFIGURATION VARIABLES * * */
    var disqus_shortname = 'ozkanpakdil';
    
    /* * * DON'T EDIT BELOW THIS LINE * * */
    (function() {
        var dsq = document.createElement('script'); dsq.type = 'text/javascript'; dsq.async = true;
        dsq.src = '//' + disqus_shortname + '.disqus.com/embed.js';
        (document.getElementsByTagName('head')[0] || document.getElementsByTagName('body')[0]).appendChild(dsq);
    })();
</script>

