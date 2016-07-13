package ke.co.rhino.claim.service;

import ke.co.rhino.claim.entity.BillVet;
import ke.co.rhino.claim.entity.VetStatus;
import ke.co.rhino.claim.repo.BillRepo;
import ke.co.rhino.claim.repo.BillVetRepo;
import ke.co.rhino.uw.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by akipkoech on 22/04/2016.
 */
@Service("billVetService")
@Transactional
public class BillVetService implements IBillVetService {

    @Autowired
    private BillRepo billRepo;

    @Autowired
    BillVetRepo billVetRepo;

    @Override
    public Result<BillVet> create(Long idBill, LocalDate billVetDate, String narration,
                                  VetStatus vetStatus, String actionUsername) {
        return null;
    }

    @Override
    public Result<BillVet> update(Long idBillVet, Long idBill, LocalDate billVetDate,
                                  String narration, VetStatus vetStatus, String actionUsername) {
        return null;
    }

    @Override
    public Result<BillVet> remove(Long idBillVet, String actionUsername) {
        return null;
    }

    @Override
    public Result<List<BillVet>> findAll(String actionUsername) {
        return null;
    }

    @Override
    public Result<List<BillVet>> findByBill(Long idBill, String actionUsername) {
        return null;
    }

    @Override
    public Result<List<BillVet>> findByDate(LocalDate billVetDate, String actionUsername) {
        return null;
    }
}
