package cl.litscl.tiendahardwareejb.bean;

import java.util.List;

import javax.ejb.Local;

import cl.litscl.tiendahardwareejb.model.Usuario;

@Local
public interface UsuarioDAOLocal {
	public boolean save(Usuario u);
	public List<Usuario> getAll();
	public Usuario find(int id);
	public Usuario findOne(String columna, String valor);
	public List<Usuario> findAll(String columna, String valor);
	public boolean update(Usuario u);
	public boolean delete(String correo);
	public List<Object[]> customQuery(String jpql);
}