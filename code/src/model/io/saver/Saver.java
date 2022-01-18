package model.io.saver;

import java.util.List;

public interface Saver {
    /**
     * Définit la méthode pour sauvegarder un objet
     *
     * @param o Objet à sauvegarder
     */
    void save(Object o);

    /**
     * Définit la méthode pour sauvegarder une liste d'objets
     *
     * @param objectList Liste des objets à sauvegarder
     */
    void save(List<Object> objectList);
}
