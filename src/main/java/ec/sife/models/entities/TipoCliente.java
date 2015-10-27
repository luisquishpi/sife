package ec.sife.models.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tipocliente")

public class TipoCliente implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;
    
    @Column(name = "nombre", nullable = false)
    private String nombre;

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

	
	public TipoCliente() {
    }

	public TipoCliente(String nombre) {
		this.id=null;
		this.nombre = nombre;
	
	}
	
	 @Override
	    public String toString() {
	        return "TipoCliente [id=" + id + ", nombre=" + nombre
	                + "]";
	    }  	
	
}
