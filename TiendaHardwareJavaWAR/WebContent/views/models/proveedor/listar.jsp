<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Listar Proveedores</title>
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
		if (session.getAttribute("eliminarProveedor") == "Exitoso") {
		%>
			<strong class="alerta alerta-verde">Proveedor eliminado exitosamente</strong>
		<%
		}
		if (session.getAttribute("eliminarProveedor") == "Fallido") {	
		%>
			<strong class="alerta alerta-roja">Error al eliminar el proveedor</strong>
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
	session.removeAttribute("eliminarProveedor");
	session.removeAttribute("registros");
	session.removeAttribute("numeros");
	session.removeAttribute("renderizarVista");
}
else {
	response.sendRedirect(request.getContextPath() + "/Proveedor?vista=listar&pagina=" + request.getParameter("pagina"));
}
%>
</body>
</html>