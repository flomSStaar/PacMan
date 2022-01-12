package model.eater;

import model.collider.CandyCollider;
import model.entity.BaseEntity;

import java.util.List;

/**
 * Classe permettant de savoir si un bonbon peut être mangé
 * La classe notifie tous les observateurs si c'est le cas
 */
public class CandyEater extends BaseEater {
    public CandyEater(List<BaseEntity> entities) {
        this.entities = entities;
        collider = new CandyCollider();
    }
}
