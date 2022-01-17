package model.eater;

import model.collider.CandyCollider;
import model.entity.BaseEntity;

import java.util.List;

/**
 * Classe permettant de savoir si un bonbon peut être mangé
 * La classe notifie tous les observateurs si c'est le cas
 */
public class CandyEater extends BaseEater {
    /**
     * Créé une instance de CandyEater
     *
     * @param entities Liste des entités
     */
    public CandyEater(List<BaseEntity> entities) {
        super(entities, new CandyCollider());
    }
}
