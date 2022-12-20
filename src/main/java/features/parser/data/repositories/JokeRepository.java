package features.parser.data.repositories;

import core.errors.exceptions.CacheException;
import core.errors.exceptions.ServerException;
import core.errors.failures.*;
import core.mappers.EntityMapper;
import core.network.InternetConnectionChecker;
import core.types.either.Either;
import core.types.either.Left;
import core.types.either.Right;
import features.parser.data.datasources.JokeLocalDatasource;
import features.parser.data.datasources.JokeRemoteDatasource;
import features.parser.data.models.RequestModel;
import features.parser.domain.entities.RequestEntity;
import features.parser.domain.repositories.JokeRepositoryInterface;

public final class JokeRepository implements JokeRepositoryInterface {
    private final JokeRemoteDatasource remoteDatasource;
    private final JokeLocalDatasource localDatasource;
    private final EntityMapper<RequestEntity, RequestModel> requestMapper;
    private final InternetConnectionChecker internetChecker;

    public JokeRepository(JokeRemoteDatasource remoteDatasource, JokeLocalDatasource localDatasource, EntityMapper<RequestEntity, RequestModel> requestMapper, InternetConnectionChecker internetChecker) {
        this.remoteDatasource = remoteDatasource;
        this.localDatasource = localDatasource;
        this.requestMapper = requestMapper;
        this.internetChecker = internetChecker;
    }

    @Override
    public Either<Failure, RequestEntity> getSomeJokes() throws NullPointerException {
        if (internetChecker.hasConnection()) {
            try {
                final RequestModel request = remoteDatasource.getSomeJokes();
                localDatasource.safeJokes(request);
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
        } else {
            try {
                final RequestModel requestModel = localDatasource.getCachedJokes();
                return new Right<>(requestMapper.toEntity(requestModel));
            } catch (Exception exception) {
                final Failure failure;
                if (exception instanceof CacheException) {
                    failure = new CacheFailure();
                } else {
                    failure = new SomethingWentWrongFailure();
                }
                return new Left<>(failure);
            }
        }
    }
}
