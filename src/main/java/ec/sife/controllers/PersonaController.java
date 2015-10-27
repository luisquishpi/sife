package ec.sife.controllers;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import ec.sife.models.daos.DaoFactory;
import ec.sife.models.daos.PersonaDao;
import ec.sife.models.entities.Persona;

import ec.sife.utils.HibernateUtil;

public class PersonaController {

	private PersonaDao personaDao;

	public PersonaController() {
		personaDao = new DaoFactory().getPersonaDao();
	}

	public void savePersona(Persona persona) {
		personaDao.create(persona);

	}

	public boolean existPersona(Persona persona) {
		return personaDao.read(persona.getId()) != null;
	}

	public List<Persona> personaList() {
		return personaDao.findAll();
	}

	public void update(Persona persona) {
		personaDao.update(persona);
	}

	public boolean delete(Integer id) {
		return personaDao.deleteById(id);
	}

	public boolean existPersona(String identificacion) {
		boolean existe = false;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {

			session.beginTransaction();
			Query query = session.createQuery("from Persona T WHERE T.identificacion = :identificacion");
			query.setParameter("identificacion", identificacion);
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
