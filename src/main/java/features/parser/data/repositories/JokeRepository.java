package features.parser.data.repositories;

import core.errors.exceptions.ServerException;
import core.errors.failures.Failure;
import core.errors.failures.MapperFailure;
import core.errors.failures.ServerFailure;
import core.errors.failures.SomethingWentWrongFailure;
import core.mappers.EntityMapper;
import core.types.either.Either;
import core.types.either.Left;
import core.types.either.Right;
import features.parser.data.datasources.JokeRemoteDatasource;
import features.parser.data.models.RequestModel;
import features.parser.domain.entities.RequestEntity;
import features.parser.domain.repositories.JokeRepositoryInterface;

public final class JokeRepository implements JokeRepositoryInterface {
    private final JokeRemoteDatasource remoteDatasource;
    private final EntityMapper<RequestEntity, RequestModel> requestMapper;

    public JokeRepository(JokeRemoteDatasource remoteDatasource, EntityMapper<RequestEntity, RequestModel> requestMapper) {
        this.remoteDatasource = remoteDatasource;
        this.requestMapper = requestMapper;
    }

    @Override
    public Either<Failure, RequestEntity> getSomeJokes() throws NullPointerException {
        try {
            final RequestModel request = remoteDatasource.getSomeJokes();
            return new Right<>(requestMapper.toEntity(request));
        } catch (Exception exception) {
            final Failure failure;
            if (exception instanceof ServerException) {
                failure = new ServerFailure();
            } else if (exception instanceof NullPointerException) {
                failure = new MapperFailure();
            } else {
                failure = new SomethingWentWrongFailure();
            }
            return new Left<>(failure);
        }
    }
}
