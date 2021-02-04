package ar.org.cpci.encuesta.repository;

import ar.org.cpci.encuesta.Encuesta;
import spark.Route;

import javax.persistence.EntityManager;
import java.util.Collection;

public class EncuestaJPARepository implements EncuestaRepository {

    private EntityManager entityManager;

    public EncuestaJPARepository(EntityManager em) {
        //Levantar EntityManagerFactory
        this.entityManager = em;
    }


    @Override
    public Encuesta findEncuestaByName(String encuestaName) throws EncuestaFindException {

        Encuesta encuesta = this.entityManager.find(Encuesta.class, encuestaName);
        if (encuesta == null) {
            throw new EncuestaFindException();
        }
        return encuesta;
    }

    @Override
    public void save(Encuesta encuesta) {
        this.entityManager.persist(encuesta);
    }

    @Override
    public void begin() {

        this.entityManager.getTransaction().begin();
    }

    @Override
    public void end() {
        this.entityManager.getTransaction().commit();
        this.entityManager.close();
    }

    @Override
    public Collection<Encuesta> all() {
        return this.entityManager.createQuery("Select a from Encuesta a", Encuesta.class)
                .getResultList();
    }


}
