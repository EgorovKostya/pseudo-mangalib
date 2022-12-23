<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>FAQ</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    <style><%@include file="/css/main.css"%></style>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />

</head>
<body>
    <%@include file="../parts/navbar.jsp"%>

    <div class="container-fluid w-50 h-52" style="margin-top: 5%; background-color: #141414; padding: 2%; border-radius: 15px;">
        <div class="accordion" id="accordionPanelsStayOpenExample">
            <c:forEach items="${questions}" var="question">
                <div class="accordion-item">
                    <h2 class="accordion-header" id="${question.getId()}">
                        <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#${question.getId()}" aria-expanded="true" aria-controls="${question.getId()}">${question.getLabel()}
                        </button>
                    </h2>
                    <div id="${question.getId()}" class="accordion-collapse collapse show" aria-labelledby="${question.getId()}">
                        <div class="accordion-body">
                            <strong>${question.getAnswer()}</strong>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
<script type="text/javascript" src="<c:url value="/js/murder.js"/>"></script>
</html>
