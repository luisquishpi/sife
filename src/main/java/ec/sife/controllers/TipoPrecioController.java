package ec.sife.controllers;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import ec.sife.models.daos.DaoFactory;
import ec.sife.models.daos.TipoPrecioDao;
import ec.sife.models.entities.TipoPrecio;
import ec.sife.utils.HibernateUtil;

public class TipoPrecioController {

    private TipoPrecioDao tipoPrecioDao;

    public TipoPrecioController() {
        tipoPrecioDao = new DaoFactory().getTipoPrecioDao();
    }

    public void saveTipoPrecio(TipoPrecio tipoPrecio) {
        tipoPrecioDao.create(tipoPrecio);
    }

    public boolean existTipoPrecio(TipoPrecio tipoPrecio) {
        return tipoPrecioDao.read(tipoPrecio.getId()) != null;
    }

    public List<TipoPrecio> TipoPrecioList() {
        return tipoPrecioDao.findAll();
    }

    public TipoPrecio getTipoPrecio(int selectedTipoPrecioId) {
        return DaoFactory.getFactory().getTipoPrecioDao().read(selectedTipoPrecioId);
    }

    public void update(TipoPrecio tipoPrecio) {
        tipoPrecioDao.update(tipoPrecio);
    }

    public boolean delete(Integer id) {
        return tipoPrecioDao.deleteById(id);
    }

    public boolean existTipoPrecio(String nombre) {
        boolean existe = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Query query = session.createQuery("from TipoPrecio T WHERE T.nombre = :nombre");
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
