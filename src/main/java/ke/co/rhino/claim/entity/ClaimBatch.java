package ke.co.rhino.claim.entity;

import ke.co.rhino.entity.AppUser;
import ke.co.rhino.fin.entity.PaymentVoucher;
import ke.co.rhino.claim.entity.AbstractEntity;
import ke.co.rhino.uw.entity.EntityItem;
import ke.co.rhino.uw.entity.LocalDatePersistenceConverter;
import org.springframework.format.datetime.joda.LocalDateTimeParser;

import javax.json.JsonObjectBuilder;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by ktonym on 1/9/15.
 */
@Entity
@Table(name = "CLAIM_BATCH")
public class ClaimBatch extends AbstractEntity implements EntityItem<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idClaimBatch;
    @Column(nullable = false, unique = true)
    private String batchNo;
//    @ManyToOne
//    @JoinColumn(name="VOUCHER_ID",nullable = true)
//    private PaymentVoucher voucher;
    @OneToMany(mappedBy = "batch")
    private List<Bill> bills;
    @Convert(converter=LocalDatePersistenceConverter.class)
    private LocalDate batchDate;
    private int size;
    private String username;
    /*@ManyToOne@JoinColumn(name="USER_ID")
    private AppUser appUser;*/

    public ClaimBatch() {
    }

    public ClaimBatch(ClaimBatchBuilder claimBatchBuilder) {
        this.idClaimBatch = claimBatchBuilder.idClaimBatch;
        this.batchNo = claimBatchBuilder.batchNo;
        this.batchDate = claimBatchBuilder.batchDate;
        this.username = claimBatchBuilder.username;
        this.size = claimBatchBuilder.size;
    }

    public static class ClaimBatchBuilder{

        private Long idClaimBatch;
        private final String batchNo;
        private final LocalDate batchDate;
        private final int size;
        private String username;

        public ClaimBatchBuilder(String batchNo, LocalDate batchDate, int size) {
            this.batchNo = batchNo;
            this.batchDate = batchDate;
            this.size = size;
        }

        public ClaimBatchBuilder idClaimBatch(Long idClaimBatch){
            this.idClaimBatch = idClaimBatch;
            return this;
        }

        public ClaimBatchBuilder username(String username){
            this.username = username;
            return this;
        }

        public ClaimBatch build(){
            return new ClaimBatch(this);
        }

    }

    public Long getIdClaimBatch() {
        return idClaimBatch;
    }

    public String getBatchNo() {
        return batchNo;
    }
//    public PaymentVoucher getVoucher() {
//        return voucher;
//    }
//
//    public void setVoucher(PaymentVoucher voucher) {
//        this.voucher = voucher;
//    }

    public List<Bill> getBills() {
        return bills;
    }

    public LocalDate getBatchDate() {
        return batchDate;
    }

    public int getSize() {
        return size;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public Long getId() {
        return idClaimBatch;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {

        builder.add("idClaimBatch",idClaimBatch)
                .add("batchNo",batchNo)
                .add("batchDate", batchDate == null ? "" : DATE_FORMATTER_yyyyMMdd.format(batchDate))
                .add("username", username);
//        if(voucher!=null){
//            voucher.addJson(builder);
//        }

    }
}
