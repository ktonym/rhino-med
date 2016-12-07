/**
 * Created by akipkoech on 26/10/2016.
 */
Ext.define('Rhino.view.reg.SchemeModel',{
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.scheme',
    requires: ['Rhino.model.uw.Corporate'],
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
            model: 'Rhino.model.uw.Category',
            autoLoad: false,
            loadByCorpAnniv: function (idCorpAnniv) {
                this.load({
                    params: {
                        idCorpAnniv: idCorpAnniv
                    }
                });
            }
        }
    }
});