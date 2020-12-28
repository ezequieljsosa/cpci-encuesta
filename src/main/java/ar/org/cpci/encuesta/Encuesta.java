package ar.org.cpci.encuesta;

import java.util.List;

public class Encuesta {

    private String nombre;
    private List<Pregunta> preguntas;


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }
}
