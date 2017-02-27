package ke.co.rhino.uw.repo;

import ke.co.rhino.uw.entity.Category;
import ke.co.rhino.uw.entity.Member;
import ke.co.rhino.uw.entity.MemberCategory;
import ke.co.rhino.uw.entity.MemberCategoryId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by user on 12/02/2017.
 */
public interface MemberCategoryRepo extends PagingAndSortingRepository<MemberCategory,MemberCategoryId> {

    Page<MemberCategory> findByCategory(Category category, Pageable pageable);
    long countByCategory(Category category);
    long countByMember(Member member);
    List<MemberCategory> findByMember(Member member);

}
