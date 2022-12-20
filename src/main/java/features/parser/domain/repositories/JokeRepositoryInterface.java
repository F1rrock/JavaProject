package features.parser.domain.repositories;

import core.errors.failures.Failure;
import core.types.either.Either;
import features.parser.domain.entities.RequestEntity;

public interface JokeRepositoryInterface {
    Either<Failure, RequestEntity> getSomeJokes();
}
