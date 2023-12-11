package cl.litscl.tiendahardwareejb.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: ProductoPedido
 *
 */
@Entity
@Table(name = "producto_pedido")
@NamedQueries({
	@NamedQuery(name = "ProductoPedido.getAll", query = "SELECT pp FROM ProductoPedido pp")
})
public class ProductoPedido implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "unidades")
	private int unidades;
	@Column(name = "producto_id")
	private int productoFK;
	@Column(name = "pedido_id")
	private int pedidoFK;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUnidades() {
		return unidades;
	}

	public void setUnidades(int unidades) {
		this.unidades = unidades;
	}

	public int getProductoFK() {
		return productoFK;
	}

	public void setProductoFK(int productoFK) {
		this.productoFK = productoFK;
	}

	public int getPedidoFK() {
		return pedidoFK;
	}
	
	public void setPedidoFK(int pedidoFK) {
		this.pedidoFK = pedidoFK;
	}
   
}