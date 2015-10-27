package ec.sife.models.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ec.sife.utils.TipoPersona;

@Entity
@Table(name = "persona")

public class Persona {
	
	public Persona(TipoIdentificacionPersona tipoIdentificacionPersona, String identificacion,
			String razonsocial, String direccion, String telefono, String celular, String mail) {
		super();
	
		this.tipoIdentificacionPersona = tipoIdentificacionPersona;
		this.identificacion = identificacion;
		this.razonsocial = razonsocial;
		this.direccion = direccion;
		this.telefono = telefono;
		this.celular = celular;
		this.mail = mail;
		this.tipopersona = TipoPersona.CLIENTE;
	}

	public Persona(TipoIdentificacionPersona tipoIdentificacionPersona, String identificacion, String razonsocial, String direccion,
			String telefono, String celular, String mail, Integer idtipoprecio, Integer idtipocliente,
			Integer diasredito, Double porcentajedescuento, String descripcion, Integer tipopersona) {
		super();
		//this.tipoIdentificacionPersona = tipoIdentificacionPersona;
		this.identificacion = identificacion;
		this.razonsocial = razonsocial;
		this.direccion = direccion;
		this.telefono = telefono;
		this.celular = celular;
		this.mail = mail;
		this.idtipoprecio = idtipoprecio;
		this.idtipocliente = idtipocliente;
		this.diasredito = diasredito;
		this.porcentajedescuento = porcentajedescuento;
		this.descripcion = descripcion;
		this.tipopersona = TipoPersona.CLIENTE;
	}

	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    private Integer id;    
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne(cascade=CascadeType.REFRESH, optional=false)
	private TipoIdentificacionPersona tipoIdentificacionPersona;

	
	
	public TipoIdentificacionPersona getTipoIdentificaconPersona() {
		return this.tipoIdentificacionPersona;
	}

	public void setIdentificacionPersona(TipoIdentificacionPersona tipoIdentificacionPersona) {
		this.tipoIdentificacionPersona = tipoIdentificacionPersona;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getRazonsocial() {
		return razonsocial;
	}

	public void setRazonsocial(String razonsocial) {
		this.razonsocial = razonsocial;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Integer getIdtipoprecio() {
		return idtipoprecio;
	}

	public void setIdtipoprecio(Integer idtipoprecio) {
		this.idtipoprecio = idtipoprecio;
	}

	public Integer getIdtipocliente() {
		return idtipocliente;
	}

	public void setIdtipocliente(Integer idtipocliente) {
		this.idtipocliente = idtipocliente;
	}

	public Integer getDiasredito() {
		return diasredito;
	}

	public void setDiasredito(Integer diasredito) {
		this.diasredito = diasredito;
	}

	public Double getPorcentajedescuento() {
		return porcentajedescuento;
	}

	public void setPorcentajedescuento(Double porcentajedescuento) {
		this.porcentajedescuento = porcentajedescuento;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public TipoPersona getTipopersona() {
		return tipopersona;
	}

	public void setTipopersona(TipoPersona tipopersona) {
		this.tipopersona = tipopersona;
	}

	@Column(name = "identificacion", nullable = false)
    private String identificacion;
	
	@Column(name = "razonsocial", nullable = false)
    private String razonsocial;

	@Column(name = "direccion", nullable = false)
    private String direccion;
	
	@Column(name = "telefono", nullable = false)
    private String telefono;
	
	@Column(name = "celular", nullable = false)
    private String celular;
	
	@Column(name = "mail", nullable = false)
    private String mail;
	
	@Column(name = "idtipoprecio", nullable = true)
    private Integer idtipoprecio;
	
	@Column(name = "idtipocliente", nullable = true)
    private Integer idtipocliente;
	
	@Column(name = "diascredito", nullable = true)
    private Integer diasredito;
	
	@Column(name = "porcentajedescuento", nullable = true)
    private Double porcentajedescuento;
	
	@Column(name = "descripcion", nullable = true)
    private String descripcion;
	
	@Column(name = "tipopersona", nullable = false)
    private TipoPersona tipopersona;



	public Persona() {
    }

	
	 @Override
	    public String toString() {
	        return "Persona [id=" + id +",identificacion="+identificacion+", razonsocial="+razonsocial+", direccion="+direccion+", telefono="+telefono+", celular="+celular+",mail="+mail+", idtipoprecio="+idtipoprecio+", idtipocliente="+idtipocliente+", diascredito="+diasredito+", porcentajedescuento="+porcentajedescuento+", descripcion="+descripcion+", tipopersona="+tipopersona
	                + "]";
	    }

}
