package ke.co.rhino.fin.service;

import ke.co.rhino.fin.entity.AdminFee;
import ke.co.rhino.fin.entity.AdminFeeType;
import ke.co.rhino.fin.repo.AdminFeeRepo;
import ke.co.rhino.fin.repo.FundInvoiceRepo;
import ke.co.rhino.uw.repo.CorpBenefitRepo;
import ke.co.rhino.uw.vo.Result;
import ke.co.rhino.uw.vo.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Created by akipkoech on 19/07/2016.
 */
@Service("adminFeeService")
@Transactional
public class AdminFeeService implements IAdminFeeService {

    @Autowired
    private AdminFeeRepo repo;
    @Autowired
    private FundInvoiceRepo fundInvoiceRepo;
    @Autowired
    private CorpBenefitRepo corpBenefitRepo;

    @Override
    public Result<AdminFee> create(Long idFundInvoice, AdminFeeType adminFeeType, BigDecimal amount, String actionUsername) {

        if(idFundInvoice==null||idFundInvoice<1){
            return ResultFactory.getFailResult("Invalid fund invoice ID provided. Cannot save.");
        }
        if(adminFeeType==null){
            return ResultFactory.getFailResult("Admin fee type not provided. Cannot save.");
        }
        switch (adminFeeType){
            case PERCENTAGE_OF_FUND:
                // Get percentage from fund invoice
                break;
            case BY_POPULATION:
                // Get count of corpMemberBenefit under the corresponding CorpBenefit;
                break;
            case FLAT_RATE:
                // Just save the amount
                break;
            case PER_VISIT:
                // Get definition of per visit fee from fund invoice;
                break;
            default:
                break;

        }
        return null;
    }

    @Override
    public Result<Page<AdminFee>> findAll(Long idCorpBenefit,int pageNo, int size, String actionUsername) {
        return null;
    }

    @Override
    public Result<AdminFee> update(Long idAdminFee, Long idFundInvoice, AdminFeeType adminFeeType, BigDecimal amount, String actionUsername) {
        return null;
    }

    @Override
    public Result<AdminFee> remove(Long idAdminFee, String actionUsername) {
        return null;
    }
}
