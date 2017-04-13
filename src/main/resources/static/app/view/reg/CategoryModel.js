/**
 * Created by user on 16/02/2017.
 */
Ext.define('Rhino.view.reg.CategoryModel',{
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.category',
    requires: ['Rhino.store.Category','Rhino.store.CategoryBenefit'/*,'Rhino.store.CategoryMember'*/],
    stores: {
        annivs: {
            type: 'corp-anniv',
            session: true
        },
        categories: {
            type: 'category'
        },
        categoryBenefits: {
            type: 'category-benefit'
        }
        /*,categoryMembers: {
            type: 'CategoryMember'
        }*/
    }
});