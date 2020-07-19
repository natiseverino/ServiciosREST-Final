package carpinteroseverino.repositories;


import carpinteroseverino.model.Herd;
import carpinteroseverino.model.HerdAlert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface HerdAlertRepository extends JpaRepository<HerdAlert, Integer> {

    List<HerdAlert> findByHerd(Herd herd);
}
