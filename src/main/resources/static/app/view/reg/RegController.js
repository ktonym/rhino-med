/**
 * Created by akipkoech on 26/10/2016.
 */
Ext.define('Rhino.view.reg.RegController',{
    extend: 'Ext.app.ViewController',
    alias: 'controller.registration',
    // requires: ['Rhino.util.Util'],
    mixins: ['Rhino.util.ControllerMixin'],

    init: function(){
        this.setCurrentView('scheme-list');
        //this.getViewModel().getStore('categories').on('add', this.afterAddCategory,this);

        this.listen({
            controller: {
                '*' : {
                    memberadded: 'afterMemberAdded',
                    memberupdated: 'afterMemberUpdated',
                    categoryadded: 'afterCategoryAdded',
                    catbenefitadded: 'afterCatBenefitAdded'
                }
            }
        });

    },

    onMenuClick: function(menu, item){
        if(item){
            this.setCurrentView(item.routeId, item.params);
        }
    },

    onBackHome: function () {
        this.setCurrentView('scheme-list');
    },

    onAnnivsClick: function () {
        this.setCurrentView('scheme-anniv-list');
    },

    onSchemeMembersClick: function () {
        this.setCurrentView('scheme-members');
    },

    onSchemeAdd: function () {
        var me = this,
            vm = me.getViewModel(),
            scheme = Ext.create('Rhino.model.uw.Corporate',{
                        planType: 'CORPORATE'
            });
        vm.set('current.scheme',scheme);
        this.setCurrentView('scheme-form',{
            openWindow: true,
            windowCfg: {
                header : false,
                maxHeight: 450,
                maxWidth: 500
            },
            targetCfg: {
                title: 'Create scheme',
                iconCls: 'x-fa fa-plus'
            }
        });
    },

    onSchemeEdit: function () {
        this.setCurrentView('scheme-form',{
            openWindow: true,
            windowCfg: {
                header : false,
                maxHeight: 450,
                maxWidth: 500
            },
            targetCfg: {
                bind: {
                    title: 'Edit: <b>{current.scheme.name}</b>'
                },
                iconCls: 'x-fa fa-edit'
            }
        });
    },

    onAnnivAdd: function () {
        var me = this,
            vm = me.getViewModel(),
            idCorp = vm.get('current.scheme.id'),
            anniv;
        anniv = Ext.create('Rhino.model.uw.CorpAnniv',{
            idCorporate: idCorp
        });
        vm.set('current.anniv',anniv);
        this.setCurrentView('anniv-form',{
            openWindow: true,
            windowCfg: {
                header : false,
                maxHeight: 350,
                maxWidth: 450
            },
            targetCfg: {
                bind: {
                    title: 'Create anniversary for <b>{current.scheme.name}</b>'
                },
                iconCls: 'x-fa fa-plus'
            }
        });

    },
    onAnnivEdit: function () {
        this.setCurrentView('anniv-form',{
            openWindow: true,
            windowCfg: {
                header : false,
                maxHeight: 350,
                maxWidth: 450
            },
            targetCfg: {
                bind: {
                    title: 'Edit: <b>{current.scheme.name}</b> (Policy Term {current.anniv.anniv})'
                },
                iconCls: 'x-fa fa-edit'
            }
        });
    },
    onCatDetailsClick: function () {
        this.setCurrentView('cat-details',{
            openWindow: false,
            windowCfg: {
                header: false,
                closable: true
            },
            targetCfg: {
                bind: {
                    // title: 'Categories for <b>{current.scheme.name}</b> ({current.anniv.anniv})'
                }
            }
        });
    },
    onPolicyMembersClick: function () {
        this.setCurrentView('policy-member-list',{
            openWindow: false,
            windowCfg: {
                header: false,
                closable: true
            },
            targetCfg: {
                bind: {
                    title: '<b>{current.scheme.name}</b> ({current.anniv.anniv}) Members'
                }
            }
        });
    },
    onAddPrincipal: function () {
        var me = this,
            vm = me.getViewModel(),
            idCorp = vm.get('current.scheme.id'),
            principal;
        principal = Ext.create('Rhino.model.uw.Member',{
            idCorporate: idCorp
        });
        vm.set('current.principal',principal);
        this.setCurrentView('member-form',{
            openWindow: true,
            windowCfg: {
                header : false,
                maxHeight: 450,
                maxWidth: 500
            },
            targetCfg: {
                bind: {
                    title: 'Add Principal to <b>{current.scheme.name}</b>'
                }
            }
        });
    },
    onEditPrincipal: function () {
        this.setCurrentView('principal-form',{
            openWindow: true,
            windowCfg: {
                header : false,
                maxHeight: 450,
                maxWidth: 500
            },
            targetCfg: {
                bind: {
                    title: 'Edit: <b>{current.principal.surname}</b>'
                }
            }
        });
    },
    onAddMember: function () {
        var me = this,
            vm = me.getViewModel(),
            //idPrincipal = vm.get('current.principal.idPrincipal'),
            idCorp = vm.get('current.scheme.id'),
            member = Ext.create('Rhino.model.uw.Member',{
                idCorporate: idCorp
            });
        vm.set('current.member',member);
        this.setCurrentView('member-form',{
            openWindow: true,
            windowCfg: {
                header : false,
                maxHeight: 450,
                maxWidth: 500
            },
            targetCfg: {
                bind: {
                    title: 'Add Member to <b>{current.scheme.name}</b>'
                },
                iconCls: 'x-fa fa-plus',
                listeners: {
                    memberadded: 'onMemberAdded'
                }
            }
        });

    },
    onEditMember: function () {
        this.setCurrentView('member-form',{
            openWindow: true,
            windowCfg: {
                header : false,
                maxHeight: 450,
                maxWidth: 500
            },
            targetCfg: {
                bind: {
                    title: 'Edit: <b>{current.member.surname}</b>'
                }
            }
        });
    },

    afterMemberAdded: function (rec) {
       /* console.info('Managing the new record!');
        debugger;*/
        var memberlist = this.getView().lookupReference('schemeMembers'),
            store = memberlist.getStore();
        store.add(rec);
    },

    afterMemberUpdated: function (rec) {
         var memberlist = this.getView().lookupReference('schemeMembers'),
             store = memberlist.getStore();
         console.info('Managing an updated record!!');
         debugger;
         store.update(rec);
    },

    onAddCategory: function () {
        var me = this,
            contPan = me.getView().down('#contentPanel'),
            grid = contPan.down('category-list'),win,
            vm = me.getViewModel(),
            corp = vm.get('current.scheme'),
            annivId = vm.get('current.anniv.idCorpAnniv'),
            rec = Ext.create('Rhino.model.uw.Category',{
                idCorpAnniv: annivId
            });
        vm.set('current.category',rec);

        this.setCurrentView('category-form',{
            openWindow: true,
            windowCfg: {
                header : false,
                maxHeight: 260,
                maxWidth: 500
            },
            targetCfg: {
                bind: {
                    title: 'Add Category to <b>{current.scheme.name}</b> ({current.anniv.anniv})'
                },
                iconCls: 'x-fa fa-plus',
                listeners: {
                    memberadded: 'onMemberAdded'
                }
            }
        });

    },

    onEditCategory: function () {
        this.setCurrentView('category-form',{
            openWindow: true,
            windowCfg: {
                header : false,
                maxHeight: 260,
                maxWidth: 500
            },
            targetCfg: {
                bind: {
                    title: 'Edit: <b>{current.category.cat}</b>'
                }
            }
        });
    },

    afterCategoryAdded: function (rec) {
        Ext.Msg.alert('Category Added!','A new category was added');
        var catList = this.getView().lookupReference('categoryList'),
            store = catList.getStore();
        store.add(rec);
    },

    onSearchToAddMember: function () {

    },

    onAddCategoryBenefit: function () {
         var me = this,
             vm = me.getViewModel(),
             //store = vm.getStore('categoryBenefits'),
             idc = vm.get('current.category.idCategory'),
             catBen = Ext.create('Rhino.model.uw.CorpBenefit',{
                 idCategory: idc
             });
        vm.set('current.category-benefit',catBen);
        this.setCurrentView('category-benefit-form',{
            openWindow: true,
            windowCfg: {
                header : false,
                maxHeight: 450,
                maxWidth: 500
            },
            targetCfg: {
                bind: {
                    title: 'Add Benefit to <b>{current.category.cat}</b>'
                }
            }
        });
    },
    afterCatBenefitAdded: function (rec) {
        var catBenList = this.getView().lookupReference('categoryBenefitList'),
            store = catBenList.getStore();
        store.add(rec);
    },
    onMemberPolicyAdd: function () {
        var me = this,
            vm = me.getViewModel(),
            vw = me.getView(),
            corpAnniv = vm.get('current.anniv'),
            win, schemeId,annivId,store;
        this.setCurrentView('policy-member-grid',{
            openWindow: true,
            windowCfg: {
                header : false,
                maxHeight: 450,
                maxWidth: 500
            },
            targetCfg: {
                bind: {
                    title: 'Add Member to <b>{current.anniv.anniv}</b>'
                }
            }
        });
    }
});