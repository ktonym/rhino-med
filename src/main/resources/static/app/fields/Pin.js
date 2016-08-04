Ext.define('Rhino.fields.Pin',{
    extend: 'Ext.data.field.String',
    alias: 'data.field.pin',
    validators: {
        type: 'format',
        matcher: /^(A|P)\d{9}\S{1}$/,
        message: 'PIN must be in the format A123456789A'
    }
});
