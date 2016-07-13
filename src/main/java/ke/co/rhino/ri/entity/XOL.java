package ke.co.rhino.ri.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Created by akipkoech on 12/9/14.
 */
@Entity
@DiscriminatorValue("XOL")
public class XOL extends ReinsuranceTreaty {

    //TODO add fields based on the current XOL arrangement with FirstRe

    public XOL() {
    }
}
