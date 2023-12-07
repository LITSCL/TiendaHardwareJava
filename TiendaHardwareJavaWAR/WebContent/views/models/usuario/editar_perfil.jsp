<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Editar Perfil</title>
<%@ include file='../../includes/head_styles.jsp' %>
<%@ include file='../../includes/head_scripts.jsp' %>
<%@ include file='../../includes/head_random.jsp' %>
</head>
<body>
<% 
if (session.getAttribute("renderizarVista") == "editarPerfil") {
	Usuario u = (Usuario)session.getAttribute("usuario");
%>
	<%@ include file='../../includes/header.jsp' %>
		
	<div class="contenido">
	
		<div class="contenedor-formulario">
			<h1>Editar Perfil</h1>
			<%
			if (session.getAttribute("editarPerfil") == "Exitoso") {
			%>
				<strong class="alerta alerta-verde">Perfil editado exitosamente</strong>
			<%
			}
			if (session.getAttribute("editarPerfil") == "Fallido") {	
			%>
				<strong class="alerta alerta-roja">Error al editar el perfil</strong>
			<%		
			}
			%>
			<form action="<%=request.getContextPath()%>/Usuario" method="POST" enctype="multipart/form-data">
				<label for="correo">Correo</label>
				<input type="email" name="correo" value="<%=u.getCorreo()%>" required/>
				<label for="clave">Contraseña</label>
				<input type="password" name="clave" value="Clave no cambiada" required/>
				<label for="primerNombre">Primer Nombre</label>
				<input type="text" name="primerNombre" value="<%=u.getPrimerNombre()%>" required/>
				<label for="segundoNombre">Segundo Nombre</label>
				<input type="text" name="segundoNombre" value="<%=u.getSegundoNombre()%>" required/>
				<label for="apellidoPaterno">Apellido Paterno</label>
				<input type="text" name="apellidoPaterno" value="<%=u.getApellidoPaterno()%>" required/>
				<label for="apellidoMaterno">Apellido Materno</label>
				<input type="text" name="apellidoMaterno" value="<%=u.getApellidoMaterno()%>" required/>
				<label for="imagen">Imagen</label>
				<input type="file" name="imagen"/>
				<div class="contenedor-modificar-imagen">
					<img src="<%=request.getContextPath() + "/uploads/models/usuario/images/" + u.getImagen()%>"/>
				</div>
				<button type="submit" name="opcion" value="3">Editar</button>
			</form>
		</div>
		
	</div>
<%
	session.removeAttribute("editarPerfil");
	session.removeAttribute("renderizarVista");
}
else {
	response.sendRedirect(request.getContextPath() + "/Usuario?vista=editar_perfil");
}
%>
</body>
</html>