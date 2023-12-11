package cl.litscl.tiendahardwareejb.bean;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import cl.litscl.tiendahardwareejb.model.Producto;
import cl.litscl.tiendahardwareejb.model.ProductoPedido;

/**
 * Session Bean implementation class ProductoPedidoDAO
 */
@Stateless
public class ProductoPedidoDAO implements ProductoPedidoDAOLocal {
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("TiendaHardwareJavaEJB");
	
    /**
     * Default constructor. 
     */
    public ProductoPedidoDAO() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public boolean save(List<Object[]> carrito, int idPedido) {
		EntityManager em = this.emf.createEntityManager();
		try {
			for (Object[] o : carrito) {
				Producto producto = (Producto)o[3];
				
				int stockActual = producto.getStock();
				int stockNuevo = stockActual - (int)o[2];
				
				new ProductoDAO().updateOne(producto.getId(), "stock", Integer.toString(stockNuevo));
				
				ProductoPedido pp = new ProductoPedido();
				pp.setPedidoFK(idPedido);
				pp.setProductoFK(producto.getId());
				pp.setUnidades((int)o[2]);
				
				em.persist(pp);
				em.flush();
			}
			return true;
		} catch (Exception ex) {
			return false;
		}
		finally {
			em.close();
		}
	}

	@Override
	public List<ProductoPedido> getAll() {
		EntityManager em = this.emf.createEntityManager();
		try {
			List<ProductoPedido> productosPedido = em.createNamedQuery("ProductoPedido.getAll", ProductoPedido.class).getResultList();
			return productosPedido;
		} catch (Exception ex) {
			return null;
		}
		finally {
			em.close();
		}
	}
	
	@Override
	public List<Object[]> getAllFK(String columnaPedido, String columnaProducto) {
		EntityManager em = this.emf.createEntityManager();
		
		if (columnaPedido == null) {
			columnaPedido = "id";
		}
		
		if (columnaProducto == null) {
			columnaProducto = "id";
		}
		
		TypedQuery<Object[]> query = em.createQuery("SELECT pp.id, pr." + columnaProducto + ", pe." + columnaPedido + " FROM ProductoPedido pp INNER JOIN Producto pr ON pp.productoFK = pr.id INNER JOIN Pedido pe ON pp.pedidoFK = pe.id", Object[].class);
		List<Object[]> productosPedido = query.getResultList();
		
		return productosPedido;
	}

	@Override
	public ProductoPedido find(int id) {
		EntityManager em = this.emf.createEntityManager();
		ProductoPedido pp = new ProductoPedido();
		try {
			pp = em.find(ProductoPedido.class, id);
			return pp;
		} catch (Exception ex) {
			return null;
		}
		finally {
			em.close();
		}
	}
	
	@Override
	public List<ProductoPedido> findAll(String columna, String valor) {
		EntityManager em = this.emf.createEntityManager();
		
		boolean numerico;
		try {
			Integer.parseInt(valor);
			numerico = true;
		} catch (Exception ex) {
			numerico = false;
		}
		
		if (numerico == true) {
			TypedQuery<ProductoPedido> query = em.createQuery("SELECT pp FROM ProductoPedido pp WHERE " + "pp." + columna + " = " + valor, ProductoPedido.class);
			List<ProductoPedido> productosPedido = query.getResultList();
			
			return productosPedido;
		}
		else {
			TypedQuery<ProductoPedido> query = em.createQuery("SELECT pp FROM ProductoPedido pp WHERE " + "pp." + columna + " = " + "'" + valor + "'", ProductoPedido.class);
			List<ProductoPedido> productosPedido = query.getResultList();
			
			return productosPedido;
		}
	}

	@Override
	public boolean update(ProductoPedido pp) {
		EntityManager em = this.emf.createEntityManager();
		ProductoPedido productoPedidoBD = new ProductoPedido();
		try {
			productoPedidoBD = em.find(ProductoPedido.class, pp.getId());
			productoPedidoBD.setId(pp.getId());
			productoPedidoBD.setPedidoFK(pp.getPedidoFK());
			productoPedidoBD.setProductoFK(pp.getProductoFK());
			productoPedidoBD.setUnidades(pp.getUnidades());
			em.merge(productoPedidoBD);
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
		ProductoPedido pp = em.find(ProductoPedido.class, id);
		
		try {
			pp = em.find(ProductoPedido.class, id);
			em.remove(pp);
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