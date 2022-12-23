<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    <style><%@include file="/css/main.css"%></style>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />

</head>
<body>
    <%@include file="../parts/navbar.jsp"%>

    <div class="container-fluid w-50 h-52" style="margin-top: 5%; background-color: #141414; padding: 2%; border-radius: 15px;">
        <form action="<c:url value="/edit"/>" method="post">
            <div class="mb-3">
                <label for="username" class="form-label text-primary">Username</label>
                <input type="text" name="newUsername" class="form-control" id="username" required>
                <div style="padding-top: 2px">
                    <button type="submit" class="btn btn-primary" >Update username</button>
                </div>
                <c:if test="${not empty present}">
                    <div class="form-text text-danger">Username already used</div>
                </c:if>
            </div>
        </form>
        <form action="<c:url value="/edit"/>" method="post">
            <div class="mb-3">
                <label for="exampleInputPassword1" class="form-label text-primary">Password</label>
                <input type="password" name="newPassword" class="form-control" id="exampleInputPassword1" required>
                <div class="form-text">We'll never share your password with anyone else.</div>
            </div>
            <button type="submit" class="btn btn-primary">Update password</button>
        </form>
        <div style="padding-top: 2px">
            <form action="<c:url value="/edit"/>" method="post">
                <button type="submit" class="btn btn-danger" name="del">Delete account</button>
            </form>
        </div>
    </div>
</body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
<script type="text/javascript" src="<c:url value="/js/murder.js"/>"></script>
</html>
