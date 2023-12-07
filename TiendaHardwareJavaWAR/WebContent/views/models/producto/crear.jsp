<%@page import="cl.litscl.tiendahardwareejb.model.Categoria"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Crear Producto</title>
<%@ include file='../../includes/head_styles.jsp' %>
<%@ include file='../../includes/head_scripts.jsp' %>
<%@ include file='../../includes/head_random.jsp' %>
</head>
<body>
<% 
if (session.getAttribute("renderizarVista") == "crear") {
	List<Categoria> categorias = (List<Categoria>)session.getAttribute("categorias");
%>
	<%@ include file='../../includes/header.jsp' %>
		
	<div class="contenido">
	
		<div class="contenedor-formulario">
			<h1>Crear Producto</h1>
			<%
			if (session.getAttribute("crearProducto") == "Exitoso") {
			%>
				<strong class="alerta alerta-verde">Producto creado exitosamente</strong>
			<%
			}
			if (session.getAttribute("crearProducto") == "Fallido") {	
			%>
				<strong class="alerta alerta-roja">Error al crear el producto</strong>
			<%		
			}
			%>
			<form action="<%=request.getContextPath()%>/Producto" method="POST" enctype="multipart/form-data">
				<label for="nombre">Nombre</label>
				<input type="text" name="nombre" required/>
				<label for="descripcion">Descripción</label>
				<textarea name="descripcion" required></textarea>
				<label for="precio">Precio</label>
				<input type="number" name="precio" min="500"/>
				<label for="stock">Stock</label>
				<input type="number" name="stock" min="1"/>
				<label for="categoria">Categoría</label>
				<select name="categoria">
				<c:forEach items="${categorias}" var="c">
					<option value="${c.id}">${c.nombre}</option>
				</c:forEach>
				</select>
				<label for="imagen">Imagen</label>
				<input type="file" name="imagen" required/>
				<button type="submit" name="opcion" value="1">Crear</button>
			</form>
		</div>
		
	</div>
<%
	session.removeAttribute("crearProducto");
	session.removeAttribute("renderizarVista");
}
else {
	response.sendRedirect(request.getContextPath() + "/Producto?vista=crear");
}
%>
</body>
</html>