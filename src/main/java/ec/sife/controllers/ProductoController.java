package ec.sife.controllers;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import ec.sife.models.daos.DaoFactory;
import ec.sife.models.daos.ProductoDao;
import ec.sife.models.entities.Producto;
import ec.sife.utils.HibernateUtil;

public class ProductoController {

	private ProductoDao productoDao;

	public ProductoController() {
		productoDao = new DaoFactory().getProductoDao();
	}

	public void saveProducto(Producto producto) {
		productoDao.create(producto);
	}

	public boolean existProducto(Producto producto) {
		return productoDao.read(producto.getId()) != null;
	}

	public List<Producto> ProductoList() {
		return productoDao.findAll();
	}

	public Producto getProducto(int selectedProductoId) {
		return DaoFactory.getFactory().getProductoDao().read(selectedProductoId);
	}

	public boolean update(Producto producto) {
		return productoDao.update(producto);
	}

	public boolean delete(Integer id) {
		return productoDao.deleteById(id);
	}

	public Producto getProducto(String codigo) {
		Producto productoReturn = new Producto();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery("from Producto T WHERE T.codigo = :codigo");
			query.setParameter("codigo", codigo);
			if (!query.list().isEmpty()) {
				productoReturn=(Producto)query.list().get(0);
				return productoReturn;
			}
			session.getTransaction().commit();
		} catch (HibernateException e) {
			if (session.getTransaction() != null)
				e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return null;
	}
}
