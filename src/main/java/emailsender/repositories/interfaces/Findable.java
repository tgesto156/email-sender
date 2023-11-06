package emailsender.repositories.interfaces;

import java.util.List;
import java.util.Optional;

public interface Findable <T, ID>{

    Optional<T> findByID(ID id);

    List<T> findAll();
}
