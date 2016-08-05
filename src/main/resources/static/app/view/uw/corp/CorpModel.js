Ext.define('Rhino.view.uw.corp.CorpModel',{
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.corporate',
    requires: ['Rhino.model.uw.CorpAnniv','Rhino.model.uw.Principal'],
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
        },

        principals: {
            model: 'Rhino.model.uw.Principal',
            autoLoad: false
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
        },
        selectedPrincipal: {
            bind: {
                bindTo: '{corpPrincipalList.selection}',
                deep: true
            },
            get: function(principal){
                return principal;
            },
            set: function(principal){
                principal = this.set('selectedPrincipal',principal);
            }
        },
        selectedMember: {
            bind: {
                bindTo: '{corpMembers.selection}',
                deep: true
            },
            get: function(member){
                return member;
            },
            set: function(member){
                member = this.set('selectedMember',member);
            }
        }
    }
    
});