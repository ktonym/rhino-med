package ke.co.rhino.uw.service;

import ke.co.rhino.uw.entity.Category;
import ke.co.rhino.uw.entity.Member;
import ke.co.rhino.uw.entity.MemberCategory;
import ke.co.rhino.uw.entity.MemberCategoryId;
import ke.co.rhino.uw.repo.CategoryRepo;
import ke.co.rhino.uw.repo.MemberCategoryRepo;
import ke.co.rhino.uw.repo.MemberRepo;
import ke.co.rhino.uw.vo.Result;
import ke.co.rhino.uw.vo.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 12/02/2017.
 */
@Transactional
@Service("memberCategoryService")
public class MemberCategoryService implements IMemberCategoryService {
    
    @Autowired
    private MemberCategoryRepo repo;
    @Autowired
    private MemberRepo memberRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    
    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Result<MemberCategory> create(Long idCategory, Long idMember, LocalDate wef, String actionUsername) {

        if(idCategory==null || idCategory <1 || idMember==null || idMember<1){
            return ResultFactory.getFailResult("A valid category and member are required to carry out this step.");
        }

        if(wef==null){
            return ResultFactory.getFailResult("Effective date is missing.");
        }

        Category category = categoryRepo.findOne(idCategory);

        if(category==null){
            return ResultFactory.getFailResult("No category with ID ["+idCategory+"] was found.");
        }

        Member member = memberRepo.findOne(idMember);

        if (member==null){
            return ResultFactory.getFailResult("No member with ID ["+idMember+"] was found.");
        }

        MemberCategoryId memberCatId = new MemberCategoryId(category,member);

        MemberCategory testRec = repo.findOne(memberCatId);

        if(testRec!=null){
            return ResultFactory.getFailResult("The member already belongs to category "+category.getCat());
        }

        // Because only one can be true at a time..

        List<MemberCategory> memberCategories = repo.findByMember(member);

//        memberCategories.parallelStream()
//                .filter(mC -> mC.isActive()).collect(Collectors.toList())
//                .forEach(mC1 -> {
//                        mC1.setActive(false);
//                        MemberCategoryRepo.save(cP1);
//                    });

        // The above java 8 looks awesome, but does it really work? If it does, then the rest of the lines below are useless

        for (MemberCategory memCat: memberCategories){
            if (memCat.isActive()){
                memCat.setActive(false);
//                MemberCategoryRepo.save(catPrincipal); //TODO consider activating this, and c waw hapens
            }
        }

        repo.save(memberCategories);

        MemberCategory memberCategory = new MemberCategory.MemberCategoryBuilder(category,member)
                .active(true).wef(wef).build();

        repo.save(memberCategory);

        return ResultFactory.getSuccessResult(memberCategory);
        
        
    }

    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Result<List<MemberCategory>> create(List<Map<String, Object>> listMap, String actionUsername) {
        return null;
    }

    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Result<MemberCategory> update(Long idCategory, Long idMember, LocalDate wef, Boolean active, String actionUsername) {
        return null;
    }

    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Result<MemberCategory> remove(Long idCategory, Long idMember, String actionUsername) {
        return null;
    }
    
    
    
}
