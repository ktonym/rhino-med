package ke.co.rhino.fin.service;

import ke.co.rhino.fin.entity.PremiumInvoice;
import ke.co.rhino.fin.entity.PremiumInvoiceItem;
import ke.co.rhino.fin.repo.PremiumInvoiceItemRepo;
import ke.co.rhino.fin.repo.PremiumInvoiceRepo;
import ke.co.rhino.uw.entity.CorpBenefit;
import ke.co.rhino.uw.entity.CorpMemberBenefit;
import ke.co.rhino.uw.repo.CorpBenefitRepo;
import ke.co.rhino.uw.repo.CorpMemberBenefitRepo;
import ke.co.rhino.uw.vo.Result;
import ke.co.rhino.uw.vo.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by akipkoech on 23/08/2016.
 */
@Service("premiumInvoiceItemService")
@Transactional
public class PremiumInvoiceItemService implements IPremiumInvoiceItemService {

    @Autowired
    private PremiumInvoiceItemRepo repo;
    @Autowired
    private PremiumInvoiceRepo premiumInvoiceRepo;
    @Autowired
    private CorpBenefitRepo corpBenefitRepo;
    @Autowired
    private CorpMemberBenefitRepo corpMemberBenefitRepo;

    @Override
    public Result<Page<PremiumInvoiceItem>> create(Long idPremiumInvoice, String actionUsername) {

        if(idPremiumInvoice==null||idPremiumInvoice<1){
            return ResultFactory.getFailResult("Invalid invoice provided. Cannot create line items.");
        }
        Optional<CorpBenefit> benefitOpt = corpBenefitRepo.queryByPremiumInvoice(idPremiumInvoice);

        if(benefitOpt.isPresent()){
            //Carry on with other verifications..
            CorpBenefit benefit = benefitOpt.get();

            long cnt = corpMemberBenefitRepo.countByBenefit(benefit);
            if(cnt<1){
                return ResultFactory.getFailResult("No member benefits have been defined for benefit with ID ["+benefit.getId()+"]. Correct this before proceeding to create line items for this premium invoice");
            }

            Stream<CorpMemberBenefit> corpMemberBenefitStream = corpMemberBenefitRepo.findByBenefit(benefit);
            String action = benefit.isMainBenefit() ? "continue" : "stop";
            //TODO let the real work begin here..


            return null;
        } else {
            return ResultFactory.getFailResult("No corporate benefit is associated with invoice ID ["+idPremiumInvoice+"]. Cannot create line items.");
        }


    }

    @Override
    public Result<Page<PremiumInvoiceItem>> findAll(Long idPremiumInvoice, int page, int size, String actionUsername) {
        return null;
    }

    @Override
    public Result<String> delete(Long idPremiumInvoice, String actionUsername) {
        return null;
    }
}
