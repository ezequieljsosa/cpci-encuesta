package ar.org.cpci.encuesta.repository;

import ar.org.cpci.encuesta.Encuesta;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;

public class EncuestaRepository {

    private final File directory;

    public EncuestaRepository(File directory) {
        this.directory = directory;
    }

    public Encuesta findEncuestaByName(String encuestaName)
            throws FileNotFoundException, EncuestaFindException {
        File f  = new File(this.directory.getAbsoluteFile() + "/" + encuestaName + ".json");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(f, Encuesta.class);
         } catch (FileNotFoundException fileNotFoundException) {
        throw  fileNotFoundException;
        }     catch (IOException ioException) {
                throw new EncuestaFindException(ioException);
            }


    }

}
