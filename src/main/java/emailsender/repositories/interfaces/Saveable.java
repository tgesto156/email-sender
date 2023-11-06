package emailsender.repositories.interfaces;

public interface Saveable <T, ID>{

    T save(T object);
}
