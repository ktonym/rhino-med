package ke.co.rhino.claim.service;

import ke.co.rhino.claim.entity.BillVet;
import ke.co.rhino.claim.entity.VetStatus;
import ke.co.rhino.uw.vo.Result;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by akipkoech on 21/04/2016.
 */
public interface IBillVetService {

    Result<BillVet> create(Long idBill,
                           LocalDate billVetDate,
                           String narration,
                           VetStatus vetStatus,
                           String actionUsername);

    Result<BillVet> update(Long idBillVet,
                           Long idBill,
                           LocalDate billVetDate,
                           String narration,
                           VetStatus vetStatus,
                           String actionUsername);

    Result<BillVet> remove(Long idBillVet,String actionUsername);

    Result<List<BillVet>> findAll(String actionUsername);

    Result<List<BillVet>> findByBill(Long idBill, String actionUsername);

    Result<List<BillVet>> findByDate(LocalDate billVetDate, String actionUsername);

}
