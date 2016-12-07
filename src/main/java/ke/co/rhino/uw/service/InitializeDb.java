package ke.co.rhino.uw.service;

import ke.co.rhino.care.entity.ServiceProvider;
import ke.co.rhino.care.repo.ServiceProviderRepo;
import ke.co.rhino.uw.entity.*;
import ke.co.rhino.uw.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by akipkoech on 12/9/14.
 */
@Service
@Transactional
public class InitializeDb {

    @Autowired
    private CorporateRepo corpRepo;

    @Autowired
    private IntermediaryRepo intRepo;

    @Autowired
    private ContactPersonRepo contactInfoRepo;

    @Autowired
    private CorpAnnivRepo corpAnnivRepo;

    @Autowired
    private ServiceProviderRepo spRepo;

    @Autowired
    private PrincipalRepo principalRepo;

    //    @Autowired
//    private UserRepo userRepo;
//
//    @Autowired
//    private RoleRepo roleRepo;
//
//    @Autowired
//    private UserRoleRepo userRoleRepo;
//
//    @Autowired
//    private MenuRepo menuRepo;
//
//    @Autowired
//    private UserGroupRepo userGroupRepo;
//
//    @Autowired
//    private PermissionRepo permissionRepo;
//
//    @Autowired
//    private IntermediaryRepo intermediaryRepo;
//
    @PostConstruct
    public void init() {
//
//        Role uw_role = new Role();
//        uw_role.setRoleName("UW_UZR");
//        roleRepo.save(uw_role);
//
//        Role uw_supervisor = new Role();
//        uw_supervisor.setRoleName("UW_SPVZ");
//        roleRepo.save(uw_supervisor);
//
//        Role clm_analyst = new Role();
//        clm_analyst.setRoleName("CLM_UZR");
//        roleRepo.save(clm_analyst);
//
//        Role clm_supervisor = new Role();
//        clm_supervisor.setRoleName("CLM_SPVZ");
//        roleRepo.save(clm_supervisor);
//
//        Role sys_adm = new Role();
//        sys_adm.setRoleName("SYS_ADM");
//        roleRepo.save(sys_adm);
//
//        UserRole userRole1 = new UserRole();
//        userRole1.setRole(uw_role);
//        userRoleRepo.save(userRole1);
//
//        UserRole userRole2 = new UserRole();
//        userRole2.setRole(sys_adm);
//        userRoleRepo.save(userRole2);
//
//        UserGroup UW_UZR = new UserGroup();
//        UW_UZR.setGroupName("Underwriter");
//        userGroupRepo.save(UW_UZR);
//
//        UserGroup UW_SPVZ = new UserGroup();
//        UW_SPVZ.setGroupName("Underwriting Supervisor");
//        userGroupRepo.save(UW_SPVZ);
//
//        UserGroup UW_MGR = new UserGroup();
//        UW_MGR.setGroupName("Underwriting Manager");
//        userGroupRepo.save(UW_MGR);
//
//        UserGroup CLM_UZR = new UserGroup();
//        CLM_UZR.setGroupName("Claims Analyst");
//        userGroupRepo.save(CLM_UZR);
//
//        UserGroup CLM_SPVZ = new UserGroup();
//        CLM_SPVZ.setGroupName("Claims Supervisor");
//        userGroupRepo.save(CLM_SPVZ);
//
//        UserGroup CLM_MGR = new UserGroup();
//        CLM_MGR.setGroupName("Claims Manager");
//        userGroupRepo.save(CLM_MGR);
//
//        UserGroup CARE_MGR = new UserGroup();
//        CARE_MGR.setGroupName("Care Manager");
//        userGroupRepo.save(CARE_MGR);
//
//        UserGroup SYS_ADM = new UserGroup();
//        SYS_ADM.setGroupName("System Administrator");
//        userGroupRepo.save(SYS_ADM);
//
//        User user1 = new User();
//        user1.setUsername("akipkoech");
//        user1.setFirstName("Anthony");
//        user1.setLastName("Kipkoech");
//        user1.setPassword("1Madison#");
//        user1.setEmail("akipkoech@madison.co.ke");
//        user1.setUserGroup(SYS_ADM);
//        userRepo.save(user1);
//
//        User liz = new User();
//        liz.setUsername("endungu");
//        liz.setFirstName("Elizabeth");
//        liz.setLastName("Ndungu");
//        liz.setPassword("1Madison#");
//        liz.setEmail("lndungu@madison.co.ke");
//        liz.setUserGroup(UW_UZR);
//        userRepo.save(liz);
//
//        User user2 = new User();
//        user2.setUsername("cmwangi");
//        user2.setFirstName("Cliffe");
//        user2.setLastName("Mwangi");
//        user2.setPassword("1Madison#");
//        user2.setEmail("cmwangi@gmail.com");
//        user2.setUserGroup(CLM_UZR);
//        userRepo.save(user2);
//
//        Menu menu1 = new Menu();
//        menu1.setText("security");
//        menu1.setIconCls("fa fa-group fa-lg");
//        menuRepo.save(menu1);
//
//        Menu menu11 = new Menu();
//        menu11.setText("groupsAndPerms");
//        menu11.setIconCls("xf0c0");
//        menu11.setClassName("panel");
//        menu11.setParentMenu(menu1);
//        menuRepo.save(menu11);
//
//        Menu menu12 = new Menu();
//        menu12.setText("users");
//        menu12.setIconCls("xf007");
//        menu12.setClassName("user");
//        menu12.setParentMenu(menu1);
//        menuRepo.save(menu12);
//
//        Menu menu2 = new Menu();
//        menu2.setText("underwriting");
//        menu2.setIconCls("fa fa-database fa-lg");
//        menuRepo.save(menu2);
//
//        Menu mnuSchemes = new Menu();
//        mnuSchemes.setText("schemes");
//        mnuSchemes.setIconCls("xf19c");
//        mnuSchemes.setClassName("corporate");
//        mnuSchemes.setParentMenu(menu2);
//        menuRepo.save(mnuSchemes);
//
//        Menu mnuMem = new Menu();
//        mnuMem.setText("members");
//        mnuMem.setIconCls("xf005");
//        mnuMem.setClassName("panel");
//        mnuMem.setParentMenu(menu2);
//        menuRepo.save(mnuMem);
//
//        Menu mnuInvc = new Menu();
//        mnuInvc.setText("invoicing");
//        mnuInvc.setIconCls("xf005");
//        mnuInvc.setClassName("panel");
//        mnuInvc.setParentMenu(menu2);
//        menuRepo.save(mnuInvc);
//
//        Menu menuUWRef = new Menu();
//        menuUWRef.setText("uwSettings");
//        menuUWRef.setIconCls("fa fa-gears fa-lg");
//        menuRepo.save(menuUWRef);
//
//        Menu mnuBenRef = new Menu();
//        mnuBenRef.setText("benefitRefs");
//        mnuBenRef.setIconCls("xf06b");
//        mnuBenRef.setClassName("benefitref");
//        mnuBenRef.setParentMenu(menuUWRef);
//        menuRepo.save(mnuBenRef);
//
//        Menu mnuIntermed = new Menu();
//        mnuIntermed.setText("intermediaries");
//        mnuIntermed.setIconCls("xf005");
//        mnuIntermed.setClassName("intermediary");
//        mnuIntermed.setParentMenu(menuUWRef);
//        menuRepo.save(mnuIntermed);
//
//        Menu mnuCare = new Menu();
//        mnuCare.setText("care");
//        mnuCare.setIconCls("fa fa-medkit fa-lg");
//        menuRepo.save(mnuCare);
//
//        Menu mnuPreauth = new Menu();
//        mnuPreauth.setText("preAuth");
//        mnuPreauth.setIconCls("xf0ac");
//        mnuPreauth.setClassName("panel");
//        mnuPreauth.setParentMenu(mnuCare);
//        menuRepo.save(mnuPreauth);
//
//        Menu mnuClm = new Menu();
//        mnuClm.setText("claims");
//        mnuClm.setIconCls("fa fa-database fa-lg");
//        menuRepo.save(mnuClm);
//
//        Menu mnuBatClm = new Menu();
//        mnuBatClm.setText("batClaim");
//        mnuBatClm.setIconCls("xf0ac");
//        mnuBatClm.setClassName("panel");
//        mnuBatClm.setParentMenu(mnuClm);
//        menuRepo.save(mnuBatClm);
//
//        Menu mnuEntrClm = new Menu();
//        mnuEntrClm.setText("enterClaim");
//        mnuEntrClm.setIconCls("xf0ac");
//        mnuEntrClm.setClassName("panel");
//        mnuEntrClm.setParentMenu(mnuClm);
//        menuRepo.save(mnuEntrClm);
//
//        Menu mnuVetClm = new Menu();
//        mnuVetClm.setText("vetClaim");
//        mnuVetClm.setIconCls("xf0ac");
//        mnuVetClm.setClassName("panel");
//        mnuVetClm.setParentMenu(mnuClm);
//        menuRepo.save(mnuVetClm);
//
//        Menu mnuClmVchr = new Menu();
//        mnuClmVchr.setText("voucherClaim");
//        mnuClmVchr.setIconCls("xf0ac");
//        mnuClmVchr.setClassName("panel");
//        mnuClmVchr.setParentMenu(mnuClm);
//        menuRepo.save(mnuClmVchr);
//
//        Menu mnuAuthClm = new Menu();
//        mnuAuthClm.setText("claimPayment");
//        mnuAuthClm.setIconCls("xf0ac");
//        mnuAuthClm.setClassName("panel");
//        mnuAuthClm.setParentMenu(mnuClm);
//        menuRepo.save(mnuAuthClm);
//
//        Menu mnuRI = new Menu();
//        mnuRI.setText("reinsurance");
//        mnuRI.setIconCls("fa fa-database fa-lg");
//        menuRepo.save(mnuRI);
//
//        Menu mnuReports = new Menu();
//        mnuReports.setText("reports");
//        mnuReports.setIconCls("fa fa-line-chart fa-lg");
//        menuRepo.save(mnuReports);
//
//        Permission perm1 = new Permission();
//        perm1.setMenu(menu1);
//        perm1.setUserGroup(SYS_ADM);
//        perm1.setName("SECURITY");
//        permissionRepo.save(perm1);
//
//        Permission perm2 = new Permission();
//        perm2.setMenu(menu11);
//        perm2.setUserGroup(SYS_ADM);
//        perm2.setName("GROUPS_PERMS");
//        permissionRepo.save(perm2);
//
//        Permission perm3 = new Permission();
//        perm3.setMenu(menu12);
//        perm3.setUserGroup(SYS_ADM);
//        perm3.setName("USERS");
//        permissionRepo.save(perm3);
//
//        Permission perm4 = new Permission();
//        perm4.setMenu(mnuReports);
//        perm4.setUserGroup(SYS_ADM);
//        perm4.setName("RPT");
//        permissionRepo.save(perm4);
//
//        Permission uw_perm = new Permission();
//        uw_perm.setMenu(menu2);
//        uw_perm.setUserGroup(UW_UZR);
//        uw_perm.setName("UW_PERM");
//        permissionRepo.save(uw_perm);
//
//        Permission schemePerm = new Permission();
//        schemePerm.setMenu(mnuSchemes);
//        schemePerm.setUserGroup(UW_UZR);
//        schemePerm.setName("SCHEME_PERM");
//        permissionRepo.save(schemePerm);
//
//        Permission memPerm = new Permission();
//        memPerm.setMenu(mnuMem);
//        memPerm.setUserGroup(UW_UZR);
//        memPerm.setName("MEM_PERM");
//        permissionRepo.save(memPerm);
//
//        Permission invcPerm = new Permission();
//        invcPerm.setMenu(mnuInvc);
//        invcPerm.setUserGroup(UW_UZR);
//        invcPerm.setName("INVC_PERM");
//        permissionRepo.save(invcPerm);
//
//        Permission uwRefPerm = new Permission();
//        uwRefPerm.setMenu(menuUWRef);
//        uwRefPerm.setUserGroup(UW_UZR);
//        uwRefPerm.setName("UW_REF_PERM");
//        permissionRepo.save(uwRefPerm);
//
//        Permission uwRefBenPerm = new Permission();
//        uwRefBenPerm.setMenu(mnuBenRef);
//        uwRefBenPerm.setUserGroup(UW_UZR);
//        uwRefBenPerm.setName("UW_BEN_REF_PERM");
//        permissionRepo.save(uwRefBenPerm);
//
//        Permission uwRefIntermedPerm = new Permission();
//        uwRefIntermedPerm.setMenu(mnuIntermed);
//        uwRefIntermedPerm.setUserGroup(UW_UZR);
//        uwRefIntermedPerm.setName("UW_INTERMED_REF_PERM");
//        permissionRepo.save(uwRefIntermedPerm);
//
//        Permission clm_perm = new Permission();
//        clm_perm.setMenu(mnuClm);
//        clm_perm.setUserGroup(CLM_UZR);
//        clm_perm.setName("CLM_PERM");
//        permissionRepo.save(clm_perm);
//
//        Permission enter_clm_perm = new Permission();
//        enter_clm_perm.setMenu(mnuEntrClm);
//        enter_clm_perm.setUserGroup(CLM_UZR);
//        enter_clm_perm.setName("ENTER_CLM_PERM");
//        permissionRepo.save(enter_clm_perm);
//
//        Permission bat_clm_perm = new Permission();
//        bat_clm_perm.setMenu(mnuBatClm);
//        bat_clm_perm.setUserGroup(CLM_UZR);
//        bat_clm_perm.setName("BATCH_CLM_PERM");
//        permissionRepo.save(bat_clm_perm);
//
//        Permission vet_clm_perm = new Permission();
//        vet_clm_perm.setMenu(mnuVetClm);
//        vet_clm_perm.setUserGroup(CLM_UZR);
//        vet_clm_perm.setName("VET_CLM_PERM");
//        permissionRepo.save(vet_clm_perm);
//
//        Permission vchr_clm_perm = new Permission();
//        vchr_clm_perm.setMenu(mnuClmVchr);
//        vchr_clm_perm.setUserGroup(CLM_UZR);
//        vchr_clm_perm.setName("VCHR_CLM_PERM");
//        permissionRepo.save(vchr_clm_perm);
//
//        Permission pay_clm_perm = new Permission();
//        pay_clm_perm.setMenu(mnuAuthClm);
//        pay_clm_perm.setUserGroup(CLM_UZR);
//        pay_clm_perm.setName("PAY_CLM_PERM");
//        permissionRepo.save(pay_clm_perm);

        Corporate c1 = new Corporate.CorporateBuilder("Kenya Broadcasting", "KBC")
                .email("info@kbc.com")
                .postalAddress("P.O. Box 30456 Nairobi")
                .joined(LocalDate.now())
                .tel("020589634").build();

        Corporate c2 = new Corporate.CorporateBuilder("Kenya Wildlife", "KWS")
                .email("info@kws.com")
                .postalAddress("P.O. Box 34446 Nairobi")
                .joined(LocalDate.now())
                .tel("0278894").build();

        Corporate c3 = new Corporate.CorporateBuilder("Uganda Wildlife", "UWS")
                .email("info@uws.com")
                .postalAddress("P.O. Box 34446 Kampala")
                .joined(LocalDate.now())
                .tel("0233333394").build();

        corpRepo.save(c1);
        corpRepo.save(c2);
        corpRepo.save(c3);

        Intermediary intm1 = new Intermediary
                .IntermediaryBuilder("The Insurance Brokers", "P898899876K", IntermediaryType.BROKER, LocalDate.of(2015,1,10))
                .email("info@broker.co.ke").tel("909090").town("Kitale").street("Kenyatta St.").build();

        Intermediary intm2 = new Intermediary.IntermediaryBuilder("Joseph Bonzo","A909909098J",IntermediaryType.AGENT,LocalDate.of(2005,5,6))
                .email("jbonzo@gmail.com").tel("0747387438").town("Machakos").street("Kwathanze").build();

        intRepo.save(intm1);
        intRepo.save(intm2);


        ServiceProvider sp1 = new ServiceProvider.ServiceProviderBuilder("The Nairobi Hospital")
                .email("info@nairobihosp.com").town("Nairobi").tel("078778777").build();

        ServiceProvider sp2 = new ServiceProvider.ServiceProviderBuilder("The Aga Khan University Hospital")
                .email("info@akuh.com")
                .town("Nairobi")
                .tel("0263633477").build();

        ServiceProvider sp3 = new ServiceProvider.ServiceProviderBuilder("The Mater Hospital")
                .email("info@themater.com")
                .town("Nairobi")
                .tel("088733477").build();

        spRepo.save(sp1);
        spRepo.save(sp2);
        spRepo.save(sp3);

        Corporate corp1 = corpRepo.findOne(Long.valueOf(1));
        Corporate corp2 = corpRepo.findOne(Long.valueOf(2));

        CorpAnniv ca1 = new CorpAnniv.CorpAnnivBuilder(1,LocalDate.of(2016,5,1),corp1)
                .expiry(LocalDate.of(2016, 5, 1).plusYears(1).minusDays(1)).renewal(LocalDate.of(2016, 5, 1).plusYears(1))
                .intermediary(intm2).lastUpdate(LocalDateTime.now()).build();

        CorpAnniv ca2 = new CorpAnniv.CorpAnnivBuilder(1,LocalDate.of(2016,3,1),corp2)
                .expiry(LocalDate.of(2016, 3, 1).plusYears(1).minusDays(1)).renewal(LocalDate.of(2016, 3, 1).plusYears(1))
                .intermediary(intm2).lastUpdate(LocalDateTime.now()).build();

        CorpAnniv ca3 = new CorpAnniv.CorpAnnivBuilder(2,LocalDate.of(2017, 5, 1),corp1)
                .expiry(LocalDate.of(2017, 5, 1).plusYears(1).minusDays(1)).renewal(LocalDate.of(2017, 5, 1).plusYears(1))
                .intermediary(intm1).lastUpdate(LocalDateTime.now()).build();

        corpAnnivRepo.save(ca1);
        corpAnnivRepo.save(ca2);
        corpAnnivRepo.save(ca3);

//        Principal p1 = new Principal();
//        p1.setCorporate(corpRepo.findOne(1L));
//        p1.setFamilyNo("KBC/0001");
//        p1.setFirstName("Joel");
//        p1.setSurname("Mukunya");
//        p1.setOtherNames("Kunye");
//        principalRepo.save(p1);


    }
}


