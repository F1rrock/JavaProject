import core.errors.exceptions.SomethingWentWrongException;
import core.errors.failures.Failure;
import core.types.either.Either;
import features.parser.dependency_injection.DependencyInjectionContainer;
import features.parser.domain.entities.FlagsEntity;
import features.parser.domain.entities.RequestEntity;
import features.parser.domain.use_cases.GetSomeJokes;
import features.parser.domain.use_cases.params.NoParams;

public class Main {
    public static void main(String[] args) {
        DependencyInjectionContainer di =
                DependencyInjectionContainer.getInstance();
        di.init();
        final GetSomeJokes getSomeJokes;
        try {
            getSomeJokes = di.get(GetSomeJokes.class);
        } catch (SomethingWentWrongException e) {
            System.out.println("something went wrong");
            return;
        }
        final Either<Failure, RequestEntity> jokesOrFailure =
                getSomeJokes.execute(new NoParams());
        final Object folder = jokesOrFailure.get();
        if (folder instanceof Failure) {
            final String failureType = folder.getClass().getSimpleName();
            final String message = switch (failureType) {
                case "ServerFailure" -> "something with server :(";
                case "CacheFailure" -> "there's nothing, please check your internet connection";
                case "MapperFailure", "SomethingWentWrongFailure" -> "something went wrong :(";
                default -> "unhandled failure :((";
            };
            System.out.println(message);
        } else if (folder instanceof final RequestEntity request) {
            System.out.println("amount: " + request.amount());
            System.out.println("jokes:");
            for (var joke : request.jokes()) {
                System.out.println("category: " + joke.category());
                System.out.println("type: " + joke.type());
                System.out.println("joke: " + joke.text());

                final FlagsEntity flags = joke.flags();
                System.out.println("nsfw: " + flags.nsfw());
                System.out.println("religious: " + flags.religious());
                System.out.println("political: " + flags.political());
                System.out.println("racist: " + flags.racist());
                System.out.println("sexist: " + flags.sexist());
                System.out.println("explicit: " + flags.explicit());

                System.out.println("id: " + joke.id());
                System.out.println("is safe: " + joke.safe());
                System.out.println("language: " + joke.lang() + "\n");
            }
        } else {
            System.out.println("unhandled failure :((");
        }
    }
}