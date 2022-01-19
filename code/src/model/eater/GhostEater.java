package model.eater;

import model.collider.GhostCollider;
import model.entity.BaseEntity;

import java.util.List;

/**
 * Classe permettant de savoir si un fantome peut etre mange
 * La classe notifie tous les observateurs si c'est le cas
 */
public class GhostEater extends BaseEater {
    /**
     * Cree une instance de GhostEater
     *
     * @param entities Liste des entites
     */
    public GhostEater(List<BaseEntity> entities) {
        super(entities, new GhostCollider());
    }
}
