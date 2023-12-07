package cl.litscl.tiendahardwareejb.bean;

import java.util.List;

import javax.ejb.Local;

import cl.litscl.tiendahardwareejb.model.ProductoUsuario;

@Local
public interface ProductoUsuarioDAOLocal {
	public boolean save(ProductoUsuario pu);
	public List<ProductoUsuario> getAll();
	public List<Object[]> getAllFK(String columnaUsuario, String columnaProducto);
	public ProductoUsuario find(int id);
	public List<ProductoUsuario> findAll(String columna, String valor);
	public boolean update(ProductoUsuario pu);
	public boolean delete(int id);
	public List<Object[]> customQuery(String jpql);
}