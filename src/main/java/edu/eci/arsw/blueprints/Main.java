package edu.eci.arsw.blueprints;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Juan Pablo Daza Pereira
 */
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("edu.eci.arsw.blueprints");
        //Servicio de blueprints
        BlueprintsServices blueprintsServices = context.getBean(BlueprintsServices.class);
        try {
            //1. Vamos a registrar algunos planos
            System.out.println("********** Registrando los planos **********");
            //Plano 1
            Point[] housePoints = new Point[]{
                    new Point(0,0),
                    new Point(15,0),
                    new Point(15,15),
                    new Point(0,15),
                    new Point(0,0)
            };
            Blueprint house = new Blueprint("Ing. Daza","Mi casa",housePoints);
            blueprintsServices.addNewBlueprint(house);
            System.out.println("Plano registrado exitosamente: " + house);

            //Plano 2:
            Point[] ciclePoints = new Point[]{
                    new Point(5,0),
                    new Point(10,5),
                    new Point(5,10),
                    new Point(0,5),
                    new Point(5,0)
            };
            Blueprint circleHouse = new Blueprint("Ing. Bernak","El estadio",ciclePoints);
            blueprintsServices.addNewBlueprint(circleHouse);
            System.out.println("Plano registrado exitosamente: " + circleHouse);

            //Plano 3:
            Point[] trianglePoints = new Point[]{
                    new Point(0,0),
                    new Point(10,0),
                    new Point(5,10),
                    new Point(0,0)
            };
            Blueprint piramideHouse = new Blueprint("Asociacion egipcia","Piramide",trianglePoints);
            blueprintsServices.addNewBlueprint(piramideHouse);
            System.out.println("Plano registrado exitosamente: " + piramideHouse);

            //2. Consultar los planos
            System.out.println("\n********** Planos registrados **********");
            blueprintsServices.getAllBlueprints().forEach(bp -> System.out.println(bp.getAuthor() + " - " +
                    bp.getName()));

            //3. Consultar los planos por au autor
            System.out.println("\n********** Planos del Ing. Daza **********");
            blueprintsServices.getBlueprintsByAuthor("Ing. Daza").forEach(bp -> System.out.println(bp.getName() +
                    " -Puntos: " + bp.getPoints().size()));

            //4. Consultar un plano en específico
            System.out.println("\n********** Consultando el plano específico **********");
            Blueprint specificBlueprint = blueprintsServices.getBlueprint("Ing. Daza","Mi casa");
            System.out.println("Plano consultado exitosamente: " + specificBlueprint);
            System.out.println("Puntos del plano:");
            specificBlueprint.getPoints().forEach(point -> System.out.println("x: " + point.getX() +
                    ", y: " + point.getY()));

            //5. Prueba para recuperar un plano que no existe
            System.out.println("\n********** Prueba para recuperar un plano que no existe **********");
            blueprintsServices.getBlueprint("nobody", "nothing");
        }catch (Exception e){
            System.out.println("Error: "  + e.getMessage());
        }
    }
}
