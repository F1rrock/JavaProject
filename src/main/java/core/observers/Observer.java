package core.observers;

public interface Observer<ReturnParam, AccessParam> {
    void add(Observable<ReturnParam, AccessParam> observable);
    ReturnParam getMapperOnFrom(AccessParam param);
    ReturnParam getMapperOnTo(Object param);
}
