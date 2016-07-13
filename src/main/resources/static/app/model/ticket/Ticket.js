Ext.define('Rhino.model.ticket.Ticket',{
    extend: 'Rhino.model.Base',
    entityName: 'Ticket',
    //idProperty: 'id',
    //TODO handle custom identifiers
    identifier: 'custom',
    fields: [
        { name: 'id', type: 'int', useNull: true},
        { name: 'name', type: 'string'},
        { name: 'assignee', type: 'string'},
        { name: 'description', type: 'string'},
        { name: 'createdTime', type: 'date', dateFormat: 'Ymd'},
        { name: 'dueDate', type: 'date', dateFormat: 'Ymd' },
        { name: 'suspended', type: 'boolean' }
    ]
});