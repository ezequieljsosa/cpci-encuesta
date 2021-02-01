package ar.org.cpci.encuesta.repository;

import java.io.IOException;
import java.sql.SQLException;

public class EncuestaFindException extends Exception {
    public EncuestaFindException(IOException ioException) {
        super(ioException);
    }

    public EncuestaFindException(SQLException throwables) {
        super(throwables);
    }
}
