package ec.sife.models.entities;

public class CalculoPrecioProducto {
	
	private TipoPrecio tipoPrecio;
	private TarifaIva tarifaIva;
	private TarifaIce tarifaIce;
	private Double preciobruto;
	private Double total;
	private Double utilidad;
	public CalculoPrecioProducto(TipoPrecio tipoPrecio, TarifaIva tarifaIva, TarifaIce tarifaIce, Double subTotal,
			Double total, Double utilidad) {
		super();
		this.tipoPrecio = tipoPrecio;
		this.tarifaIva = tarifaIva;
		this.tarifaIce = tarifaIce;
		this.preciobruto = subTotal;
		this.total = total;
		this.utilidad = utilidad;
	}
	

}
