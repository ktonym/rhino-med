Ext.define('Rhino.fields.MemberType',{
    extend: 'Ext.data.field.String',
    alias: 'data.field.member-type',
    validators: {
        type: 'inclusion',
        list: [ 'PRINCIPAL','SPOUSE','CHILD','PARENT','GRANDPARENT','OTHER' ]
    }
});