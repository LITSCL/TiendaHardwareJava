package cl.litscl.tiendahardwareejb.bean;

import java.util.List;

import javax.ejb.Local;

import cl.litscl.tiendahardwareejb.model.ProductoPedido;

@Local
public interface ProductoPedidoDAOLocal {
	public boolean save(List<Object[]> carrito, int idPedido);
	public List<ProductoPedido> getAll();
	public List<Object[]> getAllFK(String columnaPedido, String columnaProducto);
	public ProductoPedido find(int id);
	public List<ProductoPedido> findAll(String columna, String valor);
	public boolean update(ProductoPedido pp);
	public boolean delete(int id);
	public List<Object[]> customQuery(String jpql);
}