package model.eater;

import model.collider.GhostCollider;
import model.entity.BaseEntity;
import model.entity.ghost.Ghost;

import java.util.List;

/**
 * Classe permettant de savoir si un fantome peut être mangé
 * La classe notifie tous les observateurs si c'est le cas
 */
public class GhostEater extends BaseEater {
    /**
     * Créé une instance de GhostEater
     *
     * @param entities Liste des entités
     */
    public GhostEater(List<BaseEntity> entities) {
        super(entities, new GhostCollider());
    }
}
