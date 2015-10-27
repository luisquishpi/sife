package ec.sife.controllers;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import ec.sife.models.daos.DaoFactory;
import ec.sife.models.daos.TarifaIvaDao;
import ec.sife.models.entities.TarifaIva;
import ec.sife.utils.HibernateUtil;

public class TarifaIvaController {
	private TarifaIvaDao tarifaIvaDao;

	public TarifaIvaController() {
		tarifaIvaDao = new DaoFactory().getTarifaIvaDao();
	}

	public void saveTarifaIva(TarifaIva tarifaIva) {

		tarifaIvaDao.create(tarifaIva);

	}

	public boolean existTarifaIva(TarifaIva tarifaIva) {
		return tarifaIvaDao.read(tarifaIva.getId()) != null;
	}

	public List<TarifaIva> TarifaIvaList() {
		return tarifaIvaDao.findAll();
	}

	public void update(TarifaIva tarifaIva) {
		tarifaIvaDao.update(tarifaIva);
	}

	public boolean delete(Integer id) {
		return tarifaIvaDao.deleteById(id);
	}

	public boolean existTarifaIva(String codigo) {
		boolean existe = false;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {

			session.beginTransaction();
			Query query = session.createQuery("from TarifaIva T WHERE T.codigo = :codigo");
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
