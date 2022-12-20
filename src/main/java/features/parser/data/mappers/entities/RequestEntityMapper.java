package features.parser.data.mappers.entities;

import core.mappers.EntityMapper;
import core.observers.Observer;
import features.parser.data.models.RequestModel;
import features.parser.domain.entities.JokeEntity;
import features.parser.domain.entities.RequestEntity;
import features.parser.domain.mappers.entities.JokeEntityMapper;

import java.util.ArrayList;
import java.util.List;

public final class RequestEntityMapper implements EntityMapper<RequestEntity, RequestModel> {
    private final Observer<JokeEntityMapper, JokeEntity> observer;

    public RequestEntityMapper(Observer<JokeEntityMapper, JokeEntity> observer) {
        this.observer = observer;
    }

    private List<JokeEntity> convertToList(List<?> models) throws NullPointerException {
        final var entities = new ArrayList<JokeEntity>();
        for (var model : models) {
            JokeEntityMapper jokeMapper = observer.getMapperOnTo(model);
            entities.add(jokeMapper.toEntity(model));
        }
        return entities;
    }

    private List<?> convertFromList(List<JokeEntity> entities) throws NullPointerException {
        final var models = new ArrayList<>();
        for (var entity : entities) {
            JokeEntityMapper jokeMapper = observer.getMapperOnFrom(entity);
            models.add(jokeMapper.fromEntity(entity));
        }
        return models;
    }

    @Override
    public RequestEntity toEntity(RequestModel model) {
        return new RequestEntity(
                model.error(),
                model.amount(),
                convertToList(model.jokes())
        );
    }

    @Override
    public RequestModel fromEntity(RequestEntity entity) {
        return new RequestModel(
                entity.error(),
                entity.amount(),
                convertFromList(entity.jokes())
        );
    }
}
