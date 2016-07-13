Ext.define('Rhino.view.ticket.TicketMenu',{
    extend: 'Ext.menu.Menu',
    alias: 'widget.ticket-menu',
    
    //viewModel: {
    //    type: 'ticket'
    //},
    
    title: 'Tickets',
    
    iconCls: 'x-fa fa-list',
    
    floating: true,
    
    items: [
        {
            routeId: 'ticket-list',
            iconCls: 'x-fa fa-add',
            text: 'Tasks'
        }
        
    ]
    
});