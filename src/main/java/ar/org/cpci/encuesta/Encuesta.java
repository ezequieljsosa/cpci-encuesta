package ar.org.cpci.encuesta;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Encuesta {

    @Id
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "encuesta_id")
    private List<Pregunta> preguntas;

    public Encuesta() {
        this.preguntas = new ArrayList<>();
    }

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
