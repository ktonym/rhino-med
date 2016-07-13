Ext.define('Rhino.fields.Gender', {
    extend: 'Ext.data.field.String',
    alias: 'data.field.gender',
    validators: {
        type: 'inclusion',
        list: [ 'female', 'male' ]
    }
});
