package features.parser.data.models;

import java.util.List;

public record RequestModel(
        boolean error,
        int amount,
        List<?> jokes
) {
}
