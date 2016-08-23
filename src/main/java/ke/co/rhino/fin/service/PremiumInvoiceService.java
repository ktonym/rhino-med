package ke.co.rhino.fin.service;

import ke.co.rhino.fin.entity.BusinessClass;
import ke.co.rhino.fin.entity.PremiumInvoice;
import ke.co.rhino.fin.repo.PremiumInvoiceRepo;
import ke.co.rhino.uw.repo.CorpAnnivRepo;
import ke.co.rhino.uw.repo.CorporateRepo;
import ke.co.rhino.uw.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 * Created by user on 23-Aug-16.
 */
@Service("premiumInvoiceService")
@Transactional
public class PremiumInvoiceService implements IPremiumInvoiceService {

    @Autowired
    private PremiumInvoiceRepo repo;
    @Autowired
    private CorpAnnivRepo corpAnnivRepo;
    @Autowired
    private CorporateRepo corporateRepo;

    @Override
    public Result<PremiumInvoice> create(BusinessClass businessClass, LocalDate invoiceDate, Long idCorpBenefit, String actionUsername) {
        return null;
    }

    @Override
    public Result<PremiumInvoice> update(Long idPremiumInvoice, String invoiceNumber, BusinessClass businessClass, LocalDate invoiceDate, Long idCorpBenefit, String actionUsername) {
        return null;
    }

    @Override
    public Result<Page<PremiumInvoice>> findAll(Long idCorporate, String actionUsername) {
        return null;
    }

    @Override
    public Result<PremiumInvoice> delete(Long idPremiumInvoice, String actionUsername) {
        return null;
    }
}
