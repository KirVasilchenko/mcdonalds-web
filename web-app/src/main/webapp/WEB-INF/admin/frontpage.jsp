<%@ page import="java.util.List" %>
<%@ page import="ru.rosbank.javaschool.web.constant.Constants" %>
<%@ page import="ru.rosbank.javaschool.web.model.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- ! + Tab - emmet --%>
<!doctype html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport"
        content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>Dashboard</title>
  <%@include file="../bootstrap-css.jsp" %>
</head>
<body>

<div class="container">
  <h1>Dashboard</h1>
  <h2><a href="/">Back to menu</a></h2>


  <% for (ProductModel item : (List<ProductModel>) request.getAttribute(Constants.ITEMS)) { %>
  <div class="card" style="width: 18rem;">
    <img src="<%= item.getImageUrl() %>" class="card-img-top" alt="...">
    <div class="card-body">
      <h5 class="card-title"><%= item.getName() %>
      </h5>
      <ul class="list-group list-group-flush">
        <li class="list-group-item">Price: <%= item.getPrice() %></li>
        <li class="list-group-item">Quantity: <%= item.getQuantity() %></li>
        <li class="list-group-item">Description: <%= item.getDescription() %></li>
        <li class="list-group-item">Category: <%= item.getCategory() %></li>
      </ul>
      <p><a href="<%= request.getContextPath() %>/admin/edit?id=<%= item.getId()%>" class="btn btn-primary">Edit</a></p>
    </div>
  </div>
  <% } %>


  <% if (request.getAttribute(Constants.ITEM) == null) { %>
  <form action="<%= request.getContextPath() %>/admin" method="post">
    <input name="id" type="hidden" value="0">
    <div class="form group">
      <label for="name">Product Name</label>
      <input type="text" id="name" name="name">
    </div>
    <div class="form group">
      <label for="price">Product Price</label>
      <input type="number" min="0" id="price" name="price">
    </div>
    <div class="form group">
      <label for="quantity">Product Quantity</label>
      <input type="number" min="0" id="quantity" name="quantity">
    </div>
    <div class="form group">
      <label for="image">Product Image URL</label>
      <input type="text" id="image" name="image">
    </div>
    <div class="form group">
      <label for="description">Product Description</label>
      <input type="text" id="description" name="description">
    </div>
    <div class="form group">
      <label for="category">Product Category</label>
      <input type="text" id="category" name="category">
    </div>
    <button class="btn btn-primary">Add</button>
  </form>
  <% } %>

  <% if (request.getAttribute(Constants.ITEM) != null) { %>
  <% ProductModel item = (ProductModel) request.getAttribute(Constants.ITEM); %>
  <form action="<%= request.getContextPath() %>/admin" method="post">
    <input name="id" type="hidden" value="<%= item.getId() %>">
    <div class="form group">
      <label for="name">Product Name</label>
      <input type="text" id="name" name="name" value="<%= item.getName() %>">
    </div>
    <div class="form group">
      <label for="price">Product Price</label>
      <input type="number" min="0" id="price" name="price" value="<%= item.getPrice() %>">
    </div>
    <div class="form group">
      <label for="quantity">Product Quantity</label>
      <input type="number" min="0" id="quantity" name="quantity" value="<%= item.getQuantity() %>">
    </div>
    <div class="form group">
      <label for="image">Product Image URL</label>
      <input type="text" id="image" name="image" value="<%= item.getImageUrl() %>">
    </div>
    <div class="form group">
      <label for="description">Product Description</label>
      <input type="text" id="description" name="description" value="<%= item.getDescription() %>">
    </div>
    <div class="form group">
      <label for="category">Product Category</label>
      <input type="text" id="category" name="category" value="<%= item.getCategory() %>">
    </div>

    <% if ( item.getCategory().equals("burgers") ) {%>
    <div class="form group">
      <label for="cutlet_meat">Cutlet Meat (use Beef, Chicken, Fish)</label>
      <input type="text" id="cutlet_meat" name="cutlet_meat" value="">
    </div>
    <div class="form group">
      <label for="cutlet_count">Cutlet Count</label>
      <input type="number" min="0" id="cutlet_count" name="cutlet_count" min="1" value="">
    </div>
    <%}%>
    <% if ( item.getCategory().equals("potatoes") ) {%>
    <div class="form group">
      <label for="weight_in_g">Weight, g</label>
      <input type="number" min="0" id="weight_in_g" name="weight_in_g" min="1" value="">
    </div>
    <%}%>
    <% if ( item.getCategory().equals("drinks") ) {%>
    <div class="form group">
      <label for="volume_in_ml">Volume, ml</label>
      <input type="number" min="0" id="volume_in_ml" name="volume_in_ml" min="1" value="">
    </div>
    <%}%>
    <% if ( item.getCategory().equals("desserts") ) {%>
    <div class="form group">
      <label for="syrup">Syrup Flavor</label>
      <input type="text" id="syrup" name="syrup" value="">
    </div>
    <%}%>
    <button class="btn btn-primary">Save</button>
  </form>
  <% } %>
</div>

</body>
</html>
