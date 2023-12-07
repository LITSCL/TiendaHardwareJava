package cl.litscl.tiendahardwareejb.fk;

import cl.litscl.tiendahardwareejb.bean.PedidoDAO;
import cl.litscl.tiendahardwareejb.bean.PedidoDAOLocal;
import cl.litscl.tiendahardwareejb.bean.ProductoDAO;
import cl.litscl.tiendahardwareejb.bean.ProductoDAOLocal;
import cl.litscl.tiendahardwareejb.model.Pedido;
import cl.litscl.tiendahardwareejb.model.Producto;
import cl.litscl.tiendahardwareejb.model.ProductoPedido;

public class ProductoPedidoFK {
	private PedidoDAOLocal daoPedido = new PedidoDAO();
	private ProductoDAOLocal daoProducto = new ProductoDAO();
	private Pedido pe = new Pedido();
	private Producto pr = new Producto();
	
	public int getPedidoId(ProductoPedido pp) {
		this.pe = daoPedido.find(pp.getPedidoFK());
		return this.pe.getId();
	}
	
	public String getPedidoCiudad(ProductoPedido pp) {
		this.pe = daoPedido.find(pp.getPedidoFK());
		return this.pe.getCiudad();
	}
	
	public String getPedidoComuna(ProductoPedido pp) {
		this.pe = daoPedido.find(pp.getPedidoFK());
		return this.pe.getComuna();
	}
	
	public String getPedidoCalle(ProductoPedido pp) {
		this.pe = daoPedido.find(pp.getPedidoFK());
		return this.pe.getCalle();
	}
	
	public double getPedidoCoste(ProductoPedido pp) {
		this.pe = daoPedido.find(pp.getPedidoFK());
		return this.pe.getCoste();
	}
	
	public String getPedidoEstado(ProductoPedido pp) {
		this.pe = daoPedido.find(pp.getPedidoFK());
		return this.pe.getEstado();
	}
	
	public String getPedidoFecha(ProductoPedido pp) {
		this.pe = daoPedido.find(pp.getPedidoFK());
		return this.pe.getFecha();
	}
	
	public String getPedidoHora(ProductoPedido pp) {
		this.pe = daoPedido.find(pp.getPedidoFK());
		return this.pe.getHora();
	}
	
	public int getPedidoUsuarioFK(ProductoPedido pp) {
		this.pe = daoPedido.find(pp.getPedidoFK());
		return this.pe.getUsuarioFK();
	}
	
	public int getProductoId(ProductoPedido pp) {
		this.pr = daoProducto.find(pp.getProductoFK());
		return this.pr.getId();
	}
	
	public String getProductoNombre(ProductoPedido pp) {
		this.pr = daoProducto.find(pp.getProductoFK());
		return this.pr.getNombre();
	}
	
	public String getProductoDescripcion(ProductoPedido pp) {
		this.pr = daoProducto.find(pp.getProductoFK());
		return this.pr.getDescripcion();
	}
	
	public Double getProductoPrecio(ProductoPedido pp) {
		this.pr = daoProducto.find(pp.getProductoFK());
		return this.pr.getPrecio();
	}
	
	public int getProductoStock(ProductoPedido pp) {
		this.pr = daoProducto.find(pp.getProductoFK());
		return this.pr.getStock();
	}
	
	public String getProductoImagen(ProductoPedido pp) {
		this.pr = daoProducto.find(pp.getProductoFK());
		return this.pr.getImagen();
	}
	
	public int getProductoCategoriaFK(ProductoPedido pp) {
		this.pr = daoProducto.find(pp.getProductoFK());
		return this.pr.getCategoriaFK();
	}
}