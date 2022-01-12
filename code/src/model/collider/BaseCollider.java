package model.collider;

import model.entity.BaseEntity;

import java.util.List;

public interface BaseCollider {
    /**
     * Vérifie si l'entité passée en paramètre n'est pas en collision avec chacune des entités de la liste
     * sur la position x et y
     * @param entities Liste des entités
     * @param entity   Entité à vérifier
     * @param x        Position X
     * @param y        Position Y
     * @return Vrai si en collision sinon faux
     */
    boolean isCollide(List<BaseEntity> entities, BaseEntity entity, float x, float y);

    /**
     * Permet d'obtenir la liste des entités en collision avec l'entité recherchée
     * @param entities Liste des entités
     * @param entity   Entité à vérifier
     * @param x        Position X
     * @param y        Position Y
     * @return Liste des entités en collision
     */
    List<BaseEntity> getColliding(List<BaseEntity> entities, BaseEntity entity, float x, float y);
}
