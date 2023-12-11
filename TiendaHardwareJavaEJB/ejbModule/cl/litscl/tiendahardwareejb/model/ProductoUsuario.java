package cl.litscl.tiendahardwareejb.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: ProductoUsuario
 *
 */
@Entity
@Table(name = "producto_usuario")
@NamedQueries({
	@NamedQuery(name = "ProductoUsuario.getAll", query = "SELECT pu FROM ProductoUsuario pu")
})
public class ProductoUsuario implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "producto_id")
	private int productoFK;
	@Column(name = "usuario_id")
	private int usuarioFK;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProductoFK() {
		return productoFK;
	}

	public void setProductoFK(int productoFK) {
		this.productoFK = productoFK;
	}

	public int getUsuarioFK() {
		return usuarioFK;
	}
	
	public void setUsuarioFK(int usuarioFK) {
		this.usuarioFK = usuarioFK;
	}
   
}