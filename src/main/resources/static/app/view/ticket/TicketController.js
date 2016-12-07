Ext.define('Rhino.view.ticket.TicketController',{
    extend: 'Ext.app.ViewController',
    alias: 'controller.ticket',
    mixins: ['Rhino.util.ControllerMixin'],
    
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