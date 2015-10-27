package ec.sife.models.daos;

public class DaoFactory {
	public static DaoFactory factory = null;

	public static void setFactory(DaoFactory factory) {
		DaoFactory.factory = factory;
	}

	public static DaoFactory getFactory() {
		if (factory == null) {
			factory = new DaoFactory();
		}
		return factory;
	}

	public TipoPrecioDao getTipoPrecioDao() {
		return new TipoPrecioDao();
	}

	public CategoriaProductoDao getCategoriaProductoDao() {
		return new CategoriaProductoDao();
	}
	public TipoClienteDao getTipoClienteDao() {
		return new TipoClienteDao();
	}
	public TipoIdentificacionPersonaDao getTipoIdentificacionPersonaDao() {
		return new TipoIdentificacionPersonaDao();
	}
	public PersonaDao getPersonaDao() {
		return new PersonaDao();
	}
	public TarifaIvaDao getTarifaIvaDao() {
		return new TarifaIvaDao();
	}
	public TarifaIceDao getTarifaIceDao() {
		return new TarifaIceDao();
	}

}
