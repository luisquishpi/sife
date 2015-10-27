package ec.sife.models.daos;
import ec.sife.models.entities.Persona;


public class PersonaDao extends GenericDao<Persona, Integer> {

    public PersonaDao() {
        super(Persona.class);
    }
 
}
