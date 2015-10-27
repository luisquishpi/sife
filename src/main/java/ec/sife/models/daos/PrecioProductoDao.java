package ec.sife.models.daos;

import ec.sife.models.entities.PrecioProducto;

public class PrecioProductoDao extends GenericDao<PrecioProducto, Integer> {

	public PrecioProductoDao() {
		super(PrecioProducto.class);
	}
}
