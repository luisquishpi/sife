package ec.sife.controllers;

import java.util.List;

import ec.sife.models.daos.DaoFactory;
import ec.sife.models.daos.TipoPrecioDao;
import ec.sife.models.entities.TipoPrecio;

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
}
