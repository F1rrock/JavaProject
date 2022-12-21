package features.parser.data.mappers.entities;

import core.observers.Observer;
import features.parser.domain.entities.JokeEntity;
import features.parser.domain.mappers.entities.JokeEntityMapperInterface;

public class JokeEntityMapper implements JokeEntityMapperInterface {
    private final Observer<JokeEntityMapperInterface, JokeEntity> observer;

    public JokeEntityMapper(Observer<JokeEntityMapperInterface, JokeEntity> observer) {
        this.observer = observer;
    }

    @Override
    public JokeEntity toEntity(Object model) {
        JokeEntityMapperInterface jokeMapper = observer.getMapperOnTo(model);
        return jokeMapper.toEntity(model);
    }

    @Override
    public Object fromEntity(JokeEntity entity) {
        JokeEntityMapperInterface jokeMapper = observer.getMapperOnFrom(entity);
        return jokeMapper.fromEntity(entity);
    }
}
