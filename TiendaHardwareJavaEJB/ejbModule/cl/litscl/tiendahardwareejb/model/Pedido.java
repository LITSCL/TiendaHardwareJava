package cl.litscl.tiendahardwareejb.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Pedido
 *
 */
@Entity
@Table(name = "pedido")
@NamedQueries({
	@NamedQuery(name = "Pedido.getAll", query = "SELECT p FROM Pedido p")
})
public class Pedido implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "ciudad")
	private String ciudad;
	@Column(name = "comuna")
	private String comuna;
	@Column(name = "calle")
	private String calle;
	@Column(name = "coste")
	private double coste;
	@Column(name = "estado")
	private String estado;
	@Column(name = "fecha")
	private String fecha;
	@Column(name = "hora")
	private String hora;
	@Column(name = "usuario_id")
	private int usuarioFK;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public String getComuna() {
		return comuna;
	}
	public void setComuna(String comuna) {
		this.comuna = comuna;
	}
	public String getCalle() {
		return calle;
	}
	public void setCalle(String calle) {
		this.calle = calle;
	}
	public double getCoste() {
		return coste;
	}
	public void setCoste(double coste) {
		this.coste = coste;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public int getUsuarioFK() {
		return usuarioFK;
	}
	public void setUsuarioFK(int usuarioFK) {
		this.usuarioFK = usuarioFK;
	}
	
}