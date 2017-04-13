package ke.co.rhino.uw.service;

import ke.co.rhino.base.service.AbstractService;
import ke.co.rhino.uw.entity.Category;
import ke.co.rhino.uw.entity.CorpAnniv;
import ke.co.rhino.uw.repo.CategoryPrincipalRepo;
import ke.co.rhino.uw.repo.CategoryRepo;
import ke.co.rhino.uw.repo.CorpAnnivRepo;
import ke.co.rhino.uw.repo.CorpBenefitRepo;
import ke.co.rhino.uw.vo.Result;
import ke.co.rhino.uw.vo.ResultFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by akipkoech on 4/7/15.
 */
@Service("categoryService")
@Transactional
public class CategoryService extends AbstractService implements ICategoryService {

    @Autowired
    protected CategoryRepo categoryRepo;
    @Autowired
    protected CorpAnnivRepo corpAnnivRepo;
    @Autowired
    protected CorpBenefitRepo corpBenefitRepo;
    @Autowired
    protected CategoryPrincipalRepo categoryPrincipalRepo;

    final protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public CategoryService() {
        super();
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<Category> create(Long idCorpAnniv,
                                  String cat,
                                  String description) {

        Category category;

        CorpAnniv corpAnniv = corpAnnivRepo.findOne(idCorpAnniv);

        if(corpAnniv == null){
            return ResultFactory.getFailResult("Cannot find scheme anniversary with ID [ " + idCorpAnniv + " ]");
        }

        Category categoryByCatAndAnniv = categoryRepo.findByCatAndCorpAnniv(cat, corpAnniv);

        if(categoryByCatAndAnniv != null){
            return ResultFactory.getFailResult("The category supplied is already defined. Please use another category.");
        }

        Category.CategoryBuilder catBuilder = new Category.CategoryBuilder(cat,corpAnniv);

        if(description.trim().length()>0){
            catBuilder.description(description);
        }

        category = catBuilder.build();

        categoryRepo.save(category);
        logger.info("Category after saving..");
        logger.info("CAT: ".concat(category.getCat()).concat(" Desc: "+category.getDescription()).concat(" Id: "+category.getId().toString()));
        logger.info("Corpanniv ID: "+corpAnniv.getId().toString().concat(" Anniv: "+corpAnniv.getAnniv().toString()));
        return ResultFactory.getSuccessResult(category);

    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Result<Category> update(Long idCategory, Long idCorpAnniv, String cat, String description) {

        if(idCategory==null || idCategory < 1L){
            return ResultFactory.getFailResult("Invalid category ID supplied. Update failed.");
        }

        Category category = categoryRepo.findOne(idCategory);

        if(category == null){
            return ResultFactory.getFailResult("Unable to find category with ID [ " + idCategory + " ]");
        }

        CorpAnniv corpAnniv = corpAnnivRepo.findOne(idCorpAnniv);

        if(corpAnniv == null){
            return ResultFactory.getFailResult("Cannot find scheme anniversary with ID [ " + idCorpAnniv + " ]");
        }

        Category categoryByCatAndAnniv = categoryRepo.findByCatAndCorpAnniv(cat,corpAnniv);

        if(categoryByCatAndAnniv != category){
            return ResultFactory.getFailResult("The category supplied is already in use. Please select another category.");
        }

        Category category1 = new Category.CategoryBuilder(cat,corpAnniv).idCategory(idCategory).build();

        //TODO check whether this works, the saving part..
        /*category.setCat(cat);
        category.setCorpAnniv(corpAnniv);
        category.setDescription(description);
        categoryRepo.save(category);*/
        categoryRepo.save(category1);
        return ResultFactory.getSuccessResult(category);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Result<Category> remove(Long idCategory, String actionUsername) {

        Category category = categoryRepo.findOne(idCategory);

        if(category==null){
            return ResultFactory.getFailResult("Cannot remove category. Invalid ID ["+idCategory+"] provided");
        }

        if(corpBenefitRepo.countByCategory(category)>0){
            return ResultFactory.getFailResult("Category has benefits defined. Removal failed.");
        }

        if(categoryPrincipalRepo.countByCategory(category)>0){
            return ResultFactory.getFailResult("Category has principals defined. Removal failed.");
        }

        categoryRepo.delete(category);
        String msg = "Category was deleted by " + actionUsername;
        logger.info(msg);
        return ResultFactory.getSuccessResult(msg);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<Category> find(Long idCategory) {

        if(idCategory==null||idCategory<1){
            return ResultFactory.getFailResult("Category ID cannot be null,zero or a negative value.");
        }

        Category  category = categoryRepo.findOne(idCategory);
        if(category==null){
            return ResultFactory.getFailResult("No category with such ID ["+idCategory+"] was found.");
        }
        return ResultFactory.getSuccessResult(category);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<Page<Category>> findAll(int pageNum,int size,String actionUsername) {

        PageRequest request = new PageRequest(pageNum-1,size);

        Page<Category> page = categoryRepo.findAll(request);

        return ResultFactory.getSuccessResult(page);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<List<Category>> findByAnniv(Long idCorpAnniv) {

        if(idCorpAnniv==null||idCorpAnniv<0L){
            return ResultFactory.getFailResult("Null anniversary ID supplied.");
        }

        CorpAnniv anniv = corpAnnivRepo.findOne(idCorpAnniv);

        if(anniv==null){
            return ResultFactory.getFailResult("The anniversary ID ["+idCorpAnniv+"] supplied is not valid.");
        }

        List<Category> categories = categoryRepo.findByCorpAnniv(anniv);

        logger.info("IdCorpAnniv is: ".concat(idCorpAnniv.toString()));

        categories.stream()
                .forEach(category -> {
                    System.out.println("idCategory: "+category.getId().toString());
                    System.out.println("cat: "+category.getCat());
                });

        if(categories.isEmpty()){
            return ResultFactory.getFailResult("The supplied corporate anniversary has no categories defined.");
        }

        return ResultFactory.getSuccessResult(categories);

    }
}
