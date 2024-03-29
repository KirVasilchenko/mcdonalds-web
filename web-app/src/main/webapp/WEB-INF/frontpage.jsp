<%@ page import="java.util.List" %>
<%@ page import="ru.rosbank.javaschool.web.constant.Constants" %>
<%@ page import="ru.rosbank.javaschool.web.model.ProductModel" %>
<%@ page import="ru.rosbank.javaschool.web.model.OrderPositionModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport"
        content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>Burger Shop</title>
  <%@include file="bootstrap-css.jsp"%>
</head>
<body>
<div class="container">

  <h1>Star Burger</h1>

    <% List<OrderPositionModel> positions = (List<OrderPositionModel>) request.getAttribute("ordered-items"); %>
    <p>Items in cart: <%= positions.size() %></p>
    <% for (OrderPositionModel model: positions) { %>
    <p><%= model.getProductName() %> :
       <%= model.getProductPrice()%>,00 RUB x
       <%= model.getProductQuantity() %> =
       <%= model.getProductPrice()*model.getProductQuantity() %>,00 RUB
       <a href="<%= request.getContextPath() %>/update?id=<%= model.getId()%>" class="btn btn-primary">Edit</a>
       <a href="<%= request.getContextPath() %>/remove?id=<%= model.getId()%>" class="btn btn-primary">Remove</a></p>
    <% } %>
  <p><a href="/admin">Admin window</a></p>


  <div class="row">
  <% for (ProductModel item : (List<ProductModel>) request.getAttribute(Constants.ITEMS)) { %>
    <div class="col-3">
      <div class="card mt-3">
        <img src="<%= item.getImageUrl() %>" class="card-img-top" alt="...">
        <div class="card-body">
          <h5 class="card-title"><%= item.getName() %>
          </h5>
          <a href="<%= request.getContextPath() %>/more?id=<%= item.getId()%>" class="btn btn-primary">More</a>
          <ul class="list-group list-group-flush">
            <li class="list-group-item">Price: <%= item.getPrice() %></li>
          </ul>
          <form action="<%= request.getContextPath() %>" method="post">
            <input name="id" type="hidden" value="<%= item.getId() %>">
            <div class="form group">
              <label for="quantity">Product Quantity</label>
              <input type="number" min="0" id="quantity" name="quantity" value="1">
            </div>
            <button class="btn btn-primary">Add to cart</button>
          </form>
        </div>
      </div>
    </div>
  <% } %>
  </div>
</div>

</body>
</html>
