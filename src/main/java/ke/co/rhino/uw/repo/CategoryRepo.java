package ke.co.rhino.uw.repo;

import ke.co.rhino.uw.entity.Category;
import ke.co.rhino.uw.entity.CorpAnniv;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by akipkoech on 12/8/14.
 */
public interface CategoryRepo extends JpaRepository<Category,Long> {
    //List<Category> findByAnniv_Corporate(Corporate corporate);
    List<Category> findByCorpAnniv(CorpAnniv corpAnniv);
    long countByCorpAnniv(CorpAnniv corpAnniv);
    Category findByCatAndCorpAnniv(char cat, CorpAnniv corpAnniv);
}
