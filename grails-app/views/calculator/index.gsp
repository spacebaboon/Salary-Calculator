<html>
<head>
    <title>Salary Calculator</title>
    <meta name="layout" content="main"/>
    <link rel="stylesheet" href="../css/screen.css" type="text/css" media="screen, projection">
    <g:javascript library="jquery" plugin="jquery"/>
    <g:javascript src="/updateSalary.js"/>
</head>

<body>

<div class="content">

    <div class="title">
        <h1>Salary Calculator</h1>
    </div>

    <form onsubmit="updateSalary(); return false;">
        Annual Salary: <input type="text" name="annualSalary"/>
    </form>

    <div id="earnings">
        <g:render template="salaries"/>
    </div>

</div>

</body>
</html>
