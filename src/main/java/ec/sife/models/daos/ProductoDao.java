package ec.sife.models.daos;

import ec.sife.models.entities.Producto;

public class ProductoDao extends GenericDao<Producto, Integer> {

	public ProductoDao() {
		super(Producto.class);
	}

}
