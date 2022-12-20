package core.types.either;

public final class Right<L, R> implements Either<L, R> {
    private final R value;

    public Right(R value) {
        this.value = value;
    }

    @Override
    public R get() {
        return value;
    }
}
