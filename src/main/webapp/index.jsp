<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>login</title>
    <link rel="stylesheet" href="./login.css">
    <script src="./login.js"></script>
</head>
<body>
<header class="header">
    <nav>
        <div class="logo-name">
            Classroom
        </div>
        <!-- <div class="you">
            G
        </div>  -->
        <div class="auth-div">
            <button id ="signup-button" class="auth-button">signup</button>
        </div>
    </nav>
</header>
<main>
    <div class="loginDom">
        <section class='connectSection'>

        </section>
        <section class='inputSection'>
            <div class="logo">

            </div>
            <input id="emailInput" type="text" class='inputTextSection' placeholder=' Email' />
        </section>
        <section class='inputSection'>
            <div class="logo">

            </div>
            <input  id="passwordInput" type="password" class='inputTextSection' placeholder=' Password' />
        </section>
        <section class='other'>

        </section>
        <section class='buttonSection'>
            <button id="loginButton" class='loginButton'>Log In</button>
        </section>
    </div>
</main>
<footer>

</footer>
</body>
</html>