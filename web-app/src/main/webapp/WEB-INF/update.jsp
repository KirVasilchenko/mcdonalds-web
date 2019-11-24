<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Update position</title>
    <%@include file="bootstrap-css.jsp" %>
</head>
<body>

<div class="card" style="width: 18rem;">
    <img src="<%= request.getAttribute("image")  %>" class="card-img-top" alt="...">
    <div class="card-body">
        <h5 class="card-title"><%= request.getAttribute("name") %>
        </h5>
        <ul class="list-group list-group-flush">
            <li class="list-group-item">Product Name: <%= request.getAttribute("price") %>
                = <%= request.getAttribute("price") %>,00 RUB
            </li>
        </ul>
    </div>
</div>


<form action="<%= request.getContextPath() %>" method="post">
    <input name="id" type="hidden" value="<%= request.getAttribute("id") %>">
    <input name="order_id" type="hidden" value="<%= request.getAttribute("order_id") %>">
    <input name="product_id" type="hidden" value="<%= request.getAttribute("product_id") %>">
    <input name="product_name" type="hidden" value="<%= request.getAttribute("product_name") %>">
    <input name="price" type="hidden" value="<%= request.getAttribute("price") %>">
    <div class="form group">
        <label for="quantity">Product Quantity</label>
        <input type="number" min="1" id="quantity" name="quantity" value="<%= request.getAttribute("quantity") %>">
    </div>
    <button class="btn btn-primary">Save</button>
</form>


<a href="/">Back</a>

</body>
</html>
