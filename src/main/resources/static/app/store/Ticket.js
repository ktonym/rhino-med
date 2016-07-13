Ext.define('Rhino.store.Ticket',{
    extend: 'Ext.data.Store',
    model: 'ticket.Ticket',
    remoteFilter: true,//needed if paging will be used
    remoteSort: true,  //needed if paging will be used
    proxy: {
        type: 'rest',
        url: '/activiti/tasks',
        reader: {
            type: 'json',
            rootProperty: 'data',
            totalProperty: 'count'
        },
        writer: {
            type: 'json'
        }
    }
});