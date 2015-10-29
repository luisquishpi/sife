package ec.sife.models.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "precioproducto")
public class PrecioProducto {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "porcentajeutilidad", nullable = false)
	private Double porcentajeUtilidad;

	@Column(name = "preciobruto", nullable = false)
	private Double precioBruto;

	@Column(name = "utilidad", nullable = false)
	private Double utilidad;

	@ManyToOne(cascade = CascadeType.REFRESH, optional = false)
	private Producto producto;

	@ManyToOne(cascade = CascadeType.REFRESH, optional = false)
	private TipoPrecio tipoPrecio;

	@Transient
	private TarifaIva tarifaIva;

	@Transient
	private TarifaIce tarifaIce;

	public PrecioProducto() {

	}

	public PrecioProducto(Double porcentajeUtilidad, Double precioBruto, Double utilidad, Producto producto,
			TipoPrecio tipoPrecio, TarifaIva tarifaIva, TarifaIce tarifaIce) {
		super();
		this.porcentajeUtilidad = porcentajeUtilidad;
		this.precioBruto = precioBruto;
		this.utilidad = utilidad;
		this.producto = producto;
		this.tipoPrecio = tipoPrecio;
		this.tarifaIva = tarifaIva;
		this.tarifaIce = tarifaIce;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getPorcentajeUtilidad() {
		return porcentajeUtilidad;
	}

	public void setPorcentajeUtilidad(Double porcentajeUtilidad) {
		this.porcentajeUtilidad = porcentajeUtilidad;
	}

	public Double getPrecioBruto() {
		return precioBruto;
	}

	public void setPrecioBruto(Double precioBruto) {
		this.precioBruto = precioBruto;
	}

	public Double getUtilidad() {
		return utilidad;
	}

	public void setUtilidad(Double utilidad) {
		this.utilidad = utilidad;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public TipoPrecio getTipoPrecio() {
		return tipoPrecio;
	}

	public void setTipoPrecio(TipoPrecio tipoPrecio) {
		this.tipoPrecio = tipoPrecio;
	}

	public TarifaIva getTarifaIva() {
		return tarifaIva;
	}

	public void setTarifaIva(TarifaIva tarifaIva) {
		this.tarifaIva = tarifaIva;
	}

	public TarifaIce getTarifaIce() {
		return tarifaIce;
	}

	public void setTarifaIce(TarifaIce tarifaIce) {
		this.tarifaIce = tarifaIce;
	}

}
