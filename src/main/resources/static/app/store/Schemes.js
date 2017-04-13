/**
 * Created by user on 08/03/2017.
 */
Ext.define('Rhino.store.Schemes',{
    extend: 'Ext.data.Store',
    alias: 'store.schemes',
    requires: ['Rhino.model.uw.Corporate'],
    model: 'Rhino.model.uw.Corporate'
});