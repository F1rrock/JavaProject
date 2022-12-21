package features.parser.dependency_injection;

import core.errors.exceptions.SomethingWentWrongException;
import core.mappers.EntityMapper;
import core.mappers.JsonMapper;
import core.network.InternetConnectionChecker;
import core.network.concretes.CheckInternetConnectionByGoogle;
import core.observers.Observable;
import core.observers.Observer;
import features.parser.data.datasources.JokeLocalDatasource;
import features.parser.data.datasources.JokeRemoteDatasource;
import features.parser.data.datasources.local_datasources.JokeLocalDatasourceFromPrefs;
import features.parser.data.datasources.remote_datasources.JokeRemoteDatasourceFromRestApi;
import features.parser.data.mappers.entities.FlagsEntityMapper;
import features.parser.data.mappers.entities.JokeEntityMapper;
import features.parser.data.mappers.entities.RequestEntityMapper;
import features.parser.data.mappers.entities.jokes.SingleJokeEntityMapper;
import features.parser.data.mappers.entities.jokes.TwoPartJokeEntityMapper;
import features.parser.data.mappers.json.FlagsJsonMapper;
import features.parser.data.mappers.json.JokeJsonMapper;
import features.parser.data.mappers.json.RequestJsonMapper;
import features.parser.data.mappers.json.jokes.SingleJokeJsonMapper;
import features.parser.data.mappers.json.jokes.TwoPartJokeJsonMapper;
import features.parser.data.models.FlagsModel;
import features.parser.data.models.RequestModel;
import features.parser.data.observers.JokeObserver;
import features.parser.data.repositories.JokeRepository;
import features.parser.domain.entities.FlagsEntity;
import features.parser.domain.entities.JokeEntity;
import features.parser.domain.entities.RequestEntity;
import features.parser.domain.mappers.entities.JokeEntityMapperInterface;
import features.parser.domain.mappers.json.JokeJsonMapperInterface;
import features.parser.domain.repositories.JokeRepositoryInterface;
import features.parser.domain.use_cases.GetSomeJokes;
import org.json.JSONObject;

import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

public final class DependencyInjectionContainer {
    private static DependencyInjectionContainer di;
    private final List<Object> instances = new ArrayList<>();

    private DependencyInjectionContainer() {
    }

    public static DependencyInjectionContainer getInstance() {
        if (di == null) {
            di = new DependencyInjectionContainer();
        }
        return di;
    }

    private <Type> void register(Type param) {
        instances.add(param);
    }

    public <Type> Type get(Class<Type> clazz) throws SomethingWentWrongException {
        for (var instance : instances) {
            if (clazz.isInstance(instance)) {
                return clazz.cast(instance);
            }
        }
        throw new SomethingWentWrongException();
    }

    private JokeEntityMapperInterface setUpJokeEntityMappers() {
        final EntityMapper<FlagsEntity, FlagsModel> flagsMapper =
                new FlagsEntityMapper();
        final Observable<JokeEntityMapperInterface, JokeEntity> singleJokeMapper =
                new SingleJokeEntityMapper(flagsMapper);
        final Observable<JokeEntityMapperInterface, JokeEntity> twoPartJokeMapper =
                new TwoPartJokeEntityMapper(flagsMapper);
        final Observer<JokeEntityMapperInterface, JokeEntity> jokeObserver =
                new JokeObserver<>();
        jokeObserver.add(singleJokeMapper);
        jokeObserver.add(twoPartJokeMapper);
        return new JokeEntityMapper(jokeObserver);
    }

    private JokeJsonMapperInterface setUpJokeJsonMappers() {
        JsonMapper<FlagsModel> flagsJsonMapper = new FlagsJsonMapper();
        Observable<JokeJsonMapperInterface, JSONObject> singleJokeJsonMapper =
                new SingleJokeJsonMapper(flagsJsonMapper);
        Observable<JokeJsonMapperInterface, JSONObject> twoPartJokeJsonMapper =
                new TwoPartJokeJsonMapper(flagsJsonMapper);
        Observer<JokeJsonMapperInterface, JSONObject> jokeJsonObserver =
                new JokeObserver<>();
        jokeJsonObserver.add(singleJokeJsonMapper);
        jokeJsonObserver.add(twoPartJokeJsonMapper);
        return new JokeJsonMapper(jokeJsonObserver);
    }

    public void init() {
        final JokeEntityMapperInterface jokeEntityMapper =
                setUpJokeEntityMappers();
        register(jokeEntityMapper);
        final EntityMapper<RequestEntity, RequestModel> requestEntityMapper =
                new RequestEntityMapper(jokeEntityMapper);
        final JokeJsonMapperInterface jokeJsonMapper =
                setUpJokeJsonMappers();
        register(jokeJsonMapper);
        final JsonMapper<RequestModel> requestJsonMapper =
                new RequestJsonMapper(jokeJsonMapper);
        register(requestEntityMapper);
        register(requestJsonMapper);
        final JokeRemoteDatasource remoteDatasource =
                new JokeRemoteDatasourceFromRestApi(
                        HttpClient.newHttpClient(),
                        requestJsonMapper
                );
        final JokeLocalDatasource localDatasource =
                new JokeLocalDatasourceFromPrefs(
                        Preferences.userRoot(),
                        requestJsonMapper
                );
        register(remoteDatasource);
        register(localDatasource);
        final InternetConnectionChecker internetChecker =
                new CheckInternetConnectionByGoogle();
        register(internetChecker);
        final JokeRepositoryInterface jokeRepository =
                new JokeRepository(
                        remoteDatasource,
                        localDatasource,
                        requestEntityMapper,
                        internetChecker
                );
        register(jokeRepository);
        final GetSomeJokes getSomeJokes = new GetSomeJokes(
                jokeRepository
        );
        register(getSomeJokes);
    }
}
