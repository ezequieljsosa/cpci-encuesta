package ar.org.cpci.encuesta;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Pregunta {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String texto;
    @ElementCollection
    private List<String> opciones;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
