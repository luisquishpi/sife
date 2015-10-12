package ec.sife.controllers;

import java.util.List;

import ec.sife.models.daos.DaoFactory;
import ec.sife.models.entities.CategoriaProducto;
import ec.sife.models.daos.CategoriaProductoDao;

public class CategoriaProductoController {

	private CategoriaProductoDao categoriaProductoDao;

	public CategoriaProductoController() {
		categoriaProductoDao = new DaoFactory().getCategoriaProductoDao();
	}

	public void saveCategoriaProducto(CategoriaProducto categoriaProducto) {
		categoriaProductoDao.create(categoriaProducto);
	}

	public boolean existCategoriaProducto(CategoriaProducto categoriaProducto) {
		return categoriaProductoDao.read(categoriaProducto.getId()) != null;
	}

	public List<CategoriaProducto> CategoriaProductoList() {
		return categoriaProductoDao.findAll();
	}

	public CategoriaProducto getCateriaProducto(int selectedCategoriaProductoId) {
		return DaoFactory.getFactory().getCategoriaProductoDao().read(selectedCategoriaProductoId);
	}

	public void update(CategoriaProducto categoriaProducto) {
		categoriaProductoDao.update(categoriaProducto);
	}

	public boolean delete(Integer id) {
		return categoriaProductoDao.deleteById(id);
	}

}
