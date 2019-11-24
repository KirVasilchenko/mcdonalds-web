<%@ page import="ru.rosbank.javaschool.web.constant.Constants" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>About product</title>
    <%@include file="bootstrap-css.jsp" %>
</head>
<body>

<div class="card" style="width: 18rem;">
    <img src="<%= request.getAttribute(Constants.PRODUCTS_COLUMN_IMAGE)  %>" class="card-img-top" alt="...">
    <div class="card-body">
        <h5 class="card-title"><%= request.getAttribute(Constants.PRODUCTS_COLUMN_NAME) %>
        </h5>
        <ul class="list-group list-group-flush">
            <li class="list-group-item">Price: <%= request.getAttribute(Constants.PRODUCTS_COLUMN_PRICE) %></li>
            <li class="list-group-item">Quantity: <%= request.getAttribute(Constants.PRODUCTS_COLUMN_QUANTITY) %></li>
            <li class="list-group-item">Description: <%= request.getAttribute(Constants.PRODUCTS_COLUMN_DESCRIPTION) %></li>
            <li class="list-group-item">Category: <%= request.getAttribute(Constants.PRODUCTS_COLUMN_CATEGORY) %></li>
        </ul>
    </div>
</div>

<a href="/">Back</a>

</body>
</html>
