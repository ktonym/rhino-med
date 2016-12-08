Ext.define('Rhino.fields.Pin',{
    extend: 'Ext.data.field.String',
    alias: 'data.field.pin',
    validators: {
        type: 'format',
        matcher: /^(A|P)\d{9}[A-Z]$/,
        message: 'PIN must be in the format A123456789A'
    }
});
