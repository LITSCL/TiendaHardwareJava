package cl.litscl.tiendahardwareejb.bean;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import cl.litscl.tiendahardwareejb.model.Producto;

/**
 * Session Bean implementation class ProductoDAO
 */
@Stateless
public class ProductoDAO implements ProductoDAOLocal {
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("TiendaHardwareJavaEJB");

    /**
     * Default constructor. 
     */
    public ProductoDAO() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public boolean save(Producto p) {
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
	public List<Producto> getAll() {
		EntityManager em = this.emf.createEntityManager();
		try {
			List<Producto> productos = em.createNamedQuery("Producto.getAll", Producto.class).getResultList();
			return productos;
		} catch (Exception ex) {
			return null;
		}
		finally {
			em.close();
		}
	}
	
	@Override
	public List<Object[]> getAllFK(String columnaCategoria) {
		EntityManager em = this.emf.createEntityManager();
		
		if (columnaCategoria == null) {
			columnaCategoria = "id";
		}
		
		TypedQuery<Object[]> query = em.createQuery("SELECT p.id, p.nombre, p.descripcion, p.precio, p.stock, p.imagen, c." + columnaCategoria + " FROM Producto p INNER JOIN Categoria c ON p.categoriaFK = c.id", Object[].class);
		List<Object[]> productos = query.getResultList();
		
		return productos;
	}

	@Override
	public Producto find(int id) {
		EntityManager em = this.emf.createEntityManager();
		Producto p = new Producto();
		try {
			p = em.find(Producto.class, id);
			return p;
		} catch (Exception ex) {
			return null;
		}
		finally {
			em.close();
		}
	}
	
	@Override
	public List<Producto> findAll(String columna, String valor) {
		EntityManager em = this.emf.createEntityManager();
		
		boolean numerico;
		try {
			Integer.parseInt(valor);
			numerico = true;
		} catch (Exception ex) {
			numerico = false;
		}
		
		if (numerico == true) {
			TypedQuery<Producto> query = em.createQuery("SELECT p FROM Producto p WHERE " + "p." + columna + " = " + valor, Producto.class);
			List<Producto> productos = query.getResultList();
			
			return productos;
		}
		else {
			TypedQuery<Producto> query = em.createQuery("SELECT p FROM Producto p WHERE " + "p." + columna + " = " + "'" + valor + "'", Producto.class);
			List<Producto> productos = query.getResultList();
			
			return productos;
		}
	}

	@Override
	public boolean update(Producto p) {
		EntityManager em = this.emf.createEntityManager();
		Producto productoBD = new Producto();
		try {
			productoBD = em.find(Producto.class, p.getId());
			productoBD.setId(p.getId());
			productoBD.setNombre(p.getNombre());
			productoBD.setDescripcion(p.getDescripcion());
			productoBD.setPrecio(p.getPrecio());
			productoBD.setStock(p.getStock());
			productoBD.setImagen(p.getImagen());
			productoBD.setCategoriaFK(p.getCategoriaFK());
			em.merge(productoBD);
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
				Query query = em.createQuery("UPDATE Producto SET " + columna + " = " + valor + " WHERE id" + " = " + id); 
				query.executeUpdate();
				return true;
			} catch (Exception ex) {
				return false;
			}
		}
		else {
			try {
				Query query = em.createQuery("UPDATE Producto SET " + columna + " = " + "'" + valor + "'" + " WHERE id" + " = " + id); 
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
		Producto p = em.find(Producto.class, id);
		
		try {
			p = em.find(Producto.class, id);
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