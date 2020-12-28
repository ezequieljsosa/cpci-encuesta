package ar.org.cpci.encuesta;

import ar.org.cpci.encuesta.repository.EncuestaFindException;
import ar.org.cpci.encuesta.repository.EncuestaRepository;

import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;


public class Main {

    public static void main(String [] args)  {

        // Inicio del programa y carga de datos iniciales


        //TODO validar precencia de parametros y existencia de los archivos
        String dirEncuestas = args[0];
        String nombreEncuesta = args[1];


        // Carga de el/los repositorios y consola

        EncuestaRepository repo = new EncuestaRepository(new File(dirEncuestas));
        Scanner console = new Scanner(System.in);



        Encuesta encuesta = null;
        try {
            encuesta = repo.findEncuestaByName(nombreEncuesta);
        } catch (FileNotFoundException fileNotFoundException) {
            System.err.println( fileNotFoundException.getLocalizedMessage());
            System.exit(1);
        } catch (EncuestaFindException encuestaFindException) {
            encuestaFindException.printStackTrace();
            System.err.println( "error reading json");
            System.exit(1);
        }
        //


        List<Integer> respuestas = new ArrayList<Integer>();

        Iterator<Pregunta> iterator = encuesta.getPreguntas().iterator();

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

        System.out.println(  encuesta.getNombre() );
        respuestas.stream().forEach(x -> {
            System.out.println(  x );
        });




        System.err.println("programa terminado");
    }

}
