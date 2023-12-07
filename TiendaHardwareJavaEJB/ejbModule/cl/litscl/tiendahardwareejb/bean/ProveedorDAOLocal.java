package cl.litscl.tiendahardwareejb.bean;

import java.util.List;

import javax.ejb.Local;

import cl.litscl.tiendahardwareejb.model.Proveedor;

@Local
public interface ProveedorDAOLocal {
	public boolean save(Proveedor p);
	public List<Proveedor> getAll();
	public Proveedor find(int id);
	public List<Proveedor> findAll(String columna, String valor);
	public boolean update(Proveedor p);
	public boolean delete(int id);
	public List<Object[]> customQuery(String jpql);
}