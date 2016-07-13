package ke.co.rhino.uw.service;

import ke.co.rhino.uw.entity.Intermediary;
import ke.co.rhino.uw.entity.IntermediaryType;
import ke.co.rhino.uw.vo.Result;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by akipkoech on 14/03/2016.
 */
public interface IIntermediaryService {

    Result<Intermediary> create(String name,
                                IntermediaryType type,
                                String pin,
                                String email,
                                String tel,
                                String town,
                                String street,
                                String postalAddress,
                                LocalDate joinDate,
                                LocalDateTime lastUpdate,
                                String username);

    Result<Intermediary> update(Long idIntermediary,
                                String name,
                                IntermediaryType type,
                                String pin,
                                String email,
                                String tel,
                                String town,
                                String street,
                                String postalAddress,
                                LocalDate joinDate,
                                LocalDateTime lastUpdate,
                                String username);

    Result<List<Intermediary>> findAll(String username);

    Result<Intermediary> find(Long idIntermediary, String username);

    Result<Intermediary> remove(Long idIntermediary, String username);

}
