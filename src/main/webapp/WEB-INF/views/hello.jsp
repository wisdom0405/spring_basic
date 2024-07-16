<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>helloJSP FromData 실습</title>
</head>
<body>
    <p>data(EL문법) : ${myData}</p>
    <p>data(jstl문법 - java문법) : <%
        String getData = (String)request.getAttribute("myData");
        out.print(getData);
     %></p>

     <form action="/hello/servlet/jsp/post" method="post" enctype="application/x-www-form-urlencoded">

         이름 : <input type="text" name="name" size="20" maxlength="20"> <br><br>

         email : <input type="email" name="email" size="20" maxlength="20"> <br><br>

         password : <input type="password" name="password" size="20" maxlength="20">

         <input type="submit" value="제출">

     </form>

</body>
</html>