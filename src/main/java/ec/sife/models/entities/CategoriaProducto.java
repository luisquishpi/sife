
package ec.sife.models.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "categoriaproducto")
public class CategoriaProducto {

    public CategoriaProducto() {
        super();
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "dependencia", nullable = false)
    private CategoriaProducto dependencia;

    @Column(name = "contieneproductos", nullable = false)
    private Boolean contieneProductos = true;

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

    public CategoriaProducto getDependencia() {
        return dependencia;
    }

    public void setDependencia(CategoriaProducto dependencia) {
        this.dependencia = dependencia;
    }

    public String toString() {
        return "CategoriaProducto [id=" + id + ", nombre=" + nombre + ", dependencia=" + dependencia
                + "escategoria=" + contieneProductos + "]";
    }

}
