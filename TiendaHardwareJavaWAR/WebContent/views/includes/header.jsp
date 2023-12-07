<%@page import="cl.litscl.tiendahardwareejb.model.Usuario"%>
<%@page import="cl.litscl.tiendahardwareejb.bean.CategoriaDAO"%>
<%@page import="cl.litscl.tiendahardwareejb.model.Categoria"%>
<%@page import="java.util.List"%>

<%! List<Categoria> categorias = new CategoriaDAO().getAll(); %>

<header>
<%
if (session.getAttribute("usuario") == null) {
%>
	<nav id="navDefecto">
		<input id="checkbox" type="checkbox">
		<label class="boton-checkbox" for="checkbox"> 
			<i class="fas fa-bars"></i> 
		</label>
		<div class="contenido-navegacion">
			<div class="navegacion">
				<ul>
					<li><a href="<%=request.getContextPath()%>">Inicio</a></li>
				<%
				for (Categoria c : categorias) {
				%>
					<li><a href="<%=request.getContextPath() + "/Categoria?vista=mostrar&id=" + c.getId()%>"><%=c.getNombre()%></a></li>
				<%
				}
				%>
				</ul>
			</div>
	
			<div class="usuario">
				<ul>
					<li><a class="accion" href="<%=request.getContextPath()%>/carrito/mostrar"><i class="fas fa-shopping-cart" title="Carrito"></i></a></li>
					<li><a href="<%=request.getContextPath()%>/Usuario?vista=iniciar_sesion">Iniciar Sesión</a></li>
					<li><a href="<%=request.getContextPath()%>/Usuario?vista=registrarse">Registrarse</a></li>
				</ul>
			</div>
		</div>
	</nav>
<%
}
%>
	
<%
if (session.getAttribute("usuario") != null && ((Usuario)session.getAttribute("usuario")).getTipo().equals("Cliente")) {
%>
	<nav id="navDefecto">
		<input id="checkbox" type="checkbox">
		<label class="boton-checkbox" for="checkbox"> 
			<i class="fas fa-bars"></i> 
		</label>
		<div class="contenido-navegacion">
			<div class="navegacion">
				<ul>
					<li><a href="<%=request.getContextPath()%>">Inicio</a></li>
				<%
				for (Categoria c : categorias) {
				%>
					<li><a href="<%=request.getContextPath() + "/Categoria?vista=mostrar&id=" + c.getId()%>"><%=c.getNombre()%></a></li>
				<%
				}
				%>
				</ul>
			</div>
	
			<div class="usuario">
				<ul>
					<li><a class="accion" href="<%=request.getContextPath()%>/carrito/mostrar"><i class="fas fa-shopping-cart" title="Carrito"></i></a></li>
					<li><a class="accion" href="<%=request.getContextPath()%>/Pedido?vista=mis_pedidos"><i class="fas fa-parachute-box" title="Pedidos"></i></a></li>
					<li><a class="accion" href="<%=request.getContextPath()%>/producto/favoritos"><i class="fas fa-heart" title="Favoritos"></i></a></li>
					<li><a href="<%=request.getContextPath()%>/usuario/editar-perfil"><%=((Usuario)session.getAttribute("usuario")).getPrimerNombre()%></a></li>
					<li><img class="avatar" src="<%=request.getContextPath()%>/uploads/models/usuario/images/<%=((Usuario)session.getAttribute("usuario")).getImagen()%>"/></li>
					<li><a href="<%=request.getContextPath()%>/Usuario?opcion=1"><img class="cerrar" src="<%=request.getContextPath()%>/assets/img/close.png" title="Cerrar Sesión"/></a></li>
				</ul>
			</div>
		</div>
	</nav>
<%
}
%>

<%
if (session.getAttribute("usuario") != null && ((Usuario)session.getAttribute("usuario")).getTipo().equals("Administrador")) {
%>
	<nav id="navAdministrador">
		<input id="checkbox" type="checkbox">
		<label class="boton-checkbox" for="checkbox"> 
			<i class="fas fa-bars"></i> 
		</label>
		<div class="contenido-navegacion">
			<div class="navegacion">
				<ul>
					<li><a href="<%=request.getContextPath()%>">Inicio</a></li>
					<li><a href="<%=request.getContextPath()%>/categoria/crear">Crear Categoría</a></li>
					<li><a href="<%=request.getContextPath()%>/producto/crear">Crear Producto</a></li>
					<li><a href="<%=request.getContextPath()%>/proveedor/crear">Crear Proveedor</a></li>
					<li><a href="<%=request.getContextPath()%>/categoria/listar">Listar Categorías</a></li>
					<li><a href="<%=request.getContextPath()%>/producto/listar">Listar Productos</a></li>
					<li><a href="<%=request.getContextPath()%>/proveedor/listar">Listar Proveedores</a></li>
					<li><a href="<%=request.getContextPath()%>/pedido/listar">Listar Pedidos</a></li>
				</ul>
			</div>
	
			<div class="usuario">
				<ul>
					<li><a href="<%=request.getContextPath()%>/usuario/editar-perfil"><%=((Usuario)session.getAttribute("usuario")).getPrimerNombre()%></a></li>
					<li><img class="avatar" src="<%=request.getContextPath() + "/uploads/models/usuario/images/" + ((Usuario)session.getAttribute("usuario")).getImagen()%>"/></li>
					<li><a href="<%=request.getContextPath()%>/Usuario?opcion=1"><img class="cerrar" src="<%=request.getContextPath()%>/assets/img/close.png" title="Cerrar Sesión"/></a></li>
				</ul>
			</div>
		</div>
	</nav>
<%
}
%>
</header>