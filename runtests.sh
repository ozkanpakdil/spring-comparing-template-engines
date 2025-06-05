#!/bin/bash
# Kill any existing instances of the application
pkill -f template-engines.war || true
pkill -f "spring-boot:run" || true
set -xe
JVMVER=$1
TESTS=(jsp velocity freemarker thymeleaf mustache jade pebble handlebars scalate httl chunk htmlFlow trimou rocker ickenham rythm groovy kotlinx jte)
> result-$1.txt

javaver=`mvn -version`
# Run Spring Boot application using Maven instead of WAR file
mvn spring-boot:run &
JPID=$!

# Function to check if the application is ready
wait_for_app() {
    local max_attempts=90  # Increased to allow more time for Maven Spring Boot startup
    local attempt=1
    local sleep_time=2

    echo "Waiting for Spring Boot application to start..."
    while [ $attempt -le $max_attempts ]; do
        if curl -s http://localhost:8080/jsp >/dev/null 2>&1; then
            echo "Application is up and running!"
            return 0
        fi
        echo "Attempt $attempt of $max_attempts: Application is not ready yet..."
        sleep $sleep_time
        attempt=$((attempt + 1))
    done

    echo "Application failed to start within the expected time"
    # Properly terminate the Maven process and its child processes
    # Using both pkill and kill to ensure all related processes are terminated
    # pkill targets any process with "spring-boot:run" in its command line
    # kill targets the specific Maven process we started
    pkill -f "spring-boot:run" || true
    kill $JPID 2>/dev/null || true
    exit 1
}

# Wait for the application to be ready
wait_for_app

echo "|Engine Name | Seconds|" >> result-$1.txt
echo "|------------|--------|" >> result-$1.txt
for template in "${TESTS[@]}"; do
  # Check if endpoint returns 200 status code
  status_code=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8080/$template)
  if [ "$status_code" = "200" ]; then
    # Run ab command with a timeout of 60 seconds (1 minute)
    if result=$(timeout 160s bash -c "ab -q -n 10000 -c 20 http://localhost:8080/$template | grep 'Time taken for tests' | grep -Eo '[+-]?[0-9]+([.][0-9]+)?'"); then
      echo "|$template | $result|" >> result-$1.txt
      echo "✅ $template test completed successfully"
    else
      # If timeout occurred or command failed
      echo "|$template | too slow|" >> result-$1.txt
      echo "⚠️ $template test took more than 1 minute - marked as too slow"
    fi
  else
    echo "❌ $template endpoint returned $status_code - skipping test"
  fi
done
# Properly terminate the Maven process and its child processes
# Using both pkill and kill to ensure all related processes are terminated
# pkill targets any process with "spring-boot:run" in its command line
# kill targets the specific Maven process we started
pkill -f "spring-boot:run" || true
kill $JPID 2>/dev/null || true
git config pull.rebase true
sleep $[ ( $JVMVER % 20 ) + 1 ]s


sonuc=`cat result-$1.txt`
date=`date`

cat > index.md <<EOL
## Spring template engine performance tests
### taken at $date

lower is the better
<div id="chart_div"></div>

$sonuc

results taken from mvn and jvm :$javaver

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


EOL
exit 0
