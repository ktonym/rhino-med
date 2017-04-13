/**
 * Created by user on 04/03/2017.
 */
Ext.define('Rhino.view.reg.SchemeAnnivModel',{
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.scheme-anniv',
    requires: ['Rhino.store.CorpAnniv'],
    //data: {},
    // stores: {
    //     terms: {
    //         type: 'corp-anniv',
    //         session: true // trying to see
    //     }
    // },
    formulas: {
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
    }
});