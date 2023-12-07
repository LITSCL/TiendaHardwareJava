package cl.litscl.tiendahardwareejb.bean;

import java.util.List;

import javax.ejb.Local;

import cl.litscl.tiendahardwareejb.model.Pedido;

@Local
public interface PedidoDAOLocal {
	public boolean save(Pedido p);
	public List<Pedido> getAll();
	public List<Object[]> getAllFK(String columnaUsuario);
	public Pedido find(int id);
	public List<Pedido> findAll(String columna, String valor);
	public boolean update(Pedido p);
	public boolean updateOne(int id, String columna, String valor);
	public boolean delete(int id);
	public List<Object[]> customQuery(String jpql);
}