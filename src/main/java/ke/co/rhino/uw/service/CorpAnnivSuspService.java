package ke.co.rhino.uw.service;

import ke.co.rhino.base.service.AbstractService;
import ke.co.rhino.uw.entity.CorpAnniv;
import ke.co.rhino.uw.entity.CorpAnnivSuspension;
import ke.co.rhino.uw.entity.Corporate;
import ke.co.rhino.uw.repo.CorpAnnivRepo;
import ke.co.rhino.uw.repo.CorpAnnivSuspensionRepo;
import ke.co.rhino.uw.repo.CorporateRepo;
import ke.co.rhino.uw.vo.Result;
import ke.co.rhino.uw.vo.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by akipkoech on 03/02/2016.
 */
@Service("corpAnnivSuspService")
@Transactional
public class CorpAnnivSuspService extends AbstractService implements ICorpAnnivSuspService {


    @Autowired
    protected CorporateRepo corporateRepo;
    @Autowired
    protected CorpAnnivRepo corpAnnivRepo;
    @Autowired
    protected CorpAnnivSuspensionRepo corpAnnivSuspensionRepo;


    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Result<CorpAnnivSuspension> store(Integer idCorpAnnivSusp,
                                             Long idCorpAnniv,
                                             LocalDate start,
                                             LocalDate end,
                                             String reason,
                                             String actionUsername) {

//        if (!isValidUser(actionUsername)) {
//            return ResultFactory.getFailResult(USER_INVALID);
//        }

//        User actionUser = userRepo.findOne(actionUsername);
//
//        UserGroup group = actionUser.getUserGroup();

        CorpAnniv corpAnniv = corpAnnivRepo.findOne(idCorpAnniv);

        if (corpAnniv == null) {
            return ResultFactory.getFailResult("No anniversary found!");
        }

        CorpAnnivSuspension corpAnnivSuspension = corpAnnivSuspensionRepo.findOne(idCorpAnnivSusp);
        /**
         *  Test existence of a suspension
         */
        if (corpAnnivSuspension == null) { // creating a new suspension
            /**
             * Parameter <b>startDate</b> must exist, else set current date
             */

            corpAnnivSuspension = new CorpAnnivSuspension();

            if (start == null) {
                corpAnnivSuspension.setStartDate(LocalDate.now());
            }
            // Reason must be present
            if (reason == null || reason.isEmpty() || reason.trim().length() == 0) {
                return ResultFactory.getFailResult("Cannot suspend a cover without a valid reason.");
            }

            corpAnnivSuspension.setReason(reason);

            if (end != null) { //if endDate has been defined, set it
                if (end.isBefore(start) || end.equals(start)) {
                    return ResultFactory.getFailResult("End date must be after start date");
                }
                corpAnnivSuspension.setEndDate(end);
            }


        } else { /**
         * Lifting or extending a suspension
         * Only U/W Supervisors and above can do this
         */
//            if (!group.getGroupName().equalsIgnoreCase("UW_SPVZ") || !group.getGroupName().equalsIgnoreCase("UW_MGR")) {
//                return ResultFactory.getFailResult(USER_NOT_AUTHORIZED);
//            }

            if (end == null) {
                return ResultFactory.getFailResult("End date must be defined when lifting/extending a suspension");
            }

            if (end.isBefore(start) || end.equals(start)) {
                return ResultFactory.getFailResult("End date must be after start date");
            }

            /**
             * Disallowing pushing back of suspension end date.
             */
            if (corpAnnivSuspension.getEndDate().isAfter(end)) {
                return ResultFactory.getFailResult("Fraud detection alert! Cannot push back end date.");
            }

            corpAnnivSuspension.setEndDate(end);

        }

        //corpAnnivSuspension.setUser(actionUser);
        corpAnnivSuspensionRepo.save(corpAnnivSuspension);

        return ResultFactory.getSuccessResult(corpAnnivSuspension, "Cover suspension successfully saved.");


    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Result<CorpAnnivSuspension> remove(Integer idCorpAnnivSusp, String actionUsername) {
        return null;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<CorpAnnivSuspension> find(Integer idCorpAnnivSusp, String actionUsername) {
        return null;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<List<CorpAnnivSuspension>> findAllInAnniv(Long idCorpAnniv, String actionUsername) {
//        if (!isValidUser(actionUsername)) {
//            return ResultFactory.getFailResult(USER_INVALID);
//        }

        CorpAnniv corpAnniv = corpAnnivRepo.findOne(idCorpAnniv);

        if (corpAnniv == null) {
            return ResultFactory.getFailResult("Cannot fetch suspensions without a valid corporate anniversary.");
        }

        List<CorpAnnivSuspension> suspensionList = corpAnnivSuspensionRepo.findByCorpAnniv(corpAnniv);

        return ResultFactory.getSuccessResult(suspensionList);
    }

    /**
     * @param idCorporate
     * @param actionUsername
     * @return List of Anniversary suspensions
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<List<CorpAnnivSuspension>> findAll(Long idCorporate,
                                                     String actionUsername) {
//        if (!isValidUser(actionUsername)) {
//            return ResultFactory.getFailResult(USER_INVALID);
//        }

        Corporate corporate = corporateRepo.findOne(idCorporate);

        if (corporate == null) {
            return ResultFactory.getFailResult("Cannot fetch suspensions without a valid corporate id.");
        }

        List<CorpAnnivSuspension> suspensionList = corpAnnivSuspensionRepo.findByCorpAnniv_Corporate(corporate);

        return ResultFactory.getSuccessResult(suspensionList);
    }
}
