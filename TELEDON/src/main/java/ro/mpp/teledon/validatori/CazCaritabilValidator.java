package ro.mpp.teledon.validatori;

import ro.mpp.teledon.model.CazCaritabil;

public class CazCaritabilValidator implements IValidator<CazCaritabil> {

    @Override
    public void validate(CazCaritabil entity) throws ValidationException {
        String erori="";
        if (entity.getId()==null)
            erori+="Id-ul nu poate fi null!" + entity.getId();
        if (entity.getId()<0)
            erori+="Id-ul trebuie sa fie un numar intreg pozitiv!" + entity.getId();
        if(entity.getNume().isEmpty())
            erori+="Numele nu trebuie sa fie null!" +entity.getNume();
        if(entity.getDescriere().isEmpty())
            erori+="Descrierea nu trebuie sa fie null!" +entity.getDescriere();
        if(entity.getSumaStransa()==null)
            erori+="Suma stransa nu trebuie sa fie null!" +entity.getSumaStransa();
        if(entity.getSumaFinala()==null)
            erori+="Suma finala nu trebuie sa fie null!" +entity.getSumaFinala();
        if(entity.getSumaFinala()==0)
            erori+="Suma finala nu trebuie sa fie 0!" +entity.getSumaFinala();
        if (!erori.isEmpty())
            throw  new ValidationException(erori);
    }
}
