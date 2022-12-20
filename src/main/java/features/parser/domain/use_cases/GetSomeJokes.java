package features.parser.domain.use_cases;

import core.errors.failures.Failure;
import core.types.either.Either;
import core.use_cases.UseCase;
import features.parser.domain.entities.RequestEntity;
import features.parser.domain.repositories.JokeRepositoryInterface;
import features.parser.domain.use_cases.params.NoParams;

public final class GetSomeJokes implements UseCase<RequestEntity, NoParams> {
    private final JokeRepositoryInterface repository;

    public GetSomeJokes(JokeRepositoryInterface repository) {
        this.repository = repository;
    }

    @Override
    public Either<Failure, RequestEntity> execute(NoParams param) throws NullPointerException {
        return repository.getSomeJokes();
    }
}
