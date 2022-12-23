<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>ManKaka</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
    <style><%@include file="/css/main.css"%></style>

</head>
<body>
    <%@include file="../parts/navbar.jsp"%>
    <div class="container-fluid w-50 h-52" style="margin-top: 5%; background-color: #141414; padding: 2%; border-radius: 15px;">
        <c:forEach items="${mangaList}" var="manga">
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
                    <c:if test="${not empty user}">
                        <div style="padding-top: 2px">
                            <form action="<c:url value="/"/>" method="post">
                                <c:if test="${user.getMangaId().contains(manga.getId())}">
                                    <button class="btn btn-primary" type="submit" name="favourites" value="${manga.getId()}" disabled>Добавить к себе в избранное</button>
                                </c:if>
                                <c:if test="${not user.getMangaId().contains(manga.getId())}">
                                    <button class="btn btn-primary" type="submit" name="favourites" value="${manga.getId()}">Добавить к себе в избранное</button>
                                </c:if>
                            </form>
                            <c:if test="${user.getRole() == 'redactor'}">
                                <div style="padding-top: 2px">
                                    <form action="<c:url value="/"/>" method="post">
                                        <button class="btn btn-danger" type="submit" name="deleteManga" value="${manga.getId()}">Удалить</button>
                                    </form>
                                </div>
                            </c:if>
                        </div>
                    </c:if>
                </div>
            </div>
        </c:forEach>
    </div>
</body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
<script type="text/javascript" src="<c:url value="/js/murder.js"/>"></script>
</html>
