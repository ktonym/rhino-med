/**
 * Created by akipkoech on 31/10/2016.
 */
Ext.define('Rhino.store.ProviderStore',{
    extend: 'Ext.data.Store',
    requires: ['Rhino.model.care.Provider'],
    model: 'Rhino.model.care.Provider'
});