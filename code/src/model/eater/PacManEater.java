package model.eater;

import model.collider.PacManCollider;
import model.entity.BaseEntity;

import java.util.List;

/**
 * Classe permettant de savoir si PacMan peut être mangé
 * La classe notifie tous les observateurs si c'est le cas
 */
public class PacManEater extends BaseEater {
    /**
     * Créé une instance de PacManEater
     *
     * @param entities Liste des entités
     */
    public PacManEater(List<BaseEntity> entities) {
        this.entities = entities;
        collider = new PacManCollider();
    }
}
