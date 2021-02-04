package ar.org.cpci.encuesta.repository;

import ar.org.cpci.encuesta.Encuesta;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Collection;

public class EncuestaJDBCRepository implements EncuestaRepository {

    private Connection dbConnection;

    public EncuestaJDBCRepository(String jdbcConnection) throws SQLException {
        final String DB_CONNECTION = jdbcConnection;
        this.dbConnection = DriverManager.getConnection(DB_CONNECTION);

    }

    @Override
    public Encuesta findEncuestaByName(String encuestaName) throws EncuestaFindException {
        Statement statement = null;
        try {
            statement = dbConnection.createStatement();

            ResultSet rs = statement.executeQuery("SELECT nombre from encuestas WHERE nombre = '" +  encuestaName + "'");
            rs.next();
            String nombre = rs.getString("nombre");
            Encuesta encuesta = new Encuesta();
            encuesta.setNombre(nombre);
            return encuesta;
        } catch (SQLException throwables) {
            throw  new EncuestaFindException(throwables);
        }

    }

    @Override
    public void begin() {

    }

    @Override
    public void end() {

    }

    @Override
    public Collection<Encuesta> all() {
        throw  new NotImplementedException();
    }
    @Override
    public void save(Encuesta encuesta) {
        throw  new NotImplementedException();
    }
}
