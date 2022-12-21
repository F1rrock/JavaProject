package features.parser.domain.mappers.entities;

import core.mappers.EntityMapper;
import features.parser.domain.entities.JokeEntity;

public interface JokeEntityMapperInterface extends EntityMapper<JokeEntity, Object> {
    @Override
    JokeEntity toEntity(Object model);
    @Override
    Object fromEntity(JokeEntity entity);
}
