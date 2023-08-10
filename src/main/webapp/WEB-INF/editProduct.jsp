<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Edit Product</title>
</head>
<body>
<h1>Edit Product</h1>
<form action="<%=request.getContextPath()%>/ProductController" method="post">
  <c:if test="${not empty editProduct}">
    <input type="hidden" name="action" value="UPDATE">
    <input type="hidden" name="id" value="${editProduct.id}">
    <label for="name">Name</label>
    <input type="text" id="name" name="name" value="${editProduct.name}">
      <label for="des">Description</label>
      <textarea id="des" name="des">${editProduct.descriptions}</textarea>
    <br>
    <label for="price">Price</label>
    <input type="text" id="price" name="price" value="${editProduct.price}">
    <label for="stock">Stock</label>
    <input type="number" id="stock" name="stock" value="${editProduct.stock}">
    <br>
    <label >Image URL</label>
    <textarea name="imageUrl"  cols="30" rows="10">${editProduct.imageUrl}</textarea>
    <label>Status</label>

    <input type="submit" value="UPDATE">
  </c:if>
  <a href="ProductController">Back to Products List</a>
</form>
</body>
</html>
