package ke.co.rhino.uw.service;

import ke.co.rhino.uw.entity.BenefitRef;
import ke.co.rhino.uw.repo.BenefitRefRepo;
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

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by akipkoech on 10/05/2016.
 */
@Service("benefitRefService")
@Transactional
public class BenefitRefService implements IBenefitRefService {

    @Autowired
    protected BenefitRefRepo benefitRefRepo;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Result<BenefitRef> create(String benefitName,
                                     String description,
                                     String actionUsername) {

        if(benefitName==null||benefitName.trim().isEmpty()){
            return ResultFactory.getFailResult("Benefit name must be non-empty");
        }
        // Test for an existing Benefit with a similar name
        BenefitRef testBenefitRef = benefitRefRepo.findByName(benefitName);

        if(testBenefitRef!=null){
            return ResultFactory.getFailResult("Benefit [" + benefitName + "] already exists in the system.");
        }

        BenefitRef.BenefitRefBuilder benefitRefBuilder = new BenefitRef.BenefitRefBuilder(benefitName);

        BenefitRef benefitRef;

        if(description.trim().length()==0){
            benefitRef = benefitRefBuilder.build();
        } else {
            benefitRef = benefitRefBuilder.description(description).build();
        }

        benefitRefRepo.save(benefitRef);

        return ResultFactory.getSuccessResult(benefitRef);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Result<BenefitRef> update(Long benefitCode,
                                    String benefitName,
                                    String description,
                                    String actionUsername) {

        if (benefitCode == null || benefitCode<1){
            return ResultFactory.getFailResult("Invalid benefit code supplied. Update failed.");
        }

        if(benefitName==null||benefitName.trim().isEmpty()){
            return ResultFactory.getFailResult("Benefit name must be non-empty");
        }

        benefitName=benefitName.trim();
        description=description.trim();

        // Test for an existing Benefit with a similar name
        BenefitRef testBenefitRefByName = benefitRefRepo.findByName(benefitName);

        if(testBenefitRefByName.getBenefitCode()!=benefitCode){
            return ResultFactory.getFailResult("Benefit [" + benefitName + "] already exists under a different code ");
        }

        BenefitRef.BenefitRefBuilder benefitRefBuilder = new BenefitRef.BenefitRefBuilder(benefitName)
                .benefitCode(benefitCode)
                .lastUpdate(LocalDateTime.now());

        BenefitRef benefitRef;

        if(description.isEmpty()||description.length()<1){
            benefitRef = benefitRefBuilder.build();
        } else {
            benefitRef = benefitRefBuilder.description(description).build();
        }

        benefitRefRepo.save(benefitRef);
        return ResultFactory.getSuccessResult(benefitRef);

    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public Result<String> remove(Long benefitCode, String actionUsername) {

        /**
         * First confirm if the benefit ref has any child records: corporate benefits
         * and premium rates
         */

        BenefitRef testBenRef = benefitRefRepo.findOne(benefitCode);
        if(testBenRef.getCorpBenefits().isEmpty()
                && testBenRef.getPremiumRates().isEmpty()){
            benefitRefRepo.delete(benefitCode);
            String msg = testBenRef.toString() +" deleted by " + actionUsername;
            logger.info(msg);
            return ResultFactory.getSuccessResult(msg);
        } else {
            return ResultFactory.getFailResult("Benefit [" + testBenRef.getBenefitName() +"] has child records associated.");
        }


    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<BenefitRef> find(Long benefitCode, String actionUsername) {

        BenefitRef benefitRef = benefitRefRepo.findOne(benefitCode);
        return ResultFactory.getSuccessResult(benefitRef);

    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<Page<BenefitRef>> findAll(int pageNum,int size,String actionUsername) {

        PageRequest request = new PageRequest(pageNum-1,size);

        Page<BenefitRef> benefitRefPage = benefitRefRepo.findAll(request);

        if(benefitRefPage.getTotalPages()<1){
            return ResultFactory.getFailResult("No benefits have been defined yet.");
        }

        return ResultFactory.getSuccessResult(benefitRefPage);

    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<Page<BenefitRef>> search(int pageNum, int size, String searchStr, String actionUsername) {

        if(searchStr.trim().isEmpty()){
            return ResultFactory.getFailResult("Search string must be a valid non-empty text.");
        }

        PageRequest request = new PageRequest(pageNum-1,size);

        Page<BenefitRef> benefitRefPage = benefitRefRepo.findByBenefitNameLike(searchStr,request);

        if(benefitRefPage.getTotalPages()<1){
            return ResultFactory.getFailResult("No such benefit was found.");
        }
        return ResultFactory.getSuccessResult(benefitRefPage);

    }

}
