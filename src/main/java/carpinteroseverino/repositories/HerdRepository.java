package carpinteroseverino.repositories;

import carpinteroseverino.model.Herd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HerdRepository extends JpaRepository<Herd,Integer> {


}
