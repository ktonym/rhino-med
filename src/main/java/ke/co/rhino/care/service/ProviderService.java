package ke.co.rhino.care.service;

import ke.co.rhino.care.entity.ServiceProvider;
import ke.co.rhino.care.repo.ServiceProviderRepo;
import ke.co.rhino.base.service.AbstractService;
import ke.co.rhino.uw.vo.Result;
import ke.co.rhino.uw.vo.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

/**
 * Created by akipkoech on 17/03/2016.
 */
@Service("providerService")
@Transactional
public class ProviderService extends AbstractService implements IProviderService {

    @Autowired
    ServiceProviderRepo repo;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Result<ServiceProvider> create(String providerName,
                                          String email,
                                          String town,
                                          String tel,
                                          String actionUsername){

        if(providerName == null || providerName.trim().length()==0 ||
                email == null || email.trim().length() == 0 ||
                town == null || town.trim().length() == 0 ||
                tel == null || tel.trim().length() == 0){
            return ResultFactory.getFailResult("Check that no field is blank.");
        }

        if(repo.findByProviderNameIgnoreCase(providerName.trim()) != null){
            return ResultFactory.getFailResult("A provider with a similar name exists in the system.");
        }

        if(repo.findByEmailIgnoreCase(email.trim()) != null){
            return ResultFactory.getFailResult("A provider with a similar email exists in the system.");
        }

        if(providerName.length()<3){
            return ResultFactory.getFailResult("Provider must contain a minimum of 3 characters.");
        }

        ServiceProvider provider = new ServiceProvider.ServiceProviderBuilder(providerName.trim())
                .email(email.trim())
                .town(town.trim())
                .tel(tel.trim()).build();


        repo.save(provider);

        return ResultFactory.getSuccessResult(provider);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Result<ServiceProvider> update(Long idProvider,
                                          String providerName,
                                          String email,
                                          String town,
                                          String tel,
                                          String actionUsername){

        ServiceProvider provider = repo.findOne(idProvider);

        if(provider == null){
            return ResultFactory.getFailResult("Unable to find Service Provider with ID = " + idProvider);
        }

        if( providerName == null || providerName.trim().length()==0 ||
                email == null || email.trim().length() == 0 ||
                town == null || town.trim().length() == 0 ||
                tel == null || tel.trim().length() == 0){
            return ResultFactory.getFailResult("Check that no field is blank.");
        }

        if(!repo.findByEmailIgnoreCase(email.trim()).equals(provider)){
            return ResultFactory.getFailResult("A Service Provider with the same email address exists. Rename failed.");
        }

        if(!repo.findByProviderNameIgnoreCase(providerName.trim()).equals(provider)){
            return ResultFactory.getFailResult("A Service Provider with the same name exists. Rename failed.");
        }

        ServiceProvider prvdr = new ServiceProvider.ServiceProviderBuilder(providerName.trim())
                .tel(tel.trim())
                .town(town.trim()).email(email.trim())
                .idProvider(idProvider).build();
        repo.save(prvdr);
        return ResultFactory.getSuccessResult(prvdr);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Result<ServiceProvider> delete(Long idProvider, String actionUsername) {
        //TODO implement with child records in mind
        return null;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<ServiceProvider> find(Long idProvider, String actionUsername) {

        ServiceProvider provider = repo.findOne(idProvider);

        return ResultFactory.getSuccessResult(provider);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<Stream<ServiceProvider>> findAll(String actionUsername) {
        return ResultFactory.getSuccessResult(repo.findAll().stream());
    }
}
