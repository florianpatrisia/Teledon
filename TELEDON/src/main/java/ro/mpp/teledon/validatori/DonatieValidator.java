package ro.mpp.teledon.validatori;

import ro.mpp.teledon.model.Donatie;

public class DonatieValidator implements IValidator<Donatie>{
    @Override
    public void validate(Donatie entity) throws ValidationException {
        String erori="";
        if(entity.getId()==null)
            erori+="Id-ul nu poate fi null!"+ entity.getId();
        if(entity.getId()<0)
            erori+="Id-ul trebuie sa fie o valoare intreaga pozitiva!" +entity.getId();
        if(entity.getDonator()==null)
            erori+="Donatorul nu poate fi null! "+ entity.getDonator();
        if(entity.getCazCaritabil()==null)
            erori+="Cazul Caritabil nu poate fi null! " + entity.getCazCaritabil();
        if(entity.getSumaDonata()<=0)
            erori+="Suma donata trebuie sa fie un numar intreg pozitiv! "+ entity.getSumaDonata();
        if (!erori.isEmpty())
            throw new ValidationException(erori);
            }
}
