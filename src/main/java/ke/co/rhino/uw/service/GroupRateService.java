package ke.co.rhino.uw.service;

import ke.co.rhino.uw.entity.BenefitRef;
import ke.co.rhino.uw.entity.Corporate;
import ke.co.rhino.uw.entity.GroupRate;
import ke.co.rhino.uw.entity.PremiumType;
import ke.co.rhino.uw.repo.BenefitRefRepo;
import ke.co.rhino.uw.repo.CorporateRepo;
import ke.co.rhino.uw.repo.GroupRateRepo;
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
import java.util.List;
import java.util.Map;

/**
 * Created by akipkoech on 11/05/2016.
 */
@Service("groupRateService")
@Transactional
public class GroupRateService implements IGroupRateService {

    @Autowired
    private CorporateRepo corporateRepo;
    @Autowired
    private BenefitRefRepo benefitRefRepo;
    @Autowired
    private GroupRateRepo groupRateRepo;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<GroupRate> create(Long idCorporate,
                                    Long benefitCode,
                                    String famSize,
                                    BigDecimal upperLimit,
                                    BigDecimal premium,
                                    String actionUsername) {

        if(idCorporate==null||idCorporate<1){
            return ResultFactory.getFailResult("The provided corporate ID is invalid. Creation failed.");
        }

        if(benefitCode==null||benefitCode<1){
            return ResultFactory.getFailResult("The provided benefit code is invalid. Creation failed.");
        }
        /**
         * "(^M+(\\+\\d+)?)|(^x)" will match M,M+1,M+2,..,M+n and x
         */
        if(famSize.trim().matches("(^M+(\\+\\d+)?)|(^x)")){
            return ResultFactory.getFailResult("Family size must be a valid text of the format \'M\', \'M+n\' or \'x\'. Creation failed.");
        }

        if(upperLimit==null || premium==null || upperLimit==BigDecimal.ZERO || premium==BigDecimal.ZERO){
            return ResultFactory.getFailResult("Limit or premium must be valid positive amounts. Creation failed.");
        }

        Corporate corporate = corporateRepo.findOne(idCorporate);

        if(corporate==null){
            return ResultFactory.getFailResult("No corporate with ID ["+idCorporate+"] found. Creation failed.");
        }

        BenefitRef benefit = benefitRefRepo.findOne(benefitCode);

        if(benefit==null){
            return ResultFactory.getFailResult("No benefit with code ["+benefitCode+"] found. Creation failed.");
        }

        GroupRate groupRate = new GroupRate();

        groupRate.setFamilySize(famSize);
        groupRate.setCorporate(corporate);
        groupRate.setBenefitRef(benefit);
        groupRate.setUpperLimit(upperLimit);
        groupRate.setPremiumType(PremiumType.GROUP);//Really?
        groupRate.setPremium(premium);

        groupRateRepo.save(groupRate);

        return ResultFactory.getSuccessResult(groupRate);
    }

    @Override@Transactional(readOnly = false,propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<Page<GroupRate>> create(List<Map<String, Object>> mapList, String actionUsername) {
        return null;
    }

    @Override@Transactional(readOnly = false,propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<GroupRate> update(Long idPremiumRate,
                                    Long idCorporate,
                                    Long benefitCode,
                                    String famSize,
                                    BigDecimal upperLimit,
                                    BigDecimal premium,
                                    String actionUsername) {
        return null;
    }

    @Override@Transactional(readOnly = false,propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<GroupRate> remove(Long idPremiumRate, String actionUsername) {
        return null;
    }

    @Override@Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public Result<Page<GroupRate>> findAll(int page, int size,String actionUsername) {

        PageRequest request = new PageRequest(page-1,size);

        Page<GroupRate> result = groupRateRepo.findAll(request);

        return ResultFactory.getSuccessResult(result);
    }

    @Override@Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
    public Result<Page<GroupRate>> findByCorporate(Long idCorporate,
                                                   int page, int size,
                                                   String actionUsername) {

        if(idCorporate==null||idCorporate<1){
            return ResultFactory.getFailResult("The provided corporate ID is invalid.");
        }

        Corporate corporate = corporateRepo.findOne(idCorporate);

        if(corporate==null){
            return ResultFactory.getFailResult("No corporate with ID ["+idCorporate+"] was found.");
        }

        PageRequest request = new PageRequest(page-1,size);

        Page<GroupRate> result = groupRateRepo.findByCorporate(corporate,request);

        return ResultFactory.getSuccessResult(result);
    }
}
