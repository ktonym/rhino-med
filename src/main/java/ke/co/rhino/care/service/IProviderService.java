package ke.co.rhino.care.service;

import ke.co.rhino.care.entity.ServiceProvider;
import ke.co.rhino.uw.vo.Result;
import org.springframework.data.domain.Page;

import java.util.stream.Stream;

/**
 * Created by akipkoech on 17/03/2016.
 */
public interface IProviderService {

    Result<ServiceProvider> create( String providerName,
                                    String email,
                                    String town,
                                    String tel,
                                    String actionUsername);

    Result<ServiceProvider> update(Long idProvider,
                                   String providerName,
                                   String email,
                                   String town,
                                   String tel,
                                   String actionUsername);

    Result<ServiceProvider> delete(Long idProvider,String actionUsername);

    Result<ServiceProvider> find(Long idProvider,String actionUsername);

    Result<Page<ServiceProvider>> findAll(int page, int size,String actionUsername);

}
