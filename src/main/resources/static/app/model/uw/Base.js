Ext.define('Rhino.model.uw.Base',{
    extend: 'Ext.data.Model',
    fields: [{ name: 'lastUpdate', type: 'date', dateFormat: 'YmdHi'}],
    identifier: 'custom',
    schema: {
        id: 'uwSchema',
        namespace: 'Rhino.model.uw',
        urlPrefix: 'uw',
        proxy: {
            type: 'ajax',
            api : {
                create: '{prefix}/{entityName:lowercase}/create.json',
                read: '{prefix}/{entityName:lowercase}/findAll.json',
                update: '{prefix}/{entityName:lowercase}/update.json',
                destroy: '{prefix}/{entityName:lowercase}/delete.json'
            },
            reader: {
                type: 'json',
                rootProperty: 'data',
                totalProperty: 'results'
            },
            writer: {
                type: 'json',
                writeAllFields: true,
                rootProperty: 'data',
                allowSingle: true,
                encode: true
            },
            listeners: {
                exception: function(proxy, response, operation){
                    Rhino.util.Util.showErrorMsg(response.responseText);
                }
            }
        }
    }
});