package ar.org.cpci.encuesta;


import java.util.ArrayList;
import java.util.List;

public class Pregunta {

    private String texto;
    private List<String> opciones;

    public Pregunta() {
        this.opciones = new ArrayList<String>();
    }

    public List<String> getOpciones() {
        return opciones;
    }

    public void setOpciones(List<String> opciones) {
        this.opciones = opciones;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
