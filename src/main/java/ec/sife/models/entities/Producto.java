package ec.sife.models.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import ec.sife.utils.UnidadeMedida;

@Entity
@Table(name = "producto")
public class Producto {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "nombre", nullable = false)
	private String nombre;

	@Column(name = "codigo", nullable = false)
	private String codigo;

	@Column(name = "peso", nullable = false)
	private Double peso = 0.0;

	@Column(name = "unidadMedida", nullable = false)
	private UnidadeMedida unidadMedida;

	@Column(name = "fechaActualizacion", nullable = false)
	private Date fechaActualizacion;

	@Column(name = "puedeFraccionar", nullable = false)
	private Boolean puedeFraccionar = false;

	@Column(name = "cantidad", nullable = false)
	private Double cantidad;

	@Column(name = "costo", nullable = false)
	private Double costo;

	@Column(name = "stockMinimo", nullable = false)
	private Double stockMinimo = 0.0;

	@ManyToOne(cascade = CascadeType.REFRESH, optional = false)
	private CategoriaProducto categoriaProducto;

	@ManyToOne(cascade = CascadeType.REFRESH, optional = false)
	private TarifaIva tarifaIva;

	@ManyToOne(cascade = CascadeType.REFRESH, optional = false)
	private TarifaIce tarifaIce;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public CategoriaProducto getCategoriaProducto() {
		return categoriaProducto;
	}

	public void setCategoriaProducto(CategoriaProducto categoriaProducto) {
		this.categoriaProducto = categoriaProducto;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	public UnidadeMedida getUnidadMedida() {
		return unidadMedida;
	}

	public void setUnidadMedida(UnidadeMedida unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public Boolean getPuedeFraccionar() {
		return puedeFraccionar;
	}

	public void setPuedeFraccionar(Boolean puedeFraccionar) {
		this.puedeFraccionar = puedeFraccionar;
	}

	public Double getCantidad() {
		return cantidad;
	}

	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}

	public Double getCosto() {
		return costo;
	}

	public void setCosto(Double costo) {
		this.costo = costo;
	}

	public Double getStockMinimo() {
		return stockMinimo;
	}

	public void setStockMinimo(Double stockMinimo) {
		this.stockMinimo = stockMinimo;
	}
}
