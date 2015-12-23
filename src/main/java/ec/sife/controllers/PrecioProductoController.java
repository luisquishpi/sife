package ec.sife.controllers;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import ec.sife.models.daos.DaoFactory;
import ec.sife.models.daos.PrecioProductoDao;
import ec.sife.models.entities.PrecioProducto;
import ec.sife.models.entities.Producto;
import ec.sife.utils.HibernateUtil;

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

	public boolean deletePreciosPorIdProducto(Producto producto) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery("delete from PrecioProducto T WHERE T.producto = :producto");
			query.setParameter("producto", producto);
			int result = query.executeUpdate();
			if (result > 0) {
			    return true;
			}
		} catch (HibernateException e) {
			if (session.getTransaction() != null)
				e.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return false;
	}
}
