package features.parser.domain.entities;

import java.util.List;

public record RequestEntity(
        boolean error,
        int amount,
        List<JokeEntity> jokes
) {
}
