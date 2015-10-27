package ec.sife.models.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tarifaiva")

public class TarifaIva {
	


    public TarifaIva(String nombre, String codigo) {
		
    	this.id=null;
		this.nombre = nombre;
		this.codigo = codigo;
	}

	@Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;
    
	@Column(name = "nombre", nullable = false)
    private String nombre;
    
    @Column(name = "codigo", nullable = false)
    private String codigo;
    

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

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	 @Override
	    public String toString() {
	        return "TarifaIva [id=" + id + ", nombre=" + nombre + ", codigo="+codigo
	                + "]";
	    }

}
