/**
 * Created by akipkoech on 31/10/2016.
 */
Ext.define('Rhino.store.Intermediary',{
    extend: 'Ext.data.Store',
    storeId: 'intermediaryStore',
    alias: 'store.intermediary',
    requires: ['Rhino.model.uw.Intermediary'],
    model: 'Rhino.model.uw.Intermediary'
});