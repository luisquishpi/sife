package ec.sife.controllers;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import ec.sife.models.daos.DaoFactory;
import ec.sife.models.daos.TipoIdentificacionPersonaDao;
import ec.sife.models.entities.TipoIdentificacionPersona;
import ec.sife.utils.HibernateUtil;

public class TipoIdentificacionPersonaController {
	
	private TipoIdentificacionPersonaDao  tipoIdentificacionPersonaDao;

	public TipoIdentificacionPersonaController() {
		tipoIdentificacionPersonaDao = new DaoFactory().getTipoIdentificacionPersonaDao();
	}

	public void saveTipoIdentificacionPersona(TipoIdentificacionPersona tipoIdentificacionPersona) {
		tipoIdentificacionPersonaDao.create(tipoIdentificacionPersona);
	}

	public boolean existTipoIdentificacionPersona(TipoIdentificacionPersona tipoIdentificacionPersona) {
		return tipoIdentificacionPersonaDao.read(tipoIdentificacionPersona.getId()) != null;
	}

	public List<TipoIdentificacionPersona> TipoIdentificacionPersonaList() {
		return tipoIdentificacionPersonaDao.findAll();
	}

	public boolean updateTipoIdentificacionPersona(TipoIdentificacionPersona tipoIdentificacionPersona) {
		return tipoIdentificacionPersonaDao.update(tipoIdentificacionPersona);
		
	}

	public boolean delete(Integer id) {
		return tipoIdentificacionPersonaDao.deleteById(id);
	}
	public boolean existTipoIdentificacionPersona(String nombre) {
        boolean existe = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createQuery("from TipoIdentificacionPersona  T WHERE T.nombre = :nombre");
            query.setParameter("nombre", nombre);
            if(!query.list().isEmpty()){
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
