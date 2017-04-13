/**
 * Created by user on 01/03/2017.
 */
Ext.define('Rhino.store.CategoryBenefit',{
    extend: 'Ext.data.Store',
    storeId: 'categoryBenefitStore',
    alias: 'store.category-benefit',
    requires: ['Rhino.model.uw.CorpBenefit'],
    model: 'Rhino.model.uw.CorpBenefit',
    proxy: {
        type: 'ajax',
        url: 'uw/corpbenefit/findByCategory.json',
        reader: {
            type: 'json',
            rootProperty: 'data'
        }
    },
    doLoadByCategory: function (idCategory) {
        this.load({
            params: {
                idCategory: idCategory
            }
        });
    }
});


//idCorpAnniv property is needed in order to load /uw/corpbenefit/findAll.json
//TODO investigate