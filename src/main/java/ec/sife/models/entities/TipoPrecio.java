package ec.sife.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tipoprecio")
public class TipoPrecio implements java.io.Serializable {
   
	
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false)
    private String nombre;
    
    @Column(name = "utilidad", nullable = false)
    private Double utilidad;

    public Double getUtilidad() {
		return utilidad;
	}

	public void setUtilidad(Double utilidad) {
		this.utilidad = utilidad;
	}

	public TipoPrecio() {
    }

	public TipoPrecio(String nombre, Double utilidad) {
		this.nombre = nombre;
		this.utilidad = utilidad;
	}
   

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    @Override
    public String toString() {
        return "TipoPrecio [id=" + id + ", nombre=" + nombre + ", Utilidad="+utilidad
                + "]";
    }

}
