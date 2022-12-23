<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Redactor</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
    <style><%@include file="/css/main.css"%></style>
</head>
<body>
    <%@include file="../parts/navbar.jsp"%>
    <div class="container-fluid w-50 h-52" style="margin-top: 5%; background-color: #141414; padding: 2%; border-radius: 15px;">
        <form action="<c:url value="/redactor"/>" method="post" enctype="multipart/form-data">
            <div class="mb-3">
                <label for="formFile" class="form-label text-primary">Выберите превьюшку</label>
                <input class="form-control" name="file" type="file" id="formFile" required>
            </div>
            <div class="mb-3">
                <label for="exampleFormControlInput1" class="form-label text-primary">Название тайтла</label>
                <input type="text" class="form-control" name="titleName" id="exampleFormControlInput1" placeholder="Название" required>
                <c:if test="${errorName != null}">
                    <p class="text-danger">Такой тайтл уже есть</p>
                </c:if>
            </div>
            <div class="mb-3">
                <label for="exampleFormControlTextarea1" class="form-label text-primary">Краткое описание</label>
                <textarea class="form-control" name="titleDescription" id="exampleFormControlTextarea1" rows="3" placeholder="Краткое описание" required></textarea>
            </div>
            <div class="mb-3">
                <label for="exampleFormControlInput2" class="form-label text-primary">Количество глав</label>
                <input type="text" class="form-control" name="pageCount" id="exampleFormControlInput2" placeholder="Количество глав" required>
                <c:if test="${not empty errorPageCount}">
                    <p class="text-danger">Неправильный формат</p>
                </c:if>
            </div>
            <div class="mb-3">
                <label for="exampleFormControlInput3" class="form-label text-primary">Тип</label>
                <input type="text" class="form-control" name="type" id="exampleFormControlInput3" placeholder="example: Манхва" required>
            </div>
            <div class="mb-3">
                <label for="exampleFormControlInput4" class="form-label text-primary">Дата Релиза</label>
                <input type="text" class="form-control" name="date" id="exampleFormControlInput4" placeholder="example: 2002" required>
            </div>
            <div class="mb-3">
                <label for="exampleFormControlInput5" class="form-label text-primary">Статус тайтла</label>
                <input type="text" class="form-control" name="status" id="exampleFormControlInput5" placeholder="example: Онгоинг" required>
            </div>
            <div class="mb-3">
                <label for="exampleFormControlInput6" class="form-label text-primary">Автор</label>
                <input type="text" class="form-control" name="author" id="exampleFormControlInput6" placeholder="Автор" required>
            </div>
            <div class="mb-3">
                <label for="exampleFormControlInput7" class="form-label text-primary">Альтернативное название</label>
                <input type="text" class="form-control" name="alName" id="exampleFormControlInput7" placeholder="Альтернативное название" required>
            </div>
            <div class="mb-3">
                <label for="exampleFormControlInput9" class="form-label text-primary">Ссылка для чтения</label>
                <input type="text" class="form-control" name="link" id="exampleFormControlInput9" placeholder="Ссылка для чтения" required>
            </div>
            <div class="mb-3">
                <label for="exampleFormControlInput8" class="form-label text-primary">Полное описание</label>
                <textarea class="form-control" name="fullDescription" id="exampleFormControlInput8" placeholder="Полное описание" required></textarea>
            </div>
            <button type="submit" class="btn btn-primary">Добавить тайтл</button>
        </form>
    </div>
</body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
<script type="text/javascript" src="<c:url value="/js/murder.js"/>"></script>
</html>
