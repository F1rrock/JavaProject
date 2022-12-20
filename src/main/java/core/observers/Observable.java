package core.observers;

public interface Observable<ReturnParam, AccessParam> {
    ReturnParam accessOnFrom(AccessParam param);
    ReturnParam accessOnTo(Object param);
}
