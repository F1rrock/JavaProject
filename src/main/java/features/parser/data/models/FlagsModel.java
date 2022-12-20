package features.parser.data.models;

public record FlagsModel(
        boolean nsfw,
        boolean religious,
        boolean political,
        boolean racist,
        boolean sexist,
        boolean explicit
) {
}
