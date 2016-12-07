Ext.define('Rhino.view.uw.benefit.BenefitRefModel',{
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.benefit-ref',

    stores: {
        benefitRefs: {
            model: 'Rhino.model.uw.BenefitRef',
            pageSize: 10,
            autoLoad: true,
            session: true
        }
    },
    formulas: {
        selectedBenefit: {
            bind: {
                bindTo: '{benefitRefList.selection}',
                deep: true
            },
            get: function (benefit) {
                return benefit;
            },
            set: function (benefit) {
                benefit = this.set('selectedBenefit', benefit);
            }
        }
    }
});