Ext.define('Rhino.view.uw.intermediary.IntermediaryModel',{
    extend: 'Ext.app.ViewModel',
    
    alias: 'viewmodel.intermediary',
    
    stores: {
        intermediaries: {
            model: 'Rhino.model.uw.Intermediary',
            autoLoad: true
        },
        intermediaryTypes: {
            model: 'Rhino.model.TextCombo',
            data: [
                ['AGENT'],['AGENCY'],['BROKER']
            ]
        }
    },

    formulas: {
        selectedIntermediary: {
            bind: {
                bindTo: '{intermediaryList.selection}',
                deep: true
            },
            get: function(intermediary){
                return intermediary;
            },
            set: function(intermediary){
                intermediary = this.set('selectedIntermediary',intermediary);
            }
        }
    }
});