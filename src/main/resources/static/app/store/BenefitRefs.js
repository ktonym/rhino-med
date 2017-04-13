/**
 * Created by user on 15/03/2017.
 */
Ext.define('Rhino.store.BenefitRefs',{
    extend: 'Ext.data.Store',
    alias: 'store.benefit-refs',
    requires: ['Rhino.model.uw.BenefitRef'],
    model: 'Rhino.model.uw.BenefitRef',
    autoLoad: false
});