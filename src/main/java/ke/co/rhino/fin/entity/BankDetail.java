package ke.co.rhino.fin.entity;

import ke.co.rhino.care.entity.ServiceProvider;
import ke.co.rhino.uw.entity.AbstractEntity;
import ke.co.rhino.uw.entity.EntityItem;

import javax.json.JsonObjectBuilder;
import javax.persistence.*;

/**
 * Created by ktonym on 1/9/15.
 */
@Entity@Table(name="BANK_DETAIL")
public class BankDetail extends AbstractEntity implements EntityItem<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idBankDetail;
    private String bankName;
    private String branch;
    @Column(nullable = false, unique = true)
    private Long accountNo;
    //@OneToOne(optional=false)
    // TODO consider using primaryKeyJoinColumn here!!
   // @PrimaryKeyJoinColumn
  /*  @Column(name = "provider_id")
    private Long idProvider;
    this field was created to handle microservice-instigated decoupling
    */
    @OneToOne
    private ServiceProvider serviceProvider;
//    @OneToMany(mappedBy = "bankDetail")
//    private List<Payment> payments;


    public BankDetail() {
    }

    public BankDetail(BankDetailBuilder bankDetailBuilder) {
        this.accountNo = bankDetailBuilder.accountNo;
        this.bankName = bankDetailBuilder.bankName;
        this.branch = bankDetailBuilder.branch;
        this.serviceProvider = bankDetailBuilder.serviceProvider;
    }

    public static class BankDetailBuilder{
        private final ServiceProvider serviceProvider;
        private final String bankName;
        private final String branch;
        private final Long accountNo;

        public BankDetailBuilder(ServiceProvider serviceProvider, String bankName, String branch, Long accountNo) {
            this.serviceProvider = serviceProvider;
            this.bankName = bankName;
            this.branch = branch;
            this.accountNo = accountNo;
        }

        public BankDetail build(){
            return new BankDetail(this);
        }
    }

    public Long getIdBankDetail() {
        return idBankDetail;
    }

    public String getBankName() {
        return bankName;
    }

    public String getBranch() {
        return branch;
    }

    public Long getAccountNo() {
        return accountNo;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    @Override
    public Long getId() {
        return idBankDetail;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {
        builder.add("idBankDetail", idBankDetail)
                .add("bankName",bankName)
                .add("branch", branch)
                .add("accountNo", accountNo);

        serviceProvider.addJson(builder);
    }
}
