/**
 * Created by akipkoech on 15/11/2016.
 */
Ext.define('Rhino.view.reg.MemberModel',{
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.member',
    data: {},
    stores: {
        corpPrincipals: {
            model: 'Rhino.model.uw.Principal',
            proxy: {
                type: 'ajax',
                url: '/uw/principal/findAll',
                extraParams: {
                    idCorporate: '{current.scheme.id}'
                },
                reader: {
                    type: 'json',
                    rootProperty: 'data',
                    totalProperty: 'results'
                }
            },
            autoLoad: true,
            loadByCorporate: function (corpId) {
                this.load({
                    params: {
                        idCorporate: corpId
                    }
                });
            }
        },
        principals: {
            model: 'Rhino.model.uw.Principal',
            autoLoad: false,
            loadByCorporate: function (corpId) {
                this.load({
                    params: {
                        filter: "corporate",
                        id: corpId
                    }
                });
            },
            loadByAnniv: function (idCorpAnniv) {
                this.load({
                    params: {
                        filter: "corpAnniv",
                        id: idCorpAnniv
                    }
                });
            }
        },
        members: {
            model: 'Rhino.model.uw.Member',
            autoLoad: false,
            loadByCorporate: function (corpId) {
                this.load({
                    params: {
                        filter: "corporate",
                        id: corpId
                    }
                });
            },
            loadByPrincipal: function (idPrincipal) {
                this.load({
                    params: {
                        filter: "principal",
                        id: idPrincipal
                    }
                });
            },
            loadByCategory: function (catId) {
                this.load({
                   params: {
                       filter: "category",
                       id: catId
                   }
                });
            },
            loadByAnniversary: function (idCorpAnniv) {
                this.load({
                   params: {
                       filter: "corpAnniv",
                       id: idCorpAnniv
                   }
                });
            }
        }
    }
});