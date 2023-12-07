package cl.litscl.tiendahardwareejb.fk;

import cl.litscl.tiendahardwareejb.bean.UsuarioDAO;
import cl.litscl.tiendahardwareejb.bean.UsuarioDAOLocal;
import cl.litscl.tiendahardwareejb.model.Pedido;
import cl.litscl.tiendahardwareejb.model.Usuario;

public class PedidoFK {
	private UsuarioDAOLocal daoUsuario = new UsuarioDAO();
	private Usuario u = new Usuario();
	
	public int getUsuarioId(Pedido p) {
		this.u = daoUsuario.find(p.getUsuarioFK());
		return this.u.getId();
	}
	
	public String getUsuarioCorreo(Pedido p) {
		this.u = daoUsuario.find(p.getUsuarioFK());
		return this.u.getCorreo();
	}
	
	public String getUsuarioClave(Pedido p) {
		this.u = daoUsuario.find(p.getUsuarioFK());
		return this.u.getClave();
	}
	
	public String getUsuarioTipo(Pedido p) {
		this.u = daoUsuario.find(p.getUsuarioFK());
		return this.u.getTipo();
	}
	
	public String getUsuarioPrimerNombre(Pedido p) {
		this.u = daoUsuario.find(p.getUsuarioFK());
		return this.u.getPrimerNombre();
	}
	
	public String getUsuarioSegundoNombre(Pedido p) {
		this.u = daoUsuario.find(p.getUsuarioFK());
		return this.u.getSegundoNombre();
	}
	
	public String getUsuarioApellidoPaterno(Pedido p) {
		this.u = daoUsuario.find(p.getUsuarioFK());
		return this.u.getApellidoPaterno();
	}
	
	public String getUsuarioMaterno(Pedido p) {
		this.u = daoUsuario.find(p.getUsuarioFK());
		return this.u.getApellidoMaterno();
	}
	
	public String getUsuarioImagen(Pedido p) {
		this.u = daoUsuario.find(p.getUsuarioFK());
		return this.u.getImagen();
	}
}