package ro.mpp.teledon.validatori;

import ro.mpp.teledon.model.Donator;

public class DonatorValidator implements IValidator<Donator> {
    @Override
    public void validate(Donator entity) throws ValidationException {
        String erori="";
        if(entity.getId()==null)
            erori+="Id-ul nu poate fi null!" + entity.getId();
        if(entity.getId()<0)
            erori+="Id-ul trebuie sa fie o valoare intreaga pozitiva!" +entity.getId();
        if(entity.getNume().isEmpty())
            erori+="Numele nu trebuie sa fie null!" +entity.getNume();
        if(entity.getAdresa().isEmpty())
            erori+="Adresa nu trebuie sa fie null!" +entity.getAdresa();
        if(entity.getTelefon().isEmpty())
            erori+="Telefonul nu trebuie sa fie null!" +entity.getTelefon();
        if(entity.getTelefon().length()!=10)
            erori+="Telefonul trebuie sa aiba 10 cifre!" +entity.getTelefon();
        for(int i=0;i<entity.getTelefon().length();i++)
        {
            if (Character.isAlphabetic(entity.getTelefon().charAt(i)))
                erori+="Telefonul trebuie sa contina doar cifre!"+entity.getTelefon();
        }
        if(!erori.isEmpty())
            throw new ValidationException(erori);

    }
}
