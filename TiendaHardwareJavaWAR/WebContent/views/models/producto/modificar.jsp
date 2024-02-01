<%@page import="cl.litscl.tiendahardwareejb.model.Producto"%>
<%@page import="cl.litscl.tiendahardwareejb.model.Categoria"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Modificar Producto</title>
	<%@ include file='../../includes/head_styles.jsp' %>
	<%@ include file='../../includes/head_scripts.jsp' %>
	<%@ include file='../../includes/head_random.jsp' %>
</head>
<body>
<% 
if (session.getAttribute("renderizarVista") == "modificar") {
	List<Categoria> categorias = (List<Categoria>)session.getAttribute("categorias");
	Producto p = (Producto)session.getAttribute("producto");
%>
	<%@ include file='../../includes/header.jsp' %>
		
	<div class="contenido">

		<div class="contenedor-formulario">
			<h1>Crear Producto</h1>
			<%
			if (session.getAttribute("modificarProducto") == "Exitoso") {
			%>
				<strong class="alerta alerta-verde">Producto modificado exitosamente</strong>
			<%
			}
			if (session.getAttribute("modificarProducto") == "Fallido") {	
			%>
				<strong class="alerta alerta-roja">Error al modificar el producto</strong>
			<%		
			}
			%>
			<form action="<%=request.getContextPath()%>/Producto" method="POST" enctype="multipart/form-data">
				<input type="hidden" name="id" value="<%=p.getId()%>" readonly/>
				<label for="nombre">Nombre</label>
				<input type="text" name="nombre" value="<%=p.getNombre()%>" required/>
				<label for="descripcion">Descripción</label>
				<textarea name="descripcion" required><%=p.getDescripcion()%></textarea>
				<label for="precio">Precio</label>
				<input type="number" name="precio" value="<%=p.getPrecio()%>" min="500"/>
				<label for="stock">Stock</label>
				<input type="number" name="stock" value="<%=p.getStock()%>" min="1"/>
				<label for="categoria">Categoría</label>
				<select name="categoria">
				<%
				for (Categoria c : categorias) {
				%>				
					<%
					if (c.getId() == p.getCategoriaFK()) {
					%>	
						<option value="<%=c.getId()%>" selected><%=c.getNombre()%></option>
					<%
					}
					else {
					%>	
						<option value="<%=c.getId()%>"><%=c.getNombre()%></option>
					<%
					}
					%>				
				<%
				}
				%>		
				</select>
				<label for="imagen">Imagen</label>
				<input type="file" name="imagen"/>
				<div class="contenedor-modificar-imagen">
					<img src="<%=request.getContextPath() + "/uploads/models/producto/images/" + p.getImagen()%>"/>
				</div>
				<button type="submit" name="opcion" value="2">Modificar</button>
			</form>
		</div>
		
	</div>
<%
	session.removeAttribute("categorias");
	session.removeAttribute("producto");
	session.removeAttribute("modificarProducto");
	session.removeAttribute("renderizarVista");
}
else {
	if (request.getParameter("id") == null) {
		response.sendRedirect(request.getContextPath() + "/Producto?vista=modificar");
	}
	else {
		response.sendRedirect(request.getContextPath() + "/Producto?vista=modificar&id=" + request.getParameter("id"));	
	}
}
%>
</body>
</html>