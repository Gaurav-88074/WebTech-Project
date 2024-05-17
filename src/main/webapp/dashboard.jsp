<%--
  Created by IntelliJ IDEA.
  User: Gaurav
  Date: 5/17/2024
  Time: 9:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard</title>
    <link rel="stylesheet" href="./dashboard.css">
    <script src="./dashboard.js"></script>
    <!-- <script src=""></script> -->
</head>

<body>
<header class="header">
    <nav>
        <div class="logo-name">
            Dashboard
        </div>
        <div class="you">
            <button id="logout-button">logout</button>
        </div>
        <div class="auth-div authHide">

        </div>
    </nav>
</header>
<main>
    <section class="toolbar">
<%--        <button class="create-button">Create</button>--%>
        <div>
            <p class="user-info" id="user-info"></p>
        </div>
    </section>
    <section class="courses">
<%--        <div class="skeleton">--%>
<%--        </div>--%>
<%--        <div class="skeleton">--%>
<%--        </div>--%>
<%--        <div class="skeleton">--%>
<%--        </div>--%>
<%--        <div class="skeleton">--%>
<%--        </div>--%>
    </section>
</main>
<footer>

</footer>
</body>

</html>