package model.utils;

import model.entity.BaseEntity;

public interface EntityObserver {
    void update(BaseEntity e);
}
