package ar.org.cpci.encuesta;

import ar.org.cpci.encuesta.repository.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;


public class MainConsole {

    public static void main(String [] args)  {

        // Inicio del programa y carga de datos iniciales


        //TODO validar precencia de parametros y existencia de los archivos
        String dirEncuestas = args[0];
        String nombreEncuesta = args[1];


        // Carga de el/los repositorios y consola
        EncuestaRepository repo = null;
        //---------------------------------------
//        repo = new EncuestaFileRepository(new File(dirEncuestas));
        //---------------------------------------
//        try {
//            repo = new EncuestaJDBCRepository("jdbc:postgresql://localhost/cpci?user=postgres&password=123");
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//            System.err.println("No se pudo establecer la conección a la DB");
//        }
        //---------------------------------------
        EntityManagerFactory emf = null; // persistance.xml
        EntityManager em = emf.createEntityManager();
        // Begin----
        em.getTransaction().begin();
        repo  = new EncuestaJPARepository(em);

        //---------------------------------------

        Scanner console = new Scanner(System.in);



        Encuesta encuesta = null;
        try {
            encuesta = repo.findEncuestaByName(nombreEncuesta);
        }  catch (EncuestaFindException encuestaFindException) {
            encuestaFindException.printStackTrace();
            System.err.println( "error reading json");
            System.exit(1);
        }
        // Commit y Close


        List<Integer> respuestas = new ArrayList<Integer>();

        Iterator<Pregunta> iterator = encuesta.getPreguntas().iterator();
        if (iterator.hasNext()){
        Pregunta pregunta = iterator.next();
        while (true) {
            System.err.println(pregunta.getTexto());

            List<String> opciones = pregunta.getOpciones();
            opciones.forEach(option -> {
                System.err.println(  (opciones.indexOf(option) + 1) + ". " + option);
            });
            String selected = console.nextLine();
            try {
                int selectedOption = Integer.parseInt(selected);
                if (selectedOption > 0 && selectedOption <= pregunta.getOpciones().size()){
                    respuestas.add(selectedOption);
                    if(iterator.hasNext()){
                        pregunta = iterator.next();
                    } else {
                        break;
                    }
                } else {
                    System.err.println(  "opcion invalida");
                }
            } catch (NumberFormatException e) {
                System.err.println(  "opcion invalida");

            }

        }
        } else {
            System.err.println("Encuesta Vacia!");
        }

        System.out.println(  encuesta.getNombre() );
        respuestas.stream().forEach(x -> {
            System.out.println(  x );
        });


        // entityManager.close()

        System.err.println("programa terminado");
    }

}
