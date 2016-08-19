package ke.co.rhino.uw.service;

import ke.co.rhino.care.entity.ServiceProvider;
import ke.co.rhino.care.repo.ServiceProviderRepo;
import ke.co.rhino.uw.entity.CoPay;
import ke.co.rhino.uw.entity.Corporate;
import ke.co.rhino.uw.entity.Regulation;
import ke.co.rhino.uw.repo.CoPayRepo;
import ke.co.rhino.uw.repo.CorporateRepo;
import ke.co.rhino.uw.repo.RegulationRepo;
import ke.co.rhino.uw.vo.Result;
import ke.co.rhino.uw.vo.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.Callable;

/**
 * Created by akipkoech on 19/08/2016.
 */
@Service("coPayService")
@Transactional
public class CoPayService implements ICoPayService {

    @Autowired
    private CoPayRepo repo;
    @Autowired
    private ServiceProviderRepo serviceProviderRepo;
    @Autowired
    private RegulationRepo regulationRepo;
    @Autowired
    private CorporateRepo corporateRepo;

    @Override
    public Result<CoPay> create(Long idServiceProvider,
                                Long idRegulation,
                                BigDecimal coPayAmount,
                                String actionUsername) {

        if(idServiceProvider==null||idServiceProvider<1){
            return ResultFactory.getFailResult("Invalid service provider. Cannot save.");
        }
        if(idRegulation==null||idRegulation<1){
            return ResultFactory.getFailResult("Invalid regulation provided. Cannot save.");
        }
        if(coPayAmount==null||coPayAmount.compareTo(BigDecimal.ZERO)<1){
            return ResultFactory.getFailResult("Please supply a valid, non-null amount for co-pay. Cannot save.");
        }
        //Once all inputs have been verified as existing, proceed to query database
        Optional<ServiceProvider> spOpt = serviceProviderRepo.getOne(idServiceProvider);

        if(spOpt.isPresent()){

            Optional<Regulation> regOpt = regulationRepo.getOne(idRegulation);

            if(regOpt.isPresent()){
                //Testing whether the record we're trying to define already exists.
                Optional<CoPay> coPayOpt = repo.findByServiceProviderAndRegulation(spOpt.get(),regOpt.get());
                if(coPayOpt.isPresent()){ // throw an error
                    return ResultFactory.getFailResult("A co-pay record exists for the provider and anniversary given. Cannot save.");
                } else { // proceed to define the co-pay record.
                    CoPay coPay = new CoPay.CoPayBuilder(spOpt.get(),regOpt.get(),coPayAmount).build();
                    //save the co-pay record.
                    repo.save(coPay);
                    return ResultFactory.getSuccessResult(coPay);
                }

            } else {
                return ResultFactory.getFailResult("No regulation with ID ["+idRegulation+"] was found. Cannot save.");
            }

        } else {
            return ResultFactory.getFailResult("No service provider with ID ["+idServiceProvider+"] was found. Cannot save.");
        }
    }

    @Override
    public Result<CoPay> update(Long idCoPay,
                                Long idServiceProvider,
                                Long idRegulation,
                                BigDecimal coPayAmount,
                                String actionUsername) {

        if(idCoPay==null||idCoPay<1){
            return ResultFactory.getFailResult("Invalid co-pay record provided. Update failed.");
        }

        if(idServiceProvider==null||idServiceProvider<1){
            return ResultFactory.getFailResult("Invalid service provider record provided. Update failed.");
        }

        if(idRegulation==null||idRegulation<1){
            return ResultFactory.getFailResult("Invalid regulation record provided. Update failed.");
        }

        if(coPayAmount==null||coPayAmount.compareTo(BigDecimal.ZERO)<1){
            return ResultFactory.getFailResult("Invalid co-pay amount provided. Please provide a valid, non-null amount. Update failed.");
        }

        Optional<CoPay> coPayOpt = repo.getOne(idCoPay);

        Optional<ServiceProvider> spOpt = serviceProviderRepo.getOne(idServiceProvider);

        if(spOpt.isPresent()){

            Optional<Regulation> regOpt = regulationRepo.getOne(idRegulation);

            Optional<CoPay> coPayBySpAndRegulationOpt = repo.findByServiceProviderAndRegulation(spOpt.get(),regOpt.get());

            if(coPayOpt.get().equals(coPayBySpAndRegulationOpt.get())){
                CoPay copay = new CoPay.CoPayBuilder(spOpt.get(),regOpt.get(),coPayAmount).build();
                repo.save(copay);
                return ResultFactory.getSuccessResult(copay);
            }else {
                return ResultFactory.getFailResult("The co-pay record you're trying to edit does match the ID of another record. Update failed.");
            }

        } else {
            return ResultFactory.getFailResult("No co-pay with ID ["+idCoPay+"] exists in the system. Update failed.");
        }
    }

    @Override
    public Result<CoPay> delete(Long idCoPay, String actionUsername) {

        if(idCoPay==null||idCoPay<1){
            return ResultFactory.getFailResult("Invalid co-pay provided. Delete failed.");
        }

        Optional<CoPay> coPayOpt = repo.getOne(idCoPay);

        if(coPayOpt.isPresent()){
            // just delete the record, there is no child record associated with coPay.
            repo.delete(idCoPay);
            String msg = coPayOpt.get().toString().concat(" was deleted by ").concat(actionUsername);
            //consider logging this in a database audit table.
            return ResultFactory.getSuccessResult(msg);
        } else{
            return ResultFactory.getFailResult("No co-pay record with ID ["+idCoPay+"] was found. Delete failed.");
        }

    }

    @Override
    public Result<Page<CoPay>> findAll(int page,int size) {

        PageRequest request = new PageRequest(page,size);

        Page<CoPay> coPayPage = repo.findAll(request);

        return ResultFactory.getSuccessResult(coPayPage);
    }

    @Override
    public Result<Page<CoPay>> findByCorporate(Long idCorporate,int page,int size) {

        if(idCorporate==null||idCorporate<1){
            return ResultFactory.getFailResult("Invalid corporate ID provided.");
        }

        Optional<Corporate> corporateOpt = corporateRepo.getOne(idCorporate);

        if(corporateOpt.isPresent()) {
            PageRequest request = new PageRequest(page, size);
            Page<CoPay> coPayPage = repo.findByRegulationCorpAnnivCorporate(corporateOpt.get(), request);
            return ResultFactory.getSuccessResult(coPayPage);
        } else {
            return ResultFactory.getFailResult("No co-pay records found for ".concat(corporateOpt.get().getName()));
        }
    }
}
