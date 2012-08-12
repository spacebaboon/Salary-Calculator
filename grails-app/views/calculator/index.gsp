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

    %{--<g:form onsubmit="updateSalary(); return false;">--}%
    <form id="workprofile" onsubmit="updateSalary(); return false;">
        <div id="salary" class="userinput">
            Annual Salary: <g:textField name="annualSalary" value="${annualSalary}"/>
        </div>
        <div id="start" class="userinput">
            Start time:
            <g:textField name="startHour" value="${startHour}"/>
            <g:textField name="startMin" value="${startMin}"/>
        </div>
        <div id="end" class="userinput">
            End time:
            <g:textField name="endHour" value="${endHour}"/>
            <g:textField name="endMin" value="${endMin}"/>
        </div>
        <div id="days" class="userinput">
            <span class="day"><g:checkBox name="mon" value="${mon}"/>Monday</span>
            <span class="day"><g:checkBox name="tue" value="${tue}"/>Tuesday</span>
            <span class="day"><g:checkBox name="wed" value="${wed}"/>Wednesday</span>
            <span class="day"><g:checkBox name="thu" value="${thu}"/>Thursday</span>
            <span class="day"><g:checkBox name="fri" value="${fri}"/>Friday</span>
            <span class="day"><g:checkBox name="sat" value="${sat}"/>Saturday</span>
            <span class="day"><g:checkBox name="sun" value="${sun}"/>Sunday</span>
        </div>
    %{--</g:form>--}%
    </form>

    <div id="earnings">
        <g:render template="salaries" />
    </div>

</div>

</body>
</html>
