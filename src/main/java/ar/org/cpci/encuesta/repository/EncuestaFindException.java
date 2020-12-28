package ar.org.cpci.encuesta.repository;

import java.io.IOException;

public class EncuestaFindException extends Exception {
    public EncuestaFindException(IOException ioException) {
        super(ioException);
    }
}
