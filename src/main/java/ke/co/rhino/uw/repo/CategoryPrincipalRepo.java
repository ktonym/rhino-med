package ke.co.rhino.uw.repo;

import ke.co.rhino.uw.entity.*;
import ke.co.rhino.uw.vo.Result;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by akipkoech on 25/04/2016.
 */
public interface CategoryPrincipalRepo extends JpaRepository<CategoryPrincipal,CategoryPrincipalId> {

    Page<CategoryPrincipal> findByCategory(Category category, Pageable pageable);
    long countByCategory(Category category);
    long countByPrincipal(Principal principal);
    List<CategoryPrincipal> findByPrincipal(Principal principal);
}
