import core.params.JokeParam;
import core.params.NoParams;
import features.parser.dependency_injection.DependencyInjectionContainer;
import features.parser.domain.entities.FlagsEntity;
import features.parser.domain.entities.JokeEntity;
import features.parser.presentation.events.AddCustomJokeEvent;
import features.parser.presentation.events.GetSomeJokesEvent;

public class Main {
    public static void main(String[] args) {
        DependencyInjectionContainer di =
                DependencyInjectionContainer.getInstance();
        di.init();

        var getSomeJokes = new GetSomeJokesEvent();
        getSomeJokes.execute(new NoParams());

        var jokeEntity = new JokeEntity(
            "Misc",
                "single",
                "A horse walks into a bar...",
                new FlagsEntity(
                        true,
                        false,
                        true,
                        false,
                        false,
                        false
                ),
                12,
                true,
                "en"
        );

        var addCustomJoke = new AddCustomJokeEvent();
        addCustomJoke.execute(
            new JokeParam(
                    jokeEntity
            )
        );
    }
}