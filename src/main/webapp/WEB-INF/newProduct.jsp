<%--
  Created by IntelliJ IDEA.
  User: hung1
  Date: 8/7/2023
  Time: 5:50 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
</head>
<body>
<h1>New Product</h1>
<form action="<%=request.getContextPath()%>/ProductController" method="post" enctype="multipart/form-data">
  <label for="name">Name</label>
  <input type="text" id="name" name="name" >
  <label for="des">Description</label>
  <textarea type="text" id="des" name="des" ></textarea>
  <br>
  <label for="price">Price</label>
  <input type="text" id="price" name="price" >
  <label for="stock">Stock</label>
  <input type="number" id="stock" name="stock" >
  <br>
  <label for="image">imageUrl</label>
  <input type="file" name="image" id="image">
  <label for="imageUrls">imageUrls</label>
  <input type="file" name="imageUrls" id="imageUrls" multiple>
  <input type="submit" value="ADD" name="action"/>
</form>
</body>

</html>
