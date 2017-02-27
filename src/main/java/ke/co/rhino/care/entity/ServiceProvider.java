package ke.co.rhino.care.entity;

import ke.co.rhino.claim.entity.Treatment;
import ke.co.rhino.fin.entity.BankDetail;
import ke.co.rhino.uw.entity.AbstractEntity;
import ke.co.rhino.uw.entity.CoPay;
import ke.co.rhino.uw.entity.EntityItem;

import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.util.List;

/**
 * Created by akipkoech on 14/03/2016.
 */
@Entity
@Table (name="SERVICE_PROVIDER")
public class ServiceProvider extends AbstractEntity implements EntityItem<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProvider;
    private String providerName;
    private String email;
    private String town;
    private String tel;
    @OneToOne(mappedBy = "serviceProvider")//field should be redefined in a micro service edition
    private BankDetail bankDetail;
    @OneToOne(mappedBy = "serviceProvider")//redefinition in a micro service edition
    private CoPay coPay;
    @OneToMany(mappedBy = "serviceProvider")
    private List<Treatment> treatments;

    public ServiceProvider() {
    }

    public ServiceProvider(ServiceProviderBuilder serviceProviderBuilder) {
        this.idProvider = serviceProviderBuilder.idProvider;
        this.providerName = serviceProviderBuilder.providerName;
        this.email = serviceProviderBuilder.email;
        this.town = serviceProviderBuilder.town;
        this.tel = serviceProviderBuilder.tel;
        this.bankDetail = serviceProviderBuilder.bankDetail;
        this.coPay = serviceProviderBuilder.coPay;
    }

    public static class ServiceProviderBuilder{
        private Long idProvider;
        private final String providerName;
        private String email;
        private String town;
        private String tel;
        private BankDetail bankDetail;
        private CoPay coPay;

        public ServiceProviderBuilder(String providerName) {
            this.providerName = providerName;
        }

        public ServiceProviderBuilder idProvider(Long idProvider){
            this.idProvider = idProvider;
            return this;
        }

        public ServiceProviderBuilder email(String email){
            this.email = email;
            return this;
        }

        public ServiceProviderBuilder town(String town){
            this.town = town;
            return this;
        }

        public ServiceProviderBuilder tel(String tel){
            this.tel = tel;
            return this;
        }

        public ServiceProviderBuilder bankDetail(BankDetail bankDetail){
            this.bankDetail = bankDetail;
            return this;
        }

        public ServiceProviderBuilder coPay(CoPay coPay){
            this.coPay = coPay;
            return this;
        }

        public ServiceProvider build(){
            return new ServiceProvider(this);
        }

    }

    @Override
    public void addJson(JsonObjectBuilder builder) {
         builder.add("idProvider", idProvider)
                .add("providerName", providerName)
                .add("email", email)
                .add("town", town)
                .add("tel", tel)
                .build();
    }

    @Override
    public Long getId() {
        return idProvider;
    }
}
