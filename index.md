## Spring template engine performance tests
### taken at Sun Dec 25 11:07:36 UTC 2022

lower is the better
<div id="chart_div"></div>

|Engine Name | Seconds|
|------------|--------|
|jsp | 4.393|
|velocity | 3.118|
|freemarker | 3.384|
|thymeleaf | 7.097|
|mustache | 3.459|
|jade | 128.553|
|pebble | 9.484|
|handlebars | 19.632|
|scalate | 9.847|
|httl | 4.241|
|chunk | 4.972|
|htmlFlow | 3.462|
|trimou | 3.697|
|rocker | 3.626|
|ickenham | 5.943|
|rythm | 3.912|
|groovy | 835.431|
|liqp | 7.490|
|kotlinx | 4.355|

results taken from mvn and jvm :Apache Maven 3.8.6 (84538c9988a25aec085021c365c560670ad80f63)
Maven home: /usr/share/apache-maven-3.8.6
Java version: 17.0.5, vendor: Eclipse Adoptium, runtime: /usr/lib/jvm/temurin-17-jdk-amd64
Default locale: en, platform encoding: UTF-8
OS name: "linux", version: "5.15.0-1024-azure", arch: "amd64", family: "unix"

Runs performance test(ab -q -n 10000 -c 10 http://localhost:8080/TEMPLATE) from [Github Actions](https://github.com/ozkanpakdil/spring-comparing-template-engines/actions) and updates here.

If you are planning to use any template engine from the list, choose wisely, lowest is the best performance. 

<div id="disqus_thread"></div>
<script src="https://www.gstatic.com/charts/loader.js"></script>

<script type="text/javascript">
    /* * * CONFIGURATION VARIABLES * * */
    var disqus_shortname = 'ozkanpakdil';
    
    /* * * DON'T EDIT BELOW THIS LINE * * */
    (function() {
        var dsq = document.createElement('script'); dsq.type = 'text/javascript'; dsq.async = true;
        dsq.src = '//' + disqus_shortname + '.disqus.com/embed.js';
        (document.getElementsByTagName('head')[0] || document.getElementsByTagName('body')[0]).appendChild(dsq);
    })();
    
    google.charts.load('current', {
          packages: ['corechart'],
          callback: drawChart
        });

        const chartOptions = {
          width: 600,
          height: 600,
          annotations: {
            textStyle: {
              fontName: 'Times-Roman',
              fontSize: 10,
              bold: false,
              italic: false,
              // The color of the text.
              color: '#871b47',
              // The color of the text outline.
              auraColor: '#d799ae',
              // The transparency of the text.
              opacity: 0.8
            }
          }
        };

        function drawChart() {
          var tableRows = [];
          var results = document.getElementsByTagName('table');
          Array.prototype.forEach.call(results[0].rows, function (row) {
            var tableColumns = [];
            Array.prototype.forEach.call(row.cells, function (cell) {
              var cellText = cell.textContent || cell.innerText;
              if (parseFloat(cellText) > 20)
                return; //continue;
              switch (cell.cellIndex) {
                case 0:
                  tableColumns.push(cellText.trim());
                  break;

                default:
                  switch (row.rowIndex) {
                    case 0:
                      tableColumns.push(cellText.trim());
                      break;
                    default:
                      tableColumns.push(parseFloat(cellText));
                  }
              }
            });
            tableRows.push(tableColumns);
          });

          var data = google.visualization.arrayToDataTable(tableRows.filter(function (el) {
            return el[1] != null;
          }));
          const newDiv = document.createElement("div");
          var chart = new google.visualization.ColumnChart(newDiv);
          chart.draw(data, chartOptions);
          document.getElementById('chart_div').append(newDiv);
        }
</script>
<!-- Google tag (gtag.js) -->
<script async src="https://www.googletagmanager.com/gtag/js?id=UA-77642-34"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());

  gtag('config', 'UA-77642-34');
</script>


