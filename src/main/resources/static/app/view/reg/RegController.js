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
        // console.log('RegController loaded...');
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
    onMemberDetailsClick: function () {
        this.setCurrentView('panel',{
            openWindow: true,
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

    listen: {
        controller: {
            '*' : {
                memberadded: function (rec) {
                    Ext.Msg.alert('Member added', 'Fire event!!!');
                    // var memberlist = this.getView().lookupReference('schemeMembers'),
                    //     store = memberlist.getStore();
                    // if(rec.store===undefined){
                    //     store.add(rec);
                    //     //vm.get('members').add(rec);
                    // }
                }
            }
        }
    }
});