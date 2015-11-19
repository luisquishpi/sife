package ec.sife.controllers;

import java.util.List;
import ec.sife.models.daos.DaoFactory;
import ec.sife.models.daos.PrecioProductoDao;
import ec.sife.models.entities.PrecioProducto;

public class PrecioProductoController {

	private PrecioProductoDao precioProductoDao;

	public PrecioProductoController() {
		precioProductoDao = new DaoFactory().getPrecioProductoDao();
	}

	public void saveTipoPrecio(PrecioProducto precioProducto) {
		precioProductoDao.create(precioProducto);
	}

	public boolean existTipoPrecio(PrecioProducto precioProducto) {
		return precioProductoDao.read(precioProducto.getId()) != null;
	}

	public List<PrecioProducto> PrecioProductoList() {
		return precioProductoDao.findAll();
	}

	public PrecioProducto getPrecioProducto(int id) {
		return DaoFactory.getFactory().getPrecioProductoDao().read(id);
	}

	public void update(PrecioProducto precioProducto) {
		precioProductoDao.update(precioProducto);
	}

	public boolean delete(Integer id) {
		return precioProductoDao.deleteById(id);
	}
}
