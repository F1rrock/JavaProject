package features.parser.data.mappers.entities.jokes;

import core.mappers.EntityMapper;
import core.observers.Observable;
import features.parser.data.models.FlagsModel;
import features.parser.data.models.TwoPartJokeModel;
import features.parser.domain.entities.FlagsEntity;
import features.parser.domain.entities.JokeEntity;
import features.parser.domain.mappers.entities.JokeEntityMapper;

public final class TwoPartJokeEntityMapper implements JokeEntityMapper, Observable<JokeEntityMapper, JokeEntity> {
    private final EntityMapper<FlagsEntity, FlagsModel> flagsMapper;

    public TwoPartJokeEntityMapper(EntityMapper<FlagsEntity, FlagsModel> flagsMapper) {
        this.flagsMapper = flagsMapper;
    }

    @Override
    public TwoPartJokeModel fromEntity(JokeEntity entity) {
        final String[] jokeParts = entity.text().split("\t");
        return new TwoPartJokeModel(
                entity.category(),
                entity.type(),
                jokeParts[0],
                jokeParts[1],
                flagsMapper.fromEntity(entity.flags()),
                entity.id(),
                entity.safe(),
                entity.lang()
        );
    }

    @Override
    public JokeEntity toEntity(Object model) {
        var jokeModel = (TwoPartJokeModel) model;
        return new JokeEntity(
                jokeModel.category(),
                jokeModel.type(),
                jokeModel.setup() + "\t" + jokeModel.delivery(),
                flagsMapper.toEntity(jokeModel.flags()),
                jokeModel.id(),
                jokeModel.safe(),
                jokeModel.lang()
        );
    }

    @Override
    public JokeEntityMapper accessOnFrom(JokeEntity entity) {
        if (entity.type().equals("twopart")) {
            return this;
        } else {
            return null;
        }
    }

    @Override
    public JokeEntityMapper accessOnTo(Object model) {
        if (model instanceof TwoPartJokeModel) {
            return this;
        } else {
            return null;
        }
    }
}
