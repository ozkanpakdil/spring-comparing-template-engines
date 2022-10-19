
<script async src="//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
<script>
    (adsbygoogle = window.adsbygoogle || []).push({
         google_ad_client: "ca-pub-7118095690658891",
         enable_page_level_ads: true
    });
</script>

## Spring template engine performance tests

Runs performance test(ab -q -n 10000 -c 10 http://localhost:8080/TEMPLATE) from [Github Actions](https://github.com/ozkanpakdil/spring-comparing-template-engines/actions) and updates here.

### Results from Wed Oct 19 16:08:15 UTC 2022
results taken from mvn and jvm :Apache Maven 3.8.6 (84538c9988a25aec085021c365c560670ad80f63)
Maven home: /usr/share/apache-maven-3.8.6
Java version: 17.0.4.1, vendor: Eclipse Adoptium, runtime: /usr/lib/jvm/temurin-17-jdk-amd64
Default locale: en, platform encoding: UTF-8
OS name: "linux", version: "5.15.0-1021-azure", arch: "amd64", family: "unix"

|Engine Name | Seconds|
|------------|--------|
|jsp | 5.820|
|velocity | 4.488|
|freemarker | 4.412|
|thymeleaf | 9.505|
|mustache | 4.726|
|jade | 201.620|
|pebble | 5.937|
|handlebars | 24.128|
|scalate | 10.253|
|httl | 4.875|
|chunk | 4.530|
|htmlFlow | 3.367|
|trimou | 3.681|
|rocker | 3.549|
|ickenham | 6.710|
|rythm | 4.504|
|groovy | 1066.008|
|liqp | 8.997|
|kotlinx | 4.571|

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

