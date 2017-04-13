package ke.co.rhino.uw.service;

import ke.co.rhino.util.RhinoUtil;
import ke.co.rhino.uw.entity.BenefitRef;
import ke.co.rhino.uw.entity.StdPremiumRate;
import ke.co.rhino.uw.repo.BenefitRefRepo;
import ke.co.rhino.uw.repo.StdPremiumRateRepo;
import ke.co.rhino.uw.vo.Result;
import ke.co.rhino.uw.vo.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by user on 10/04/2017.
 */
@Service("stdPremiumRateService")
@Transactional
public class StdPremiumRateService implements IStdPremiumRateService {


    @Autowired
    private StdPremiumRateRepo repo;
    @Autowired
    private BenefitRefRepo benefitRefRepo;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<StdPremiumRate> create( BigDecimal upperLimit,
                                         BigDecimal premium, String familySize,
                                         Long benefitCode, String actionUsername) {

        if(upperLimit == null || upperLimit.compareTo(BigDecimal.ZERO)<0){
            return ResultFactory.getFailResult("No limit specified or limit less than ZERO. Base rate will not be saved");
        }
        if(premium == null){
            return ResultFactory.getFailResult("No premium specified. Base rate will not be saved");
        }
        //Ensure premium is not greater than limit
        if(premium.compareTo(upperLimit)>0){
            return ResultFactory.getFailResult("Premium cannot be more than limit. Base rate will not be saved");
        }
        if(familySize.isEmpty()||familySize==null){
            return ResultFactory.getFailResult("Invalid family size specified. Base rate will not be saved");
        }
        
        if(!RhinoUtil.checkFamSizeFormat(familySize)){
            return ResultFactory.getFailResult("Invalid family size format. Base rate will not be saved");
        }

        if(benefitCode==null||benefitCode<1L){
            return ResultFactory.getFailResult("Invalid benefit reference code provided. Base rate will not be saved");
        }
        BenefitRef benefitRef = benefitRefRepo.findOne(benefitCode);
        if(benefitRef==null){
            return ResultFactory.getFailResult("No benefit with the code provided exists in the system. Base rate will not be saved");
        }

        StdPremiumRate stdPremiumRate = new StdPremiumRate.StdPremiumRateBuilder()
                .premium(premium).upperLimit(upperLimit)
                .benefitRef(benefitRef).familySize(familySize)
                .build();

        repo.save(stdPremiumRate);
        return ResultFactory.getSuccessResult(stdPremiumRate);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<StdPremiumRate> update(Long idPremiumRate,/*PremiumType premiumType,*/BigDecimal upperLimit,
                                         BigDecimal premium, String familySize,
                                         Long benefitCode, String actionUsername) {

        if(idPremiumRate==null||idPremiumRate<1L){
            return ResultFactory.getFailResult("Invalid premium rate ID. Base rate update failed");
        }
        StdPremiumRate testRate = repo.findOne(idPremiumRate);
        if(testRate==null){
            return ResultFactory.getFailResult("No premium rate with ID ["+idPremiumRate+"] was found. Base rate will not be changed");
        }

        StdPremiumRate.StdPremiumRateBuilder builder = new StdPremiumRate.StdPremiumRateBuilder().idPremiumRate(idPremiumRate);
        String msg = new String();
        //Change BenefitRef only if it exists and is valid, else ignore
        if(benefitCode!=null && benefitCode>0){
            BenefitRef benefitRef = benefitRefRepo.findOne(benefitCode);
            if(benefitRef!=null){
                builder.benefitRef(benefitRef);
            }
        }
        //Change FamilySize iff it is valid
        if(familySize!=null && RhinoUtil.checkFamSizeFormat(familySize)){
            builder.familySize(familySize);
        } else {
            msg.concat("Family size supplied was invalid and therefore not changed.");
        }

        if(upperLimit != null && upperLimit.compareTo(BigDecimal.ZERO)>0){
            builder.upperLimit(upperLimit);
        } else {
            msg.concat("Benefit limit was invalid and therefore not changed.");
        }
        if(premium != null && premium.compareTo(upperLimit)<0){
            builder.premium(premium);
        } else {
            msg.concat("Benefit premium was invalid and therefore not changed.");
        }

        StdPremiumRate premiumRate = builder.build();

        return ResultFactory.getSuccessResult(premiumRate,msg);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<Page<StdPremiumRate>> findAll(int page, int size,String actionUsername) {

        PageRequest request = new PageRequest(page-1,size);

        Page<StdPremiumRate> premiumRatePage = repo.findAll(request);

        return ResultFactory.getSuccessResult(premiumRatePage);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<Page<StdPremiumRate>> findByBenefit(int page, int size,Long benefitCode, String actionUsername) {
        
        if(benefitCode==null||benefitCode<1L){
            return ResultFactory.getFailResult("Invalid benefit code provided");
        }
        
        BenefitRef benefitRef = benefitRefRepo.findOne(benefitCode);
        if(benefitRef==null){
            return ResultFactory.getFailResult("No benefit with ID ["+benefitCode+"] was found");
        }
        
        PageRequest request = new PageRequest(page-1,size);

        Page<StdPremiumRate> premiumRatePage = repo.findByCorpBenefit(benefitRef,request);

        return ResultFactory.getSuccessResult(premiumRatePage);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<Page<StdPremiumRate>> findByFamilySize(int page, int size, String familySize, String actionUsername) {

        if( familySize!=null && RhinoUtil.checkFamSizeFormat(familySize)){
            PageRequest request = new PageRequest(page-1,size);
            Page<StdPremiumRate> premiumRatePage = repo.findByFamilySize(familySize,request);
            return ResultFactory.getSuccessResult(premiumRatePage);
        } else {
            return ResultFactory.getFailResult("Family size format is invalid.");
        }

    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<List<StdPremiumRate>> findByLimitSizeAndBenefit(BigDecimal upperLimit, String familySize,
                                                                  Long benefitCode, String actionUsername) {

        //TODO
        return null;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<List<StdPremiumRate>> findByPremiumSizeAndBenefit(BigDecimal premium, String familySize,
                                                                    Long benefitCode, String actionUsername) {

        //TODO
        return null;
    }
    
}
