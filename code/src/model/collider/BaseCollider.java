package model.collider;

import model.entity.BaseEntity;

import java.util.List;

public interface BaseCollider {
    /**
     * Verifie si l'entite passee en parametre n'est pas en collision avec chacune des entites de la liste
     * sur la position x et y
     *
     * @param entities Liste des entites
     * @param entity   Entite a verifier
     * @param x        Position X
     * @param y        Position Y
     * @return Vrai si en collision sinon faux
     */
    boolean isCollide(List<BaseEntity> entities, BaseEntity entity, float x, float y);

    /**
     * Permet d'obtenir la liste des entites en collision avec l'entite recherchee
     *
     * @param entities Liste des entites
     * @param entity   Entite a verifier
     * @param x        Position X
     * @param y        Position Y
     * @return Liste des entites en collision
     */
    List<BaseEntity> getColliding(List<BaseEntity> entities, BaseEntity entity, float x, float y);
}
