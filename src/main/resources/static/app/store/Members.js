/**
 * Created by user on 18/02/2017.
 */
Ext.define('Rhino.store.Members',{
    extend: 'Ext.data.Store',
    alias: 'store.members',
    requires: ['Rhino.model.uw.Member'],
    model: 'Rhino.model.uw.Member',
    proxy: {
        type: 'ajax',
        url: '/uw/member/tree',
        /*extraParams: {
            idCorporate: '{current.scheme.id}'
        },*/
        reader: {
            type: 'json',
            rootProperty: 'members',
            totalProperty: 'results'
        }
    },
    loadByCorporate: function (corpId) {
        this.load({
            params: {
                //filter: "corporate",
                idCorporate: corpId
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
                //filter: "corpAnniv",
                idCorpAnniv: idCorpAnniv
            }
        });
    }
});