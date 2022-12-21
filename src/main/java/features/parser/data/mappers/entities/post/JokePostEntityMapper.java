package features.parser.data.mappers.entities.post;

import core.mappers.EntityMapper;
import features.parser.data.models.JokePostModel;
import features.parser.domain.entities.JokeEntity;
import features.parser.domain.mappers.entities.JokeEntityMapperInterface;

public final class JokePostEntityMapper implements EntityMapper<JokeEntity, JokePostModel> {
    private final JokeEntityMapperInterface jokeMapper;

    public JokePostEntityMapper(JokeEntityMapperInterface jokeMapper) {
        this.jokeMapper = jokeMapper;
    }

    @Override
    public JokeEntity toEntity(JokePostModel model) {
        return jokeMapper.toEntity(model.joke());
    }

    @Override
    public JokePostModel fromEntity(JokeEntity entity) {
        return new JokePostModel(
                3,
                jokeMapper.fromEntity(entity)
        );
    }
}
