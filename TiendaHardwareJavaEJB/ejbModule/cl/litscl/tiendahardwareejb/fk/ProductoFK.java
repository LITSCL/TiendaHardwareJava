package cl.litscl.tiendahardwareejb.fk;

import cl.litscl.tiendahardwareejb.bean.CategoriaDAO;
import cl.litscl.tiendahardwareejb.bean.CategoriaDAOLocal;
import cl.litscl.tiendahardwareejb.model.Categoria;
import cl.litscl.tiendahardwareejb.model.Producto;

public class ProductoFK {
	private CategoriaDAOLocal daoCategoria = new CategoriaDAO();
	private Categoria c = new Categoria();
	
	public int getCategoriaId(Producto p) {
		this.c = daoCategoria.find(p.getCategoriaFK());
		return this.c.getId();
	}
	
	public String getCategoriaNombre(Producto p) {
		this.c = daoCategoria.find(p.getCategoriaFK());
		return this.c.getNombre();
	}
}