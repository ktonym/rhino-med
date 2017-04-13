/**
 * Created by akipkoech on 26/10/2016.
 */
Ext.define('Rhino.view.reg.SchemeModel',{
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.scheme',
    requires: ['Rhino.model.uw.Corporate','Rhino.model.uw.Intermediary',
        /*'Rhino.model.uw.Category','Rhino.model.uw.CorpBenefit',*/
        'Rhino.store.Category','Rhino.store.CategoryBenefit'],
    data: {},
    formulas: {
        currentScheme: {
            bind: {
                bindTo: '{schemeList.selection}',
                deep: true
            },
            get: function (scheme) {
                this.set('current.scheme', scheme);
                return scheme;
            }
        },
        currentAnniv: {
            bind: {
                bindTo: '{schemeAnnivList.selection}',
                deep: true
            },
            get: function (anniv) {
                this.set('current.anniv', anniv);
                return anniv;
            }
        },
        currentCategory: {
            bind: {
                bindTo: '{categoryList.selection}',
                deep: true
            },
            get: function (category) {
                this.set('current.category',category);
                return category;
            }
        }
    },
    stores: {
        schemes: {
            model: 'Rhino.model.uw.Corporate',
            autoLoad: true
        },
        anniversaries: {
            model: 'Rhino.model.uw.CorpAnniv',
            autoLoad: false,
            loadByCorporate: function(idCorp){
                this.load({
                    params: {
                        idCorporate: idCorp
                    }
                });
            }
        },
        intermediaries: {
            model: 'Rhino.model.uw.Intermediary',
            autoLoad: true
        },
        categories: {
            type: 'category'
        },
        categoryBenefits: {
            /*model: 'Rhino.model.uw.CorpBenefit',
            autoLoad: false,
            loadByCategory: function (idCategory) {
                this.load({
                    params: {
                        idCategory: idCategory
                    }
                });
            }*/
            type: 'category-benefit'
        }/*,
        planTypes: {
            model: 'Rhino.model.TextCombo',
            data: [
                ['CORPORATE'],['INDIVIDUAL'],['SME']
            ]
        }*/
    }
});