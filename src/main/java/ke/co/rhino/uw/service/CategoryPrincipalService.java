package ke.co.rhino.uw.service;

import ke.co.rhino.uw.entity.Category;
import ke.co.rhino.uw.entity.CategoryPrincipal;
import ke.co.rhino.uw.entity.CategoryPrincipalId;
import ke.co.rhino.uw.entity.Principal;
import ke.co.rhino.uw.repo.CategoryPrincipalRepo;
import ke.co.rhino.uw.repo.CategoryRepo;
import ke.co.rhino.uw.repo.PrincipalRepo;
import ke.co.rhino.uw.vo.Result;
import ke.co.rhino.uw.vo.ResultFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by akipkoech on 24/05/2016.
 */
@Service("categoryPrincipalService")
@Transactional
public class CategoryPrincipalService implements ICategoryPrincipalService {

    @Autowired
    private CategoryPrincipalRepo categoryPrincipalRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private PrincipalRepo principalRepo;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Result<CategoryPrincipal> create(Long idCategory,
                                            Long idPrincipal,
                                            LocalDate wef, String actionUsername) {

        if(idCategory==null || idCategory <1 || idPrincipal==null || idPrincipal<1){
            return ResultFactory.getFailResult("A valid category and principal are required to carry out this step.");
        }

        if(wef==null){
            return ResultFactory.getFailResult("Effective date is missing.");
        }

        Category category = categoryRepo.findOne(idCategory);

        if(category==null){
            return ResultFactory.getFailResult("No category with ID ["+idCategory+"] was found.");
        }

        Principal principal = principalRepo.findOne(idPrincipal);

        if (principal==null){
            return ResultFactory.getFailResult("No principal with ID ["+idPrincipal+"] was found.");
        }

        CategoryPrincipalId catPrinId = new CategoryPrincipalId(category,principal);

        CategoryPrincipal testRec = categoryPrincipalRepo.findOne(catPrinId);

        if(testRec!=null){
            return ResultFactory.getFailResult("The principal already belongs to category "+category.getCat());
        }

        // Because only one can be true at a time..

        List<CategoryPrincipal> categoryPrincipals = categoryPrincipalRepo.findByPrincipal(principal);

//        categoryPrincipals.parallelStream()
//                .filter(cP -> cP.isActive()).collect(Collectors.toList())
//                .forEach(cP1 -> {
//                        cP1.setActive(false);
//                        categoryPrincipalRepo.save(cP1);
//                    });

        // The above java 8 looks awesome, but does it really work? If it does, then the rest of the lines below are useless

        for (CategoryPrincipal catPrincipal: categoryPrincipals){
            if (catPrincipal.isActive()){
                catPrincipal.setActive(false);
//                categoryPrincipalRepo.save(catPrincipal); //TODO consider activating this, and c waw hapens
            }
        }

        categoryPrincipalRepo.save(categoryPrincipals);

        CategoryPrincipal categoryPrincipal = new CategoryPrincipal.CategoryPrincipalBuilder(category,principal)
                .active(true).wef(wef).build();

        categoryPrincipalRepo.save(categoryPrincipal);

        return ResultFactory.getSuccessResult(categoryPrincipal);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Result<List<CategoryPrincipal>> create(List<Map<String, Object>> listMap, String actionUsername) {

        List<CategoryPrincipal> categoryPrincipalList = new ArrayList<>();

        StringBuilder errs = new StringBuilder();

        for(Map map : listMap){
            Long idCategory = (Long) map.get("idCategory");
            Long idPrincipal = (Long) map.get("idPrincipal");
            LocalDate wef = (LocalDate) map.get("wef");

            if(idCategory==null||idCategory<1||idPrincipal==null||idPrincipal<1){
                errs.append("A valid category ID and principal ID are required to carry out this step.");
                break;
            }

            if(wef == null){
                errs.append("Effective date is missing.");
                break;
            }

            Principal principal = principalRepo.getOne(idPrincipal);

            if(principal==null){
                errs.append("No principal with ID ["+idPrincipal+"] was found.");
                break;
            }

            Category category = categoryRepo.findOne(idCategory);

            if(category==null){
                errs.append("No category with ID ["+idCategory+"] was found.");
                break;
            }

            CategoryPrincipalId catPrinId = new CategoryPrincipalId(category,principal);

            if(categoryPrincipalRepo.findOne(catPrinId)!=null){
                errs.append("Principal already belongs to category " + category.getCat());
                break;
            }

            // Because only one can be active at a time..
            List<CategoryPrincipal> testCatPrincipals = categoryPrincipalRepo.findByPrincipal(principal);

//        categoryPrincipals.parallelStream()
//                .filter(cP -> cP.isActive()).collect(Collectors.toList())
//                .forEach(cP1 -> {
//                        cP1.setActive(false);
//                        categoryPrincipalRepo.save(cP1);
//                    });

            // The above java 8 looks awesome, but does it really work? If it does, then the rest of the lines below are useless

            for (CategoryPrincipal catPrincipal: testCatPrincipals){
                if (catPrincipal.isActive()){
                    catPrincipal.setActive(false);
//                categoryPrincipalRepo.save(catPrincipal); //TODO consider activating this, and c waw happens
                    categoryPrincipalList.add(catPrincipal);
                }
            }

            CategoryPrincipal categoryPrincipal = new CategoryPrincipal.CategoryPrincipalBuilder(category,principal)
                    .active(true).wef(wef).build();

            categoryPrincipalList.add(categoryPrincipal);

        }

        categoryPrincipalRepo.save(categoryPrincipalList);
        return ResultFactory.getSuccessResult(categoryPrincipalList,errs.toString());
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Result<CategoryPrincipal> update(Long idCategory,
                                            Long idPrincipal,
                                            LocalDate wef,
                                            Boolean active,
                                            String actionUsername) {

        if(idCategory==null||idCategory<1||idPrincipal==null||idPrincipal<1){
            return ResultFactory.getFailResult("A valid category and principal are required to carry out this step.");
        }

        if(wef==null&&active==null){
            return ResultFactory.getFailResult("A valid effective date or active status need to be provided.");
        }

        Principal principal=principalRepo.findOne(idPrincipal);

        if(principal==null){
            return ResultFactory.getFailResult("No principal with ID ["+idPrincipal+"] was found.");
        }

        Category category = categoryRepo.getOne(idCategory);

        if(category==null){
            return ResultFactory.getFailResult("No category with ID ["+idCategory+"] was found.");
        }

        CategoryPrincipalId catPrinId=new CategoryPrincipalId(category,principal);

        CategoryPrincipal categoryPrincipal = categoryPrincipalRepo.findOne(catPrinId);

        if(categoryPrincipal==null){
            return ResultFactory.getFailResult("The principal does not belong to the specified category. Update failed.");
        }

        CategoryPrincipal.CategoryPrincipalBuilder builder = new CategoryPrincipal.CategoryPrincipalBuilder(category,principal);

        if(wef!=null){
            builder.wef(wef);
        }

        if(active!=null){
            builder.active(active);
        }

        categoryPrincipalRepo.save(builder.build());

        return ResultFactory.getSuccessResult(categoryPrincipal);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Result<CategoryPrincipal> remove(Long idCategory,
                                            Long idPrincipal,
                                            String actionUsername) {

        if(idCategory==null||idCategory<1||idPrincipal==null||idPrincipal<1){
            return ResultFactory.getFailResult("A valid category and principal are required to carry out this step.");
        }

        Category cat = categoryRepo.findOne(idCategory);

        if(cat == null){
            return ResultFactory.getFailResult("No category with ID ["+idCategory+"] was found. Delete failed.");
        }

        Principal prin = principalRepo.findOne(idPrincipal);

        if(prin == null){
            return ResultFactory.getFailResult("No principal with ID ["+idPrincipal+"] was found. Delete failed.");
        }

        CategoryPrincipalId catPrinId = new CategoryPrincipalId(cat,prin);

        CategoryPrincipal categoryPrincipal = categoryPrincipalRepo.findOne(catPrinId);

        if(categoryPrincipal==null){
            return ResultFactory.getFailResult("The principal does not belong to the category " + cat.getCat());
        }

        categoryPrincipalRepo.delete(catPrinId);

        String msg = prin.toString() + "was removed from category " + cat.getCat() + "by " + actionUsername;

        logger.info(msg);

        return ResultFactory.getSuccessResult(msg);
    }
}
