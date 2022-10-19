
<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
<script>
    (adsbygoogle = window.adsbygoogle || []).push({
         google_ad_client: "ca-pub-7118095690658891",
         enable_page_level_ads: true
    });
</script>

## Spring template engine performance tests

Runs performance test(ab -q -n 10000 -c 10 http://localhost:8080/TEMPLATE) from [Github Actions](https://github.com/ozkanpakdil/spring-comparing-template-engines/actions) and updates here.

### Results from Wed Oct 19 10:24:59 UTC 2022
results taken from mvn and jvm :Apache Maven 3.8.6 (84538c9988a25aec085021c365c560670ad80f63)
Maven home: /usr/share/apache-maven-3.8.6
Java version: 11.0.17, vendor: Azul Systems, Inc., runtime: /opt/hostedtoolcache/jdk/11.0.17/x64
Default locale: en, platform encoding: UTF-8
OS name: "linux", version: "5.15.0-1020-azure", arch: "amd64", family: "unix"

|Engine Name | Seconds|
|------------|--------|
|jsp | 6.197|
|velocity | 4.939|
|freemarker | 4.739|
|thymeleaf | 10.853|
|mustache | 5.463|
|jade | 199.262|
|pebble | 6.199|
|handlebars | 24.204|
|scalate | 10.200|
|httl | 5.554|
|chunk | 5.107|
|htmlFlow | 3.701|
|trimou | 4.049|
|rocker | 3.873|
|ickenham | 7.018|
|rythm | 4.937|
|groovy | 1147.600|
|liqp | 8.934|
|kotlinx | 4.257|

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

