/**
 * Created by user on 01/03/2017.
 */
Ext.define('Rhino.store.CategoryMember',{
    extend: 'Ext.data.Store',
    storeId: 'categoryMemberStore',
    alias: 'store.category-member',
    requires: ['Rhino.model.uw.Member'],
    model: 'Rhino.model.uw.Member'
});