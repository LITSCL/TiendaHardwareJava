package cl.litscl.tiendahardwareejb.bean;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import cl.litscl.tiendahardwareejb.model.Pedido;

/**
 * Session Bean implementation class PedidoDAO
 */
@Stateless
public class PedidoDAO implements PedidoDAOLocal {
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("TiendaHardwareJavaEJB");
	
    /**
     * Default constructor. 
     */
    public PedidoDAO() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public boolean save(Pedido p) {
		EntityManager em = this.emf.createEntityManager();
		try {
			em.persist(p);
			em.flush();
			return true;
		} catch (Exception ex) {
			return false;
		}
		finally {
			em.close();
		}
	}

	@Override
	public List<Pedido> getAll() {
		EntityManager em = this.emf.createEntityManager();
		try {
			List<Pedido> pedidos = em.createNamedQuery("Pedido.getAll", Pedido.class).getResultList();
			return pedidos;
		} catch (Exception ex) {
			return null;
		}
		finally {
			em.close();
		}
	}
	
	@Override
	public List<Object[]> getAllFK(String columnaUsuario) {
		EntityManager em = this.emf.createEntityManager();
		
		if (columnaUsuario == null) {
			columnaUsuario = "correo";
		}
		
		TypedQuery<Object[]> query = em.createQuery("SELECT p.id, p.ciudad, p.comuna, p.calle, p.coste, p.estado, p.fecha, p.hora, u." + columnaUsuario + " FROM Pedido p INNER JOIN Usuario u ON p.usuarioFK = u.correo", Object[].class);
		List<Object[]> pedidos = query.getResultList();
		
		return pedidos;
	}

	@Override
	public Pedido find(int id) {
		EntityManager em = this.emf.createEntityManager();
		Pedido p = new Pedido();
		try {
			p = em.find(Pedido.class, id);
			return p;
		} catch (Exception ex) {
			return null;
		}
		finally {
			em.close();
		}
	}
	
	@Override
	public List<Pedido> findAll(String columna, String valor) {
		EntityManager em = this.emf.createEntityManager();
		
		boolean numerico;
		try {
			Integer.parseInt(valor);
			numerico = true;
		} catch (Exception ex) {
			numerico = false;
		}
		
		if (numerico == true) {
			TypedQuery<Pedido> query = em.createQuery("SELECT p FROM Pedido p WHERE " + "p." + columna + " = " + valor, Pedido.class);
			List<Pedido> pedidos = query.getResultList();
			
			return pedidos;
		}
		else {
			TypedQuery<Pedido> query = em.createQuery("SELECT p FROM Pedido p WHERE " + "p." + columna + " = " + "'" + valor + "'", Pedido.class);
			List<Pedido> pedidos = query.getResultList();
			
			return pedidos;
		}
	}
	
	@Override
	public boolean update(Pedido p) {
		EntityManager em = this.emf.createEntityManager();
		Pedido pedidoBD = new Pedido();
		try {
			pedidoBD = em.find(Pedido.class, p.getId());
			pedidoBD.setId(p.getId());
			pedidoBD.setComuna(p.getComuna());
			pedidoBD.setCalle(p.getCalle());
			pedidoBD.setCoste(p.getCoste());
			pedidoBD.setEstado(p.getEstado());
			pedidoBD.setFecha(p.getFecha());
			pedidoBD.setHora(p.getHora());
			pedidoBD.setUsuarioFK(p.getUsuarioFK());
			em.merge(pedidoBD);
			em.flush();
			return true;
		} catch (Exception ex) {
			return false;
		}
		finally {
			em.close();
		}
	}
	
	@Override
	public boolean updateOne(int id, String columna, String valor) {
		EntityManager em = this.emf.createEntityManager(); 	
		
		boolean numerico;
		try {
			Integer.parseInt(valor);
			numerico = true;
		} catch (Exception ex) {
			numerico = false;
		}
		
		if (numerico == true) {
			try {
				Query query = em.createQuery("UPDATE Pedido SET " + columna + " = " + valor + " WHERE id" + " = " + id); 
				query.executeUpdate();
				return true;
			} catch (Exception ex) {
				return false;
			}
		}
		else {
			try {
				Query query = em.createQuery("UPDATE Pedido SET " + columna + " = " + "'" + valor + "'" + " WHERE id" + " = " + id); 
				query.executeUpdate();
				return true;
			} catch (Exception ex) {
				return false;
			}
		}
	}
	
	@Override
	public boolean delete(int id) {
		EntityManager em = this.emf.createEntityManager();
		Pedido p = em.find(Pedido.class, id);
		
		try {
			p = em.find(Pedido.class, id);
			em.remove(p);
			em.flush();
			return true;
		} catch (Exception ex) {
			return false;
		}
		finally {
			em.close();
		}
	}
	
	@Override
	public List<Object[]> customQuery(String jpql) {
		EntityManager em = this.emf.createEntityManager(); 
		try {
			TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class); 
			List<Object[]> resultado = query.getResultList(); 
			
			return resultado;
		} catch (Exception ex) {
			return null;
		}
	}
}