package ec.sife.controllers;

import java.util.List;

import ec.sife.models.daos.DaoFactory;
import ec.sife.models.daos.ProductoDao;
import ec.sife.models.entities.Producto;

public class ProductoController {

	private ProductoDao productoDao;

	public ProductoController() {
		productoDao = new DaoFactory().getProductoDao();
	}

	public void saveTipoPrecio(Producto producto) {
		productoDao.create(producto);
	}

	public boolean existTipoPrecio(Producto producto) {
		return productoDao.read(producto.getId()) != null;
	}

	public List<Producto> TipoPrecioList() {
		return productoDao.findAll();
	}

	public Producto getTipoPrecio(int selectedProductoId) {
		return DaoFactory.getFactory().getProductoDao().read(selectedProductoId);
	}

	public boolean update(Producto producto) {
		return productoDao.update(producto);
	}

	public boolean delete(Integer id) {
		return productoDao.deleteById(id);
	}
}
