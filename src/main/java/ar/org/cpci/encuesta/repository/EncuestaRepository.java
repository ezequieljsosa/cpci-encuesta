package ar.org.cpci.encuesta.repository;

import ar.org.cpci.encuesta.Encuesta;

import java.io.FileNotFoundException;
import java.util.Collection;

public interface EncuestaRepository {

    Encuesta findEncuestaByName(String encuestaName)
            throws  EncuestaFindException;

    void begin();
    void end();

    Collection<Encuesta> all();
}
