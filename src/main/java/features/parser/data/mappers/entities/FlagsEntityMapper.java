package features.parser.data.mappers.entities;

import core.mappers.EntityMapper;
import features.parser.data.models.FlagsModel;
import features.parser.domain.entities.FlagsEntity;

public final class FlagsEntityMapper implements EntityMapper<FlagsEntity, FlagsModel> {
    @Override
    public FlagsEntity toEntity(FlagsModel model) {
        return new FlagsEntity(
                model.nsfw(),
                model.religious(),
                model.political(),
                model.racist(),
                model.sexist(),
                model.explicit()
        );
    }

    @Override
    public FlagsModel fromEntity(FlagsEntity entity) {
        return new FlagsModel(
                entity.nsfw(),
                entity.religious(),
                entity.political(),
                entity.racist(),
                entity.sexist(),
                entity.explicit()
        );
    }
}
