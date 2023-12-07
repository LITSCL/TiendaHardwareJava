package cl.litscl.tiendahardwareejb.fk;

import cl.litscl.tiendahardwareejb.bean.ProductoDAO;
import cl.litscl.tiendahardwareejb.bean.ProductoDAOLocal;
import cl.litscl.tiendahardwareejb.bean.UsuarioDAO;
import cl.litscl.tiendahardwareejb.bean.UsuarioDAOLocal;
import cl.litscl.tiendahardwareejb.model.Producto;
import cl.litscl.tiendahardwareejb.model.ProductoUsuario;
import cl.litscl.tiendahardwareejb.model.Usuario;

public class ProductoUsuarioFK {
	private UsuarioDAOLocal daoUsuario = new UsuarioDAO();
	private ProductoDAOLocal daoProducto = new ProductoDAO();
	private Usuario u = new Usuario();
	private Producto p = new Producto();
	
	public int getUsuarioId(ProductoUsuario pu) {
		this.u = daoUsuario.find(pu.getUsuarioFK());
		return this.u.getId();
	}
	
	public String getUsuarioCorreo(ProductoUsuario pu) {
		this.u = daoUsuario.find(pu.getUsuarioFK());
		return this.u.getCorreo();
	}
	
	public String getUsuarioClave(ProductoUsuario pu) {
		this.u = daoUsuario.find(pu.getUsuarioFK());
		return this.u.getClave();
	}
	
	public String getUsuarioTipo(ProductoUsuario pu) {
		this.u = daoUsuario.find(pu.getUsuarioFK());
		return this.u.getTipo();
	}
	
	public String getUsuarioPrimerNombre(ProductoUsuario pu) {
		this.u = daoUsuario.find(pu.getUsuarioFK());
		return this.u.getPrimerNombre();
	}
	
	public String getUsuarioSegundoNombre(ProductoUsuario pu) {
		this.u = daoUsuario.find(pu.getUsuarioFK());
		return this.u.getSegundoNombre();
	}
	
	public String getUsuarioApellidoPaterno(ProductoUsuario pu) {
		this.u = daoUsuario.find(pu.getUsuarioFK());
		return this.u.getApellidoPaterno();
	}
	
	public String getUsuarioMaterno(ProductoUsuario pu) {
		this.u = daoUsuario.find(pu.getUsuarioFK());
		return this.u.getApellidoMaterno();
	}
	
	public String getUsuarioImagen(ProductoUsuario pu) {
		this.u = daoUsuario.find(pu.getUsuarioFK());
		return this.u.getImagen();
	}
	
	public int getProductoId(ProductoUsuario pu) {
		this.p = daoProducto.find(pu.getProductoFK());
		return this.p.getId();
	}
	
	public String getProductoNombre(ProductoUsuario pu) {
		this.p = daoProducto.find(pu.getProductoFK());
		return this.p.getNombre();
	}
	
	public String getProductoDescripcion(ProductoUsuario pu) {
		this.p = daoProducto.find(pu.getProductoFK());
		return this.p.getDescripcion();
	}
	
	public Double getProductoPrecio(ProductoUsuario pu) {
		this.p = daoProducto.find(pu.getProductoFK());
		return this.p.getPrecio();
	}
	
	public int getProductoStock(ProductoUsuario pu) {
		this.p = daoProducto.find(pu.getProductoFK());
		return this.p.getStock();
	}
	
	public String getProductoImagen(ProductoUsuario pu) {
		this.p = daoProducto.find(pu.getProductoFK());
		return this.p.getImagen();
	}
	
	public int getProductoCategoriaFK(ProductoUsuario pu) {
		this.p = daoProducto.find(pu.getProductoFK());
		return this.p.getCategoriaFK();
	}
}