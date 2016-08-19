package ke.co.rhino.care.repo;

import ke.co.rhino.care.entity.ServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Created by akipkoech on 17/03/2016.
 */
public interface ServiceProviderRepo extends PagingAndSortingRepository<ServiceProvider,Long> {

    /*@Query("SELECT s FROM ServiceProvider s WHERE UPPER(s.providerName) = UPPER(:name)")
    ServiceProvider findByNameEqualsIgnoreCase(@Param("name") String name);*/

    //The above method should be same as
    ServiceProvider findByProviderNameIgnoreCase(String name);

   /* @Query("SELECT s FROM ServiceProvider s WHERE UPPER(s.email) = UPPER(:email)")
    ServiceProvider findByEmailEqualsIgnoreCase(@Param("email") String email);*/

    // and this..
    ServiceProvider findByEmailIgnoreCase(String email);

    Optional<ServiceProvider> getOne(Long idServiceProvider);
}
