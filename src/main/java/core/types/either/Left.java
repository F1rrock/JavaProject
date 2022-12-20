package core.types.either;

public final class Left<L, R> implements Either<L, R> {
    private final L value;

    public Left(L value) {
        this.value = value;
    }

    @Override
    public L get() {
        return value;
    }
}
