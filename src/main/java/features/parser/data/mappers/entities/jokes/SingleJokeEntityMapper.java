package features.parser.data.mappers.entities.jokes;

import core.mappers.EntityMapper;
import core.observers.Observable;
import features.parser.data.models.FlagsModel;
import features.parser.data.models.SingleJokeModel;
import features.parser.domain.entities.FlagsEntity;
import features.parser.domain.entities.JokeEntity;
import features.parser.domain.mappers.entities.JokeEntityMapper;

public final class SingleJokeEntityMapper implements JokeEntityMapper, Observable<JokeEntityMapper, JokeEntity> {
    private final EntityMapper<FlagsEntity, FlagsModel> flagsMapper;

    public SingleJokeEntityMapper(EntityMapper<FlagsEntity, FlagsModel> flagsMapper) {
        this.flagsMapper = flagsMapper;
    }

    @Override
    public JokeEntity toEntity(Object model) {
        var jokeModel = (SingleJokeModel) model;
        return new JokeEntity(
                jokeModel.category(),
                jokeModel.type(),
                jokeModel.joke(),
                flagsMapper.toEntity(
                        jokeModel.flags()
                ),
                jokeModel.id(),
                jokeModel.safe(),
                jokeModel.lang()
        );
    }

    @Override
    public SingleJokeModel fromEntity(JokeEntity entity) {
        return new SingleJokeModel(
                entity.category(),
                entity.type(),
                entity.text(),
                flagsMapper.fromEntity(
                        entity.flags()
                ),
                entity.id(),
                entity.safe(),
                entity.lang()
        );
    }

    @Override
    public JokeEntityMapper accessOnFrom(JokeEntity entity) {
        if (entity.type().equals("single")) {
            return this;
        } else {
            return null;
        }
    }

    @Override
    public JokeEntityMapper accessOnTo(Object model) {
        if (model instanceof SingleJokeModel) {
            return this;
        } else {
            return null;
        }
    }
}
