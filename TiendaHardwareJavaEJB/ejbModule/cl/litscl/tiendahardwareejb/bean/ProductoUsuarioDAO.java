package cl.litscl.tiendahardwareejb.bean;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import cl.litscl.tiendahardwareejb.model.ProductoUsuario;

/**
 * Session Bean implementation class ProductoUsuarioDAO
 */
@Stateless
public class ProductoUsuarioDAO implements ProductoUsuarioDAOLocal {
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("TiendaHardwareJavaEJB");
	
    /**
     * Default constructor. 
     */
    public ProductoUsuarioDAO() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public boolean save(ProductoUsuario pu) {
		EntityManager em = this.emf.createEntityManager();
		try {
			em.persist(pu);
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
	public List<ProductoUsuario> getAll() {
		EntityManager em = this.emf.createEntityManager();
		try {
			List<ProductoUsuario> productosUsuario = em.createNamedQuery("ProductoUsuario.getAll", ProductoUsuario.class).getResultList();
			return productosUsuario;
		} catch (Exception ex) {
			return null;
		}
		finally {
			em.close();
		}
	}
	
	@Override
	public List<Object[]> getAllFK(String columnaUsuario, String columnaProducto) {
		EntityManager em = this.emf.createEntityManager();
		
		if (columnaUsuario == null) {
			columnaUsuario = "correo";
		}
		
		if (columnaProducto == null) {
			columnaProducto = "id";
		}
		
		TypedQuery<Object[]> query = em.createQuery("SELECT pp.id, p." + columnaProducto + ", u." + columnaUsuario + " FROM ProductoPedido pp INNER JOIN Producto p ON pp.productoFK = p.id INNER JOIN Usuario u ON pp.usuarioFK = u.id", Object[].class);
		List<Object[]> productosPedido = query.getResultList();
		
		return productosPedido;
	}

	@Override
	public ProductoUsuario find(int id) {
		EntityManager em = this.emf.createEntityManager();
		ProductoUsuario pu = new ProductoUsuario();
		try {
			pu = em.find(ProductoUsuario.class, id);
			return pu;
		} catch (Exception ex) {
			return null;
		}
		finally {
			em.close();
		}
	}
	
	@Override
	public List<ProductoUsuario> findAll(String columna, String valor) {
		EntityManager em = this.emf.createEntityManager();
		
		boolean numerico;
		try {
			Integer.parseInt(valor);
			numerico = true;
		} catch (Exception ex) {
			numerico = false;
		}
		
		if (numerico == true) {
			TypedQuery<ProductoUsuario> query = em.createQuery("SELECT pu FROM ProductoUsuario pu WHERE " + "pu." + columna + " = " + valor, ProductoUsuario.class);
			List<ProductoUsuario> productosUsuario = query.getResultList();
			
			return productosUsuario;
		}
		else {
			TypedQuery<ProductoUsuario> query = em.createQuery("SELECT pu FROM ProductoUsuario pu WHERE " + "pu." + columna + " = " + "'" + valor + "'", ProductoUsuario.class);
			List<ProductoUsuario> productosUsuario = query.getResultList();
			
			return productosUsuario;
		}
	}

	@Override
	public boolean update(ProductoUsuario pu) {
		EntityManager em = this.emf.createEntityManager();
		ProductoUsuario productoUsuarioBD = new ProductoUsuario();
		try {
			productoUsuarioBD = em.find(ProductoUsuario.class, pu.getId());
			productoUsuarioBD.setId(pu.getId());
			productoUsuarioBD.setUsuarioFK(pu.getUsuarioFK());
			productoUsuarioBD.setProductoFK(pu.getProductoFK());
			em.merge(productoUsuarioBD);
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
		ProductoUsuario pu = em.find(ProductoUsuario.class, id);
		
		try {
			pu = em.find(ProductoUsuario.class, id);
			em.remove(pu);
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