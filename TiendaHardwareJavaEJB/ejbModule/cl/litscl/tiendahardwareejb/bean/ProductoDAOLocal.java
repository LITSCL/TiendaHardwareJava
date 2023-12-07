package cl.litscl.tiendahardwareejb.bean;

import java.util.List;

import javax.ejb.Local;

import cl.litscl.tiendahardwareejb.model.Producto;

@Local
public interface ProductoDAOLocal {
	public boolean save(Producto p);
	public List<Producto> getAll();
	public List<Object[]> getAllFK(String columnaCategoria);
	public Producto find(int id);
	public List<Producto> findAll(String columna, String valor);
	public boolean update(Producto p);
	public boolean updateOne(int id, String columna, String valor);
	public boolean delete(int id);
	public List<Object[]> customQuery(String jpql);
}