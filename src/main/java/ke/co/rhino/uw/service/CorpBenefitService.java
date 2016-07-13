package ke.co.rhino.uw.service;

import ke.co.rhino.uw.entity.*;
import ke.co.rhino.uw.repo.*;
import ke.co.rhino.uw.vo.Result;
import ke.co.rhino.uw.vo.ResultFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by akipkoech on 27/05/2016.
 */
@Transactional
@Service("corpBenefitService")
public class CorpBenefitService implements ICorpBenefitService {

    @Autowired
    private CorpBenefitRepo corpBenefitRepo;
    @Autowired
    private BenefitRefRepo benefitRefRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private CorporateRepo corporateRepo;
    @Autowired
    private CorpAnnivRepo corpAnnivRepo;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRED)
    public Result<CorpBenefit> create(Long benefitCode,
                                      BigDecimal upperLimit,
                                      MemberType memberType,
                                      BenefitType benefitType,
                                      boolean sharing, boolean needPreAuth,
                                      Integer waitingPeriod,
                                      Long idParentBenefit,
                                      Long idCategory, String actionUsername) {

        if(benefitCode==null||benefitCode<1){
            return ResultFactory.getFailResult("Invalid benefit code provided.");
        }
        if(upperLimit==null||upperLimit.compareTo(BigDecimal.ZERO)<0){
            return ResultFactory.getFailResult("Invalid limit provided. Please revise.");
        }
        if(memberType==null){
            return ResultFactory.getFailResult("Please define the applicable member type.");
        }
        if(idCategory==null||idCategory<1){
            return ResultFactory.getFailResult("Please provide a valid category.");
        }

        BenefitRef benefitRef = benefitRefRepo.findOne(benefitCode);
        if(benefitRef==null){
            return ResultFactory.getFailResult("No benefit with code["+benefitCode+"] exists. Kindly supply a valid benefit.");
        }

        Category category = categoryRepo.findOne(idCategory);
        if(category==null){
            return ResultFactory.getFailResult("No category with code["+idCategory+"] exists. Kindly supply a valid category.");
        }

        //Need to ensure we avoid duplicate (Cat,Benefit) combo
        CorpBenefit testCorpBenefit = corpBenefitRepo.findByCategoryAndBenefitRef(category,benefitRef);
        if(testCorpBenefit!=null){
            return ResultFactory.getFailResult("The specified category already has the benefit assigned.");
        }

        CorpBenefit.CorpBenefitBuilder corpBenefitBuilder = new CorpBenefit.CorpBenefitBuilder(benefitRef,upperLimit,memberType,benefitType,category);

        if(idParentBenefit!=null){
            CorpBenefit parentBenefit = corpBenefitRepo.findOne(idParentBenefit);
            if(parentBenefit==null){
                return ResultFactory.getFailResult("No parent benefit with code ["+idParentBenefit+"] was found. Kindly supply a valid benefit.");
            }
            corpBenefitBuilder.parentBenefit(parentBenefit);
        }

        if(sharing){
            corpBenefitBuilder.sharing(true);
        }
        if(needPreAuth){
            corpBenefitBuilder.requiresPreAuth(true);
        }

        CorpBenefit corpBenefit = corpBenefitBuilder.build();
        corpBenefitRepo.save(corpBenefit);
        return ResultFactory.getSuccessResult(corpBenefit);
    }

    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRED)
    public Result<List<CorpBenefit>> create(List<Map<String, Object>> mapList,
                                            String actionUsername) {

        List<CorpBenefit> corpBenefitList = new ArrayList<>();
        StringBuilder errs = new StringBuilder();

        for(Map map: mapList){
            Long benefitCode = (Long) map.get("benefitCode");
            Double upperLimit = (Double) map.get("upperLimit");
            MemberType memberType = (MemberType) map.get("memberType");
            BenefitType benefitType = (BenefitType) map.get("benefitType");
            boolean sharing = (Boolean) map.get("sharing");
            boolean needPreAuth = (Boolean) map.get("needPreAuth");
            Integer waitingPeriod = (Integer) map.get("waitingPeriod");
            Long idParentBenefit = (Long) map.get("idParentBenefit");
            Long idCategory = (Long) map.get("idCategory");
        }

        corpBenefitRepo.save(corpBenefitList);
        return ResultFactory.getSuccessResult(corpBenefitList,errs.toString());
    }

    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRED)
    public Result<CorpBenefit> update(Long idCorpBenefit,
                                      Long benefitCode,
                                      BigDecimal upperLimit,
                                      MemberType memberType,
                                      BenefitType benefitType,
                                      boolean sharing, boolean needPreAuth,
                                      Integer waitingPeriod, Long idParentBenefit,
                                      Long idCategory, String actionUsername) {

        //TODO 13/07/2016 finish up this..
        return null;
    }

    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRED)
    public Result<CorpBenefit> update(List<Map<String, Object>> mapList, String actionUsername) {
        return null;
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public Result<Page<CorpBenefit>> findAll(Long idCorporate, int pageNum, int size,  String actionUsername) {

        if(idCorporate==null||idCorporate<1){
            return ResultFactory.getFailResult("No corporate code provided.");
        }

        Corporate corporate = corporateRepo.findOne(idCorporate);
        if(corporate==null){
            return ResultFactory.getFailResult("No corporate with ID ["+idCorporate+"] was found.");
        }

        PageRequest request = new PageRequest(pageNum-1,size);
        Page<CorpBenefit> corpBenefitPage = corpBenefitRepo.findByCategory_CorpAnniv_Corporate(corporate,request);
        return ResultFactory.getSuccessResult(corpBenefitPage);
    }

    @Override
    public Result<Page<CorpBenefit>> findByCorpAnniv(Long idCorpAnniv, int pageNum, int size, String actionUsername) {

        if(idCorpAnniv==null||idCorpAnniv<1){
            return ResultFactory.getFailResult("Invalid corporate anniversary id provided. Kindly revise.");
        }

        CorpAnniv corpAnniv = corpAnnivRepo.findOne(idCorpAnniv);
        if(corpAnniv==null){
            return ResultFactory.getFailResult("No corporate anniversary with ID ["+idCorpAnniv+"] was found.");
        }

        PageRequest request = new PageRequest(pageNum-1,size);
        Page<CorpBenefit> corpBenefitPage = corpBenefitRepo.findByCategory_CorpAnniv(corpAnniv,request);
        return ResultFactory.getSuccessResult(corpBenefitPage);
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public Result<Page<CorpBenefit>> findByCategory(Long idCategory, int pageNum, int size, String actionUsername) {

        if(idCategory==null||idCategory<1){
            return ResultFactory.getFailResult("Invalid category code given. Kindly revise.");
        }

        Category category = categoryRepo.findOne(idCategory);

        if(category==null){
            return ResultFactory.getFailResult("No category with ID ["+idCategory+"] was found.");
        }

        PageRequest request = new PageRequest(pageNum-1,size);
        Page<CorpBenefit> corpBenefitPage = corpBenefitRepo.findByCategory(category,request);
        return ResultFactory.getSuccessResult(corpBenefitPage);
    }

    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRED)
    public Result<CorpBenefit> remove(Long idCorpBenefit, String actionUsername) {
        return null;
    }
}
