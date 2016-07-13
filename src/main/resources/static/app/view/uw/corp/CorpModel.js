Ext.define('Rhino.view.uw.corp.CorpModel',{
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.corporate',
    
    stores: {
        corporates: {
            model: 'Rhino.model.uw.Corporate',
            pageSize: 3,
            autoLoad: true
        },

        anniversaries: {
            model: 'Rhino.model.uw.CorpAnniv',
            //proxy: {
            //    extraParams: {
            //        idCorporate : ''
            //    }
            //},
            autoLoad: false
            //,remoteFilter: true,
            //filters: [{
            //    property: 'idCorporate',
            //    value: '{idCorporateFilter}'
            //}]
        }
    },
    
    formulas: {
        selectedCorporate: {
            bind: {
                bindTo: '{corpList.selection}',
                deep: true
            },
            get: function (corporate) {
                return corporate;
            },
            set: function (corporate) {
                corporate = this.set('selectedCorporate', corporate);
            }
        },
        selectedAnniversary: {
            bind: {
                bindTo: '{corpAnnivList.selection}',
                deep: true
            },
            get: function(anniv){
                return anniv;
            },
            set: function(anniv){
                anniv = this.set('selectedAnniversary',anniv);
            }
        }
    }
    
});