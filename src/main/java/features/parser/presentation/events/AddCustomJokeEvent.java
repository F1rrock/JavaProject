package features.parser.presentation.events;

import core.errors.exceptions.SomethingWentWrongException;
import core.errors.failures.Failure;
import core.events.Event;
import core.params.JokeParam;
import core.types.either.Either;
import features.parser.dependency_injection.DependencyInjectionContainer;
import features.parser.domain.use_cases.AddCustomJoke;

public class AddCustomJokeEvent implements Event<JokeParam> {
    @Override
    public void execute(JokeParam param) {
        DependencyInjectionContainer di =
                DependencyInjectionContainer.getInstance();
        final AddCustomJoke addCustomJoke;
        try {
            addCustomJoke = di.get(AddCustomJoke.class);
        } catch (SomethingWentWrongException e) {
            System.out.println("something went wrong");
            return;
        }
        final Either<Failure, Void> response = addCustomJoke.execute(param);
        if (response != null) {
            final Object folder = response.get();
            final String failureType = folder.getClass().getSimpleName();
            final String message = switch (failureType) {
                case "ServerFailure" -> "something with server :(";
                case "MapperFailure", "SomethingWentWrongFailure" -> "something went wrong :(";
                default -> "unhandled failure :((";
            };
            System.out.println(message);
        } else {
            System.out.println("joke successfully added");
        }
    }
}
