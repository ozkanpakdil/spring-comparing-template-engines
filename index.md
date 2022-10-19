## Spring template engine performance tests

### Results from Wed Oct 19 22:09:47 UTC 2022
|Engine Name | Seconds|
|------------|--------|
|jsp | 3.892|
|velocity | 2.847|
|freemarker | 2.941|
|thymeleaf | 6.524|
|mustache | 3.069|
|jade | 113.584|
|pebble | 3.877|
|handlebars | 16.579|
|scalate | 7.139|
|httl | 3.121|
|chunk | 3.051|
|htmlFlow | 2.229|
|trimou | 2.427|
|rocker | 2.337|
|ickenham | 4.615|
|rythm | 3.018|
|groovy | 773.485|
|liqp | 7.273|
|kotlinx | 2.996|

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

