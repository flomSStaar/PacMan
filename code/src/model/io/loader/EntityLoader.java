package model.io.loader;

import model.entity.BaseEntity;

import java.util.List;

public interface EntityLoader {
    /**
     * Charge les entités
     * @return Entités chargées
     * @throws Exception
     */
    List<BaseEntity> load() throws Exception;
}
