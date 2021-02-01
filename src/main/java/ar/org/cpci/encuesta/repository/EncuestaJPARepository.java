package ar.org.cpci.encuesta.repository;

import ar.org.cpci.encuesta.Encuesta;
import spark.Route;

import javax.persistence.EntityManager;

public class EncuestaJPARepository implements  EncuestaRepository {

    private EntityManager entityManager;

    public EncuestaJPARepository(EntityManager em){
        //Levantar EntityManagerFactory
        this.entityManager = em;
    }




    @Override
    public Encuesta findEncuestaByName(String encuestaName) throws EncuestaFindException {

        return this.entityManager.find(Encuesta.class,encuestaName);
    }

    @Override
    public void begin() {

        this.entityManager.getTransaction().begin();
    }

    @Override
    public void end() {
        this.entityManager.close();
    }


}
