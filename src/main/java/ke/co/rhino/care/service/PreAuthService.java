package ke.co.rhino.care.service;

import ke.co.rhino.care.entity.PreAuth;
import ke.co.rhino.care.repo.PreAuthRepo;
import ke.co.rhino.care.repo.ServiceProviderRepo;
import ke.co.rhino.uw.repo.CorpMemberBenefitRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Stream;

/**
 * Created by akipkoech on 03/05/2016.
 */
@Service("preAuthService")
@Transactional
public class PreAuthService implements IPreAuthService {

    @Autowired
    private PreAuthRepo preAuthRepo;

    @Autowired
    private ServiceProviderRepo serviceProviderRepo;

    @Autowired
    private CorpMemberBenefitRepo corpMemberBenefitRepo;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public PreAuth create(LocalDate preAuthDate,
                          String preDiagnosis,
                          BigDecimal preAuthLimit,
                          Long idServiceProvider,
                          Long idMember, Long idCorpAnniv, Long idCorpBenefit,
                          String actionUsername){

        return null;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public PreAuth update(Long idPreAuth,
                          LocalDate preAuthDate,
                          String preDiagnosis,
                          BigDecimal preAuthLimit,
                          Long idServiceProvider,
                          Long idMember, Long idCorpAnniv, Long idCorpBenefit,
                          String actionUsername) {
        return null;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public PreAuth remove(Long idPreAuth, String actionUsername) {
        return null;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Stream<PreAuth> findAll() {
        return null;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Stream<PreAuth> findByPreAuthDate(LocalDate date) {
        return null;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Stream<PreAuth> findByPreAuthDateBetween(LocalDate start, LocalDate end) {
        return null;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Stream<PreAuth> findByProvider(Long idProvider) {
        return null;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Stream<PreAuth> findByMember(Long idMember, Long idCorpAnniv, Long idCorpBenefit) {
        return null;
    }
}
