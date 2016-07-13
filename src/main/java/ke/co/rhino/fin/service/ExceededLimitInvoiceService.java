package ke.co.rhino.fin.service;

import ke.co.rhino.base.service.AbstractService;
import ke.co.rhino.fin.entity.ExceededLimitInvoice;
import ke.co.rhino.fin.repo.ExceededLimitInvoiceRepo;
import ke.co.rhino.uw.entity.*;
import ke.co.rhino.uw.repo.*;
import ke.co.rhino.uw.vo.Result;
import ke.co.rhino.uw.vo.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by akipkoech on 08/07/2016.
 */
@Service("ExceededLimitInvoiceService")
@Transactional
public class ExceededLimitInvoiceService extends AbstractService implements IExceededLimitInvoiceService {

    @Autowired
    private ExceededLimitInvoiceRepo repo;
    @Autowired
    private CorpMemberBenefitRepo cmbRepo;
    @Autowired
    private MemberRepo memberRepo;
    @Autowired
    private CorpAnnivRepo corpAnnivRepo;
    @Autowired
    private MemberAnniversaryRepo mAnnivRepo;
    @Autowired
    private CorpBenefitRepo corpBenefitRepo;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Result<ExceededLimitInvoice> create(Long idMember,
                                               Long idCorpAnniv,
                                               Long idCorpBenefit,
                                               BigDecimal amount) {
        //check if we have all required inputs for memberAnnivId class
        if(idMember==null||idMember<0||idCorpAnniv==null||idCorpAnniv<0){
            return ResultFactory.getFailResult("Missing or invalid member ID or corporate anniversary ID");
        }
        //check if we have the parameter to build a corp benefit
        if(idCorpBenefit==null||idCorpBenefit<0){
            return ResultFactory.getFailResult("Missing or invalid corp benefit ID");
        }
        if(amount==null||amount.doubleValue()<0||amount.equals(BigDecimal.ZERO)){
            return ResultFactory.getFailResult("Invalid amount given. Amount must be a positive non-null figure.");
        }

        Member member = memberRepo.findOne(idMember);
        if(member==null){
            return ResultFactory.getFailResult("No member with ID ["+idMember+"] was found.");
        }
        CorpAnniv corpAnniv = corpAnnivRepo.findOne(idCorpAnniv);
        if(corpAnniv==null){
            return ResultFactory.getFailResult("No corporate anniversary with ID ["+idCorpAnniv+"] was found.");
        }
        CorpBenefit corpBenefit = corpBenefitRepo.findOne(idCorpBenefit);
        if(corpBenefit==null){
            return ResultFactory.getFailResult("No corporate benefit with ID ["+idCorpBenefit+"] was found.");
        }
        //MemberAnnivId mai = new MemberAnnivId(member,corpAnniv);
        MemberAnniversary memberAnniversary = mAnnivRepo.findByCorpAnnivAndMember(corpAnniv,member);
        CorpMemberBenefit cmb = cmbRepo.findByMemberAnnivAndBenefit(memberAnniversary,corpBenefit);
        ExceededLimitInvoice invoice = new ExceededLimitInvoice.ExceededLimitInvoiceBuilder(cmb,amount, LocalDate.now()).build();
        repo.save(invoice);
        return ResultFactory.getSuccessResult(invoice);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Result<ExceededLimitInvoice> update(Long idXLInvoice, BigDecimal amount) {

        if(idXLInvoice==null){
            return ResultFactory.getFailResult("Missing ID for exceeded limit invoice. Update failed.");
        }
        ExceededLimitInvoice xlInv = repo.getOne(idXLInvoice);
        if(xlInv==null){
            return ResultFactory.getFailResult("Invalid ID for exceeded limit invoice supplied. Update failed.");
        }
        //ExceededLimitInvoice.ExceededLimitInvoiceBuilder builder = new ExceededLimitInvoice.ExceededLimitInvoiceBuilder()

        return null;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<Page<ExceededLimitInvoice>> findAll() {
        return null;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<Page<ExceededLimitInvoice>> findByMemberAnniversary(Long idMember, Long idCorpAnniv) {
        return null;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<Page<ExceededLimitInvoice>> findByCorpAnniversary(Long idCorpAnniv) {
        return null;
    }
}
