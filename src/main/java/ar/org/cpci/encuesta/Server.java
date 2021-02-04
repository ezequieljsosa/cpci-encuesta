package ar.org.cpci.encuesta;

import ar.org.cpci.encuesta.repository.EncuestaFindException;
import ar.org.cpci.encuesta.repository.EncuestaJPARepository;
import ar.org.cpci.encuesta.repository.EncuestaRepository;
import spark.ModelAndView;
import spark.Route;
import spark.TemplateViewRoute;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.*;

import static spark.Spark.*;
import static spark.Spark.get;
import static spark.debug.DebugScreen.enableDebugScreen;

public class Server {

    private static  EntityManagerFactory entityManagerFactory = null;

    public static void main(String[] args) {
        enableDebugScreen();
        port(4567);
        initPersistance();
        initRoutes();
    }

    public static void initPersistance(){
        entityManagerFactory = Persistence.createEntityManagerFactory("db");
        EncuestaRepository repo = createEncuestaRepo();
        repo.begin();
        testData().stream().forEach(e-> repo.save(e));
        repo.end();
    }

    public static List<Encuesta> testData(){
        ArrayList<Encuesta> encuestas = new ArrayList<>();
        Encuesta e = new Encuesta();
        e.setNombre("encuesta1");
        Pregunta p = new Pregunta();
        p.setTexto("cuanto es 2 + 2");
        p.getOpciones().add("4");
        p.getOpciones().add("14");
        e.getPreguntas().add(p);

        encuestas.add(e);
        e = new Encuesta();
        e.setNombre("encuesta2");
        encuestas.add(e);
        p = new Pregunta();
        p.setTexto("cuanto es 2 + 3");
        p.getOpciones().add("5");
        p.getOpciones().add("14");
        e.getPreguntas().add(p);
        return encuestas;
    }

    private static EncuestaRepository createEncuestaRepo() {
        return new EncuestaJPARepository(entityManagerFactory.createEntityManager());
    }

    public static void initRoutes() {

        String projectDir = System.getProperty("user.dir");
        String staticDir = "/src/main/resources/static/";
        staticFiles.externalLocation(projectDir + staticDir);

        ThymeleafTemplateEngine engine = new ThymeleafTemplateEngine();

        get("/encuesta", (request, response) -> {
            Map<String, Object> map = new HashMap<>();
            EncuestaRepository repo = createEncuestaRepo();
            Collection<Encuesta> encuestas = repo.all();
            map.put("encuestas",encuestas);
            return engine.render(new ModelAndView(map, "encuestas"));
        });

        get("/encuesta/:name", (request, response) -> {

            //INIT
            EncuestaRepository repo = createEncuestaRepo();
            repo.begin();
            try {
                //INPUT
                String name = request.params("name");
                //Domain
                Map<String, Object> map = new HashMap<>();

                Encuesta pepe = repo.findEncuestaByName(name);
                //OUTPUT
                map.put("encuesta", pepe);

                return engine.render(new ModelAndView(map, "encuesta"));
            } catch (EncuestaFindException e){
                response.status(404);
                return engine.render(new ModelAndView(new HashMap<>(), "page404"));
            }   finally {
                repo.end();
            }
        });

        post("/encuesta/:name/responder", (request, response) -> {
            HashMap<String,Object> data = new HashMap<>();
//            List<String> respuestas = new ArrayList<>();
            data.put("respuestas",request.body().split("&"));
            // cuanto+es+2+%2B+2_4=on&cuanto+es+2+%2B+3_5=on


            return engine.render(new ModelAndView(data, "contestarfinalizado"));
        });

        get("/encuesta/:name/responder", (request, response) -> {

            //INIT
            EncuestaRepository repo = createEncuestaRepo();
            repo.begin();
            try {
                //INPUT
                String name = request.params("name");
                //Domain
                Map<String, Object> map = new HashMap<>();
                try {
                    Encuesta pepe = repo.findEncuestaByName(name);
                    //OUTPUT

                    map.put("encuesta", pepe);

                } catch (EncuestaFindException ex) {
                    response.redirect("/encuesta");
                }
                return engine.render(new ModelAndView(map, "contestar"));
            } finally {
                repo.end();
            }
        });

//        get("/encuesta/:id", (request, response) -> {
//
//            //INIT
//            EncuestaRepository repo = createEncuestaRepo();
//            repo.begin();
//            try {
//                //INPUT
//                String name = request.params("name");
//                //Domain
//                Map<String, Object> map = new HashMap<>();
//                try {
//                    Encuesta encuesta = repo.findEncuestaByName(name);
//                    //OUTPUT
//
//                    map.put("encuesta", encuesta);
//
//                } catch (EncuestaFindException ex) {
//                    response.redirect("/encuesta");
//                }
//                return engine.render(new ModelAndView(map, "encuesta.html"));
//            } finally {
//                repo.end();
//            }
//        });

//        get("/encuesta/", ); // Listar
//        post("/encuesta",); // Crear
//        delete("/encuesta/:id", ); //Modificar
//        post("/encuesta/:id", ); // Actualizar

    }


}
