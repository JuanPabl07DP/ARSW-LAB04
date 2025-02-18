package edu.eci.arsw.blueprints;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BlueprintFilter {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("edu.eci.arsw.blueprints");
        BlueprintsServices blueprintsServices = context.getBean(BlueprintsServices.class);

        try {
            // Crear un plano con puntos redundantes
            Point[] redundantPoints = new Point[]{
                    new Point(0,0),
                    new Point(0,0),  // redundante
                    new Point(1,1),
                    new Point(1,1),  // redundante
                    new Point(2,2),
                    new Point(2,2),  // redundante
                    new Point(3,3)
            };
            Blueprint redundantBp = new Blueprint("test", "redundant-design", redundantPoints);

            // Crear un plano sin redundancias
            Point[] normalPoints = new Point[]{
                    new Point(0,0),
                    new Point(1,1),
                    new Point(2,2),
                    new Point(3,3),
                    new Point(4,4)
            };
            Blueprint normalBp = new Blueprint("test", "normal-design", normalPoints);

            // Registrar los planos
            System.out.println("=== Registrando planos ===");
            blueprintsServices.addNewBlueprint(redundantBp);
            blueprintsServices.addNewBlueprint(normalBp);

            // Consultar y mostrar los planos filtrados
            System.out.println("\n=== Plano con puntos redundantes ===");
//            System.out.println("\n=== Plano con puntos submuestreo ===");
            Blueprint filteredRedundant = blueprintsServices.getBlueprint("test", "redundant-design");
            System.out.println("Puntos originales: " + redundantPoints.length);
            System.out.println("Puntos después del filtro: " + filteredRedundant.getPoints().size());
            System.out.println("Puntos filtrados:");
            filteredRedundant.getPoints().forEach(p ->
                    System.out.println("(" + p.getX() + "," + p.getY() + ")")
            );

            System.out.println("\n=== Plano normal ===");
            Blueprint filteredNormal = blueprintsServices.getBlueprint("test", "normal-design");
            System.out.println("Puntos originales: " + normalPoints.length);
            System.out.println("Puntos después del filtro: " + filteredNormal.getPoints().size());
            System.out.println("Puntos filtrados:");
            filteredNormal.getPoints().forEach(p ->
                    System.out.println("(" + p.getX() + "," + p.getY() + ")")
            );

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
