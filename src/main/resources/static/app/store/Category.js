/**
 * Created by user on 16/02/2017.
 */
Ext.define('Rhino.store.Category',{
    extend: 'Ext.data.Store',
    storeId: 'categoryStore',
    alias: 'store.category',
    requires: ['Rhino.model.uw.Category'],
    model: 'Rhino.model.uw.Category',
    doLoadByAnniv: function (idCorpAnniv) {
        this.load({
            params: {
                idCorpAnniv: idCorpAnniv
            }
        });
    }
});