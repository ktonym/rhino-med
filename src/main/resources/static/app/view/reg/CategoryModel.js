/**
 * Created by user on 16/02/2017.
 */
Ext.define('Rhino.view.reg.CategoryModel',{
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.category',
    requires: ['Rhino.store.Category','Rhino.model.uw.CorpBenefit'],
    stores: {
        categories: {
            type: 'Category'
        }
    }
});