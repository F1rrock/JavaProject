package core.use_cases;

import core.errors.failures.Failure;
import core.types.either.Either;

public interface UseCase<Type, Param> {
    Either<Failure, Type> execute(Param param);
}
