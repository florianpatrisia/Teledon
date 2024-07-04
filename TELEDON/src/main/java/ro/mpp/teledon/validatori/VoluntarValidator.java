package ro.mpp.teledon.validatori;

import ro.mpp.teledon.model.Voluntar;

public class VoluntarValidator implements IValidator<Voluntar> {

    @Override
    public void validate(Voluntar entity) throws ValidationException {
        String erori="";
        if(entity.getId()==null)
            erori+="Id-ul nu poate fi null!"+entity.getId();
        if(entity.getId()<0)
            erori+="Id-ul trebuie sa fie o valoare intreaga pozitiva!"+entity.getId();
        if(entity.getNume().isEmpty())
            erori+="Numele nu trebuie sa fie null!"+entity. getNume();
        for(int i=0;i<entity.getNume().length();i++)
            if (Character.isDigit(entity.getNume().charAt(i)))
            {
                erori+="Numele trebuie sa contina doar litere!" +entity.getNume();
            }
        if(entity.getUsername().isEmpty())
            erori+="Username-ul nu trebuie sa fie null!"+entity.getUsername();
        if(entity.getParola().isEmpty())
            erori+="Parola nu trebuie sa fie null!"+ entity.getParola();
        if(!erori.isEmpty())
            throw new ValidationException(erori);


//        StringBuffer erori=new StringBuffer();
//        if(entity.getId()==null)
//            erori.append("Id-ul nu poate fi null!"+entity.getId());
//        if(entity.getId()<0)
//            erori.append("Id-ul trebuie sa fie o valoare intreaga pozitiva!"+entity.getId());
//        if(entity.getNume().isEmpty())
//            erori.append("Numele nu trebuie sa fie null!"+entity.getNume());
//        for(int i=0;i<entity.getNume().length();i++)
//            if (Character.isDigit(entity.getNume().charAt(i)))
//            {
//                erori.append("Numele trebuie sa contina doar litere!"+entity.getNume());
//            }
//        if(entity.getUsername().isEmpty())
//            erori.append("Username-ul nu trebuie sa fie null!"+ entity.getUsername());
//        if(entity.getParola().isEmpty())
//            erori.append("Parola nu trebuie sa fie null!"+entity.getParola());
//        if(erori.length()>0)
//            throw new ValidationException(erori.toString());
    }
}
