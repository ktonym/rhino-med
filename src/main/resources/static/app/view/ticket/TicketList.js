Ext.define('Rhino.view.ticket.TicketList',{
    extend: 'Ext.grid.Panel',    
    alias: 'widget.ticket-list',
    reference: 'ticketGrid',

    requires: ['Rhino.model.ticket.Ticket'],
    
    cls: 'email-inbox-panel shadow-panel',
    
    viewModel: {
        type: 'ticket'
    },
    
    bind: {
        store: '{tickets}'    
    },
    
    viewConfig: {
        preserveScrollOnRefresh: true,
        preserveScrollOnReload: true
    },
    
    headerBorders: false,
    rowLines: false,
    
    tbar: [
        {
            iconCls: 'x-fa fa-cog',
            text: 'Process',
            listeners: {
                click: 'onProcessTicket'
            },
            bind: {
                disabled: '{!ticketGrid.selection}'
            }
        },
        {
            iconCls: 'x-fa fa-reverse',
            text: 'Reassign',
            listeners: {
                click: 'onReassignTicket'
            },
            bind: {
                disabled: '{!ticketGrid.selection}'
            }
        }
    ],
    
    items: [
        {
            dataIndex: 'suspended',
            menuDisabled: true,
            text: '<span class="x-fa fa-heart"></span>',
            width: 40,
            renderer: function(value){
                return '<span class="x-fa fa-heart'+(value ? '' : '-o') +'"></span>';
            }
        },
        {
            dataIndex: 'id',
            text: 'Ticket No.',
            width: 60
        },
        {
            dataIndex: 'name',
            text: 'Ticket name',
            width: 2
        },
        {
            dataIndex: 'assignee',
            text: 'Assignee',
            flex: 1
        },
        {
            dataIndex: 'description',
            text: 'Details',
            flex: 2
        },
        {
            dataIndex: 'createdTime',
            text: 'Created date',
            flex: 1
        },
        {
            dataIndex: 'dueDate',
            text: 'Due date',
            flex: 1
        },
        {
            dataIndex: 'recordId',
            text: 'Record ID',
            flex: 1
        },
        {
            dataIndex: 'module',
            text: 'Module',
            flex: 1
        }
    ]
});