<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title><link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    <style><%@include file="/css/main.css"%></style>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />

</head>
<body>
    <%@include file="../parts/navbar.jsp"%>

    <div class="container-fluid w-50 h-52" style="margin-top: 5%; background-color: #141414; padding: 2%; border-radius: 15px;">
        <h2 class="text-danger">Your favourite <span class="badge bg-danger">${user.getMangaId().size()}</span></h2>
        <c:forEach items="${mangas}" var="manga">
            <div class="card mb-3">
                <img src="${pageContext.request.contextPath}/garbage/${manga.getId()}${manga.getExtension()}" class="card-img-top" alt="${manga.getId()}" style="height: 400px;">
                <div class="card-body">
                    <hr class="divider">
                    <h5 class="card-title"><c:out value="${manga.getName()}"/></h5>
                    <p class="card-text"><c:out value="${manga.getDescription()}"/></p>
                    <p class="card-text"><small class="text-muted"><c:out value="${manga.getPageCount()}"/> Глав</small></p>
                    <form action="<c:url value="/titles"/>" method="get">
                        <button class="btn btn-primary" type="submit" name="title" value="${manga.getName()}">More information</button>
                    </form>
                    <div style="padding-top: 2px">
                        <form action="<c:url value="/profile"/>" method="post">
                            <button class="btn btn-danger" type="submit" name="delete" value="${manga.getId()}">Удалить</button>
                        </form>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
<script type="text/javascript" src="<c:url value="/js/murder.js"/>"></script>
</html>
