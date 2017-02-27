/**
 * Created by user on 24/02/2017.
 */
Ext.define('Rhino.fields.PlanType',{
    extend: 'Ext.data.field.String',
    alias: 'data.field.plan',
    validators: {
        type: 'inclusion',
        list: [ 'CORPORATE','INDIVIDUAL','SME' ]
    }
});