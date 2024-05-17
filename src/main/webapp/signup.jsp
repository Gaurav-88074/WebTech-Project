<%--
  Created by IntelliJ IDEA.
  User: Gaurav
  Date: 5/17/2024
  Time: 8:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>login</title>
    <link rel="stylesheet" href="./signup.css">
    <script src="./signup.js"></script>
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
            <button id="login-button" class="auth-button">login</button>
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
            <input id="emailInput"  type="text" class='inputTextSection' placeholder='  Email' />
        </section>
        <section class='inputSection'>
            <div class="logo">

            </div>
            <input id="nameInput" type="text" class='inputTextSection' placeholder='  Your Name' />
        </section>
        <section class='inputSection'>
            <div class="logo">

            </div>
            <input id="passwordInput" type="password" class='inputTextSection' placeholder='  Password' />
        </section>
        <section class='other'>

        </section>
        <section class='buttonSection'>
            <button id="signupButton" class='loginButton'>Sign Up</button>
        </section>
    </div>
</main>
<footer>

</footer>
</body>
</html>