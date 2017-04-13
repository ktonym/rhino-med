/**
 * Created by user on 06/04/2017.
 */
Ext.define('Rhino.fields.RateType',{
    extend: 'Ext.data.field.String',
    alias: 'data.field.ratetype',
    validators: {
        type: 'inclusion',
        list: [ 'GROUP','INDIVIDUAL','STANDARD']
    }
});