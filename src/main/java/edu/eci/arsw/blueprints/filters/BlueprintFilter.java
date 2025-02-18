package edu.eci.arsw.blueprints.filters;

import edu.eci.arsw.blueprints.model.Blueprint;

/**
 * @author Juan Pablo Daza Pereira
 */
public interface BlueprintFilter {
    Blueprint filter(Blueprint blueprint);
}
