package analyser.repository;

import analyser.entity.PageDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageRepository extends JpaRepository<PageDataEntity, String> {

    PageDataEntity findByUrl(String url);

}
