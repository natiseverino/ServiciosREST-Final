package carpinteroseverino.repositories;

import carpinteroseverino.model.CowBCS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BCSRepository extends JpaRepository<CowBCS,Integer> {
}
