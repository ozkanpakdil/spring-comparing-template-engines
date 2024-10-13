@param java.lang.Iterable<com.jeroenreijn.examples.model.Presentation> presentations
@param String contextPath
@param com.jeroenreijn.examples.model.i18nLayout i18n

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
    <title>${i18n.message("example.title")} - JTE</title>
    <link rel="stylesheet" href="${contextPath}/webjars/bootstrap/css/bootstrap.min.css" media="screen"/>
</head>
<body>
<div class="container">
    <div class="pb-2 mt-4 mb-3 border-bottom">
        <h1>${i18n.message("example.title")} - JTE</h1>
    </div>

    @for(var presentation : presentations)
        <div class="card mb-3 shadow-sm rounded">
            <div class="card-header">
                <h5 class="card-title">${presentation.getTitle()} - ${presentation.getSpeakerName()}</h5>
            </div>
            <div class="card-body">
                ${presentation.getSummary()}
            </div>
        </div>
    @endfor

</div>
<script src="${contextPath}/webjars/jquery/jquery.min.js"></script>
<script src="${contextPath}/webjars/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>