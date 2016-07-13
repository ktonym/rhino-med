package ke.co.rhino.uw.service;

import ke.co.rhino.uw.entity.CorpAnnivSuspension;
import ke.co.rhino.uw.vo.Result;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by akipkoech on 03/02/2016.
 */
public interface ICorpAnnivSuspService {

    Result<CorpAnnivSuspension> store(Integer idCorpAnnivSusp,
                                      Long idCorpAnniv,
                                      LocalDate start,
                                      LocalDate end,
                                      String reason,
                                      String actionUsername);

    Result<CorpAnnivSuspension> remove(Integer idCorpAnnivSusp, String actionUsername);

    Result<CorpAnnivSuspension> find(Integer idCorpAnnivSusp, String actionUsername);

    Result<List<CorpAnnivSuspension>> findAllInAnniv(Long idCorpAnniv, String actionUsername);

    Result<List<CorpAnnivSuspension>> findAll(Long idCorporate, String actionUsername);

}
