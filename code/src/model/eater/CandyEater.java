package model.eater;

import model.collider.CandyCollider;
import model.entity.BaseEntity;

import java.util.List;

/**
 * Classe permettant de savoir si un bonbon peut etre mange
 * La classe notifie tous les observateurs si c'est le cas
 */
public class CandyEater extends BaseEater {
    /**
     * Cree une instance de CandyEater
     *
     * @param entities Liste des entites
     */
    public CandyEater(List<BaseEntity> entities) {
        super(entities, new CandyCollider());
    }
}
