package features.parser.domain.use_cases;

import core.errors.failures.Failure;
import core.types.either.Either;
import core.use_cases.UseCase;
import features.parser.domain.repositories.JokeRepositoryInterface;
import core.params.JokeParam;

public final class AddCustomJoke implements UseCase<Void, JokeParam> {
    private final JokeRepositoryInterface repository;

    public AddCustomJoke(JokeRepositoryInterface repository) {
        this.repository = repository;
    }

    @Override
    public Either<Failure, Void> execute(JokeParam param) {
        return repository.addJoke(param.joke());
    }
}
