<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Listar Productos</title>
<%@ include file='../../includes/head_styles.jsp' %>
<%@ include file='../../includes/head_scripts.jsp' %>
<%@ include file='../../includes/head_random.jsp' %>
</head>
<body>
<% 
if (session.getAttribute("renderizarVista") == "listar") {
%>
	<%@ include file='../../includes/header.jsp' %>
		
	<div class="contenido">
	
		<%
		if (session.getAttribute("eliminarProducto") == "Exitoso") {
		%>
			<strong class="alerta alerta-verde">Producto eliminado exitosamente</strong>
		<%
		}
		if (session.getAttribute("eliminarProducto") == "Fallido") {	
		%>
			<strong class="alerta alerta-roja">Error al eliminar el producto</strong>
		<%		
		}
		%>
		<div class="contenedor-paginacion-objetos">
		<%
		if (session.getAttribute("registros") == "Pagina Inexistente") {
		%>
			<h1 class="titulo-pagina-centrada">No se han encontrado resultados en esta página</h1>
		<%
		}
		else if (session.getAttribute("registros") == "Sin Registros") {
		%>
			<h1 class="titulo-pagina-centrada">No hay productos</h1>
		<%
		}
		else {
		%>
			<%=session.getAttribute("registros")%>
		<%
		}
		%>	
		</div>
		
		<div class="contenedor-paginacion-paginas">
			<%=session.getAttribute("numeros")%>
		</div>
		
	</div>
<%
	session.removeAttribute("eliminarProducto");
	session.removeAttribute("registros");
	session.removeAttribute("numeros");
	session.removeAttribute("renderizarVista");
}
else {
	response.sendRedirect(request.getContextPath() + "/Producto?vista=listar&pagina=" + request.getParameter("pagina"));
}
%>
</body>
</html>