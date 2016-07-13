Ext.define('Rhino.view.ticket.TicketController',{
    extend: 'Rhino.view.BaseController',
    alias: 'controller.ticket',
    
    init: function(){
        this.setCurrentView('ticket-list');
    },
    
    onReassignTicket: function(){
        console.log("Reassigning ticket..");
    },
    
    onProcessTicket: function(){
        console.log("Processing ticket...");
    }
    
});