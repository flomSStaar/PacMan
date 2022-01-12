package model.utils;

import model.entity.BaseEntity;

public interface DisplacerObserver {
    void onMove(BaseEntity entity, Direction direction);
}
