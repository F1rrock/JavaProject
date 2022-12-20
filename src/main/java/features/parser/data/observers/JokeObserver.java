package features.parser.data.observers;

import core.observers.Observable;
import core.observers.Observer;

import java.util.ArrayList;
import java.util.List;

public final class JokeObserver<Mapper, To> implements Observer<Mapper, To> {
    private final List<Observable<Mapper, To>> observables;

    public JokeObserver() {
        observables = new ArrayList<>();
    }

    @Override
    public void add(Observable<Mapper, To> observable) {
        observables.add(observable);
    }

    @Override
    public Mapper getMapperOnFrom(To to) {
        for (var observable : observables) {
            final Mapper mapper = observable.accessOnFrom(to);
            if (mapper != null) {
                return mapper;
            }
        }
        return null;
    }

    @Override
    public Mapper getMapperOnTo(Object model) {
        for (var observable : observables) {
            final Mapper mapper = observable.accessOnTo(model);
            if (mapper != null) {
                return mapper;
            }
        }
        return null;
    }
}
