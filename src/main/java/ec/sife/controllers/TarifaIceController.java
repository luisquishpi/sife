package ec.sife.controllers;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import ec.sife.models.daos.DaoFactory;
import ec.sife.models.daos.TarifaIceDao;
import ec.sife.models.entities.TarifaIce;
import ec.sife.utils.HibernateUtil;

public class TarifaIceController {

	private TarifaIceDao tarifaIceDao;

	public TarifaIceController() {
		tarifaIceDao = new DaoFactory().getTarifaIceDao();
	}

	public void saveTarifaIce(TarifaIce tarifaIce) {

		tarifaIceDao.create(tarifaIce);

	}

	public boolean existTarifaIce(TarifaIce tarifaIce) {
		return tarifaIceDao.read(tarifaIce.getId()) != null;
	}

	public List<TarifaIce> TarifaIceList() {
		return tarifaIceDao.findAll();
	}

	public void update(TarifaIce tarifaIce) {
		tarifaIceDao.update(tarifaIce);
	}

	public boolean delete(Integer id) {
		return tarifaIceDao.deleteById(id);
	}

	public boolean existTarifaIce(String codigo) {
		boolean existe = false;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {

			System.out.println("Valoraaaa"+ codigo  );
			session.beginTransaction();
			Query query = session.createQuery("from TarifaIce T WHERE T.codigo = :codigo");
			query.setParameter("codigo", codigo);
			if (!query.list().isEmpty()) {
				existe = true;
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
		return existe;
	}
}
