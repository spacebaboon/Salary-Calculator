<html>
    <head>
        <title>Salary Calculator</title>
        <meta name="layout" content="main" />
        <link rel="stylesheet" href="../css/screen.css" type="text/css" media="screen, projection">
    </head>
    <body>
        <div class="title">
            <h1>Salary Calculator</h1>
        </div>
        <div>
        <g:form controller="calculator">
            <g:textField name="annualSalary"/>
            <g:submitButton name="submit" value="calculate"/>
        </g:form>
        </div>
        <div id="earnings">
            <p>Daily earnings: ${daily}</p>
            <p>Monthly earnings: ${monthly}</p>
            <p>Annual earnings: ${annual}</p>
        </div>
    </body>
</html>
