<%-- 
    Document   : forgotpassword
    Created on : Nov 21, 2017, 2:06:30 PM
    Author     : 728918
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Password Retrieval</title>
    </head>
    <body>
        <form action="login" method="get">
            <h1>Forgot Password</h1><br>
            Please enter your email address to receive your password<br>
            Email Address: <input type="textfield" name="emailaddr"><br>
            <input type="hidden" name="logout" value="forgot">
            <input type="submit" value="Submit">
        </form>
    </body>
</html>
