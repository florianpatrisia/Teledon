package ro.mpp.teledon.validatori;

public interface IValidator<E>{
    void validate(E entity)
        throws ValidationException;

}
