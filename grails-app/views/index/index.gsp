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
        <g:form controller="login">
            <g:textField name="userName"/>
            <g:passwordField name="password"/>
            <g:submitButton name="submit" value="log in"/>
        </g:form>
        </div>
        <g:link controller="register">Register a new account</g:link>
    </body>
</html>
