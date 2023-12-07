package cl.litscl.tiendahardwareejb.bean;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import cl.litscl.tiendahardwareejb.model.Proveedor;

/**
 * Session Bean implementation class ProveedorDAO
 */
@Stateless
public class ProveedorDAO implements ProveedorDAOLocal {
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("TiendaHardwareJavaEJB");
	
    /**
     * Default constructor. 
     */
    public ProveedorDAO() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public boolean save(Proveedor p) {
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
	public List<Proveedor> getAll() {
		EntityManager em = this.emf.createEntityManager();
		try {
			List<Proveedor> proveedor = em.createNamedQuery("Proveedor.getAll", Proveedor.class).getResultList();
			return proveedor;
		} catch (Exception ex) {
			return null;
		}
		finally {
			em.close();
		}
	}

	@Override
	public Proveedor find(int id) {
		EntityManager em = this.emf.createEntityManager();
		Proveedor p = new Proveedor();
		try {
			p = em.find(Proveedor.class, id);
			return p;
		} catch (Exception ex) {
			return null;
		}
		finally {
			em.close();
		}
	}
	
	@Override
	public List<Proveedor> findAll(String columna, String valor) {
		EntityManager em = this.emf.createEntityManager();
		
		boolean numerico;
		try {
			Integer.parseInt(valor);
			numerico = true;
		} catch (Exception ex) {
			numerico = false;
		}
		
		if (numerico == true) {
			TypedQuery<Proveedor> query = em.createQuery("SELECT p FROM Proveedor p WHERE " + "p." + columna + " = " + valor, Proveedor.class);
			List<Proveedor> proveedores = query.getResultList();
			
			return proveedores;
		}
		else {
			TypedQuery<Proveedor> query = em.createQuery("SELECT p FROM Proveedor p WHERE " + "p." + columna + " = " + "'" + valor + "'", Proveedor.class);
			List<Proveedor> proveedores = query.getResultList();
			
			return proveedores;
		}
	}

	@Override
	public boolean update(Proveedor p) {
		EntityManager em = this.emf.createEntityManager();
		Proveedor proveedorBD = new Proveedor();
		try {
			proveedorBD = em.find(Proveedor.class, p.getId());
			proveedorBD.setId(p.getId());
			proveedorBD.setNombre(p.getNombre());
			proveedorBD.setTelefono(p.getTelefono());
			proveedorBD.setCorreo(p.getCorreo());
			em.merge(proveedorBD);
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
	public boolean delete(int id) {
		EntityManager em = this.emf.createEntityManager();
		Proveedor p = em.find(Proveedor.class, id);
		
		try {
			p = em.find(Proveedor.class, id);
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