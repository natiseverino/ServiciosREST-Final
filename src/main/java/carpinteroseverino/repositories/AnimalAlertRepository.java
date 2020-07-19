package carpinteroseverino.repositories;

import carpinteroseverino.model.AnimalAlert;
import carpinteroseverino.model.Cow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimalAlertRepository extends JpaRepository<AnimalAlert,Integer> {

    List<AnimalAlert> findByCow(Cow cow);
}
