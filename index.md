## Spring template engine performance tests
### taken at Thu Oct 20 07:26:47 UTC 2022

|Engine Name | Seconds|
|------------|--------|
|jsp | 6.533|
|velocity | 5.014|
|freemarker | 5.267|
|thymeleaf | 10.646|
|mustache | 4.784|
|jade | 130.902|
|pebble | 4.541|
|handlebars | 18.274|
|scalate | 7.815|
|httl | 3.622|
|chunk | 3.525|
|htmlFlow | 2.527|
|trimou | 2.800|
|rocker | 2.740|
|ickenham | 5.456|
|rythm | 3.486|
|groovy | 867.855|
|liqp | 7.802|
|kotlinx | 3.065|

results taken from mvn and jvm :Apache Maven 3.8.6 (84538c9988a25aec085021c365c560670ad80f63)
Maven home: /usr/share/apache-maven-3.8.6
Java version: 17.0.4.1, vendor: Eclipse Adoptium, runtime: /usr/lib/jvm/temurin-17-jdk-amd64
Default locale: en, platform encoding: UTF-8
OS name: "linux", version: "5.15.0-1020-azure", arch: "amd64", family: "unix"

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

