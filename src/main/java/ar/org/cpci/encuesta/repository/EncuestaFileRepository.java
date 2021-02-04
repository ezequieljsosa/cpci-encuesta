package ar.org.cpci.encuesta.repository;

import ar.org.cpci.encuesta.Encuesta;
import com.fasterxml.jackson.databind.ObjectMapper;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.*;
import java.util.Collection;

public class EncuestaFileRepository implements EncuestaRepository {

    private final File directory;

    public EncuestaFileRepository(File directory) {
        this.directory = directory;
    }

    public Encuesta findEncuestaByName(String encuestaName)
            throws  EncuestaFindException {
        File f  = new File(this.directory.getAbsoluteFile() + "/" + encuestaName + ".json");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(f, Encuesta.class);
         } catch (FileNotFoundException fileNotFoundException) {
            throw new EncuestaFindException(fileNotFoundException);
        }     catch (IOException ioException) {
                throw new EncuestaFindException(ioException);
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
