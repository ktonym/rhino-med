package ke.co.rhino.uw.service;

import ke.co.rhino.uw.entity.Category;
import ke.co.rhino.uw.entity.CorpAnniv;
import ke.co.rhino.uw.vo.Result;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * Created by akipkoech on 4/7/15.
 */
public interface ICategoryService {

    @PreAuthorize("hasAnyRole('ROLE_UW_MANAGERS','ROLE_UNDERWRITERS')")
    public Result<Category> create(Long idCorpAnniv,
                                   String cat,
                                   String description);

    @PreAuthorize("hasAnyRole('ROLE_UW_MANAGERS','ROLE_UNDERWRITERS')")
    public Result<Category> update(Long idCategory,
                                   Long idCorpAnniv,
                                   String cat,
                                   String description);

    @PreAuthorize("hasRole('ROLE_UW_MANAGERS')")
    public Result<Category> remove(Long idCategory, String actionUsername);

    @PreAuthorize("isAuthenticated()")
    public Result<Category> find(Long idCategory);

    @PreAuthorize("isAuthenticated()")
    public Result<Page<Category>> findAll(int pageNum,int size,String actionUsername);

    @PreAuthorize("isAuthenticated()")
    public Result<List<Category>> findByAnniv(Long idCorpAnniv);


}
