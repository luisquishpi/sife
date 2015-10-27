package ec.sife.controllers;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import ec.sife.models.daos.DaoFactory;
import ec.sife.models.daos.TipoClienteDao;
import ec.sife.models.entities.TipoCliente;
import ec.sife.utils.HibernateUtil;

public class TipoClienteController {

    private TipoClienteDao tipoClienteDao;

    public TipoClienteController() {
    	tipoClienteDao = new DaoFactory().getTipoClienteDao();
    }

    public void saveTipoCliente(TipoCliente tipoCliente) {
    	 System.out.println("Valor1" + tipoCliente );
    	tipoClienteDao.create(tipoCliente);
    	 System.out.println("Valor2" + tipoCliente );
    }

    public boolean existTipoCliente(TipoCliente tipoCliente) {
        return tipoClienteDao.read(tipoCliente.getId()) != null;
    }

    public List<TipoCliente> TipoClienteList() {
        return tipoClienteDao.findAll();
    }

    public TipoCliente getTipoCliente(int selectedTipoClienteId) {
        return DaoFactory.getFactory().getTipoClienteDao().read(selectedTipoClienteId);
    }

    public void update(TipoCliente tipoCliente) {
    	tipoClienteDao.update(tipoCliente);
    }

    public boolean delete(Integer id) {
        return tipoClienteDao.deleteById(id);
    }

    public boolean existTipoCliente(String nombre) {
        boolean existe = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
        	System.out.println("Valoraaaa"  );
            session.beginTransaction();
            Query query = session.createQuery("from TipoCliente T WHERE T.nombre = :nombre");
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
