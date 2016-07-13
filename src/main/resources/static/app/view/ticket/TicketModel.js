Ext.define('Rhino.view.ticket.TicketModel',{
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.ticket',
    stores: {
        tickets: {
            // TODO create proxy to load tickets from server
            model: 'Rhino.model.ticket.Ticket',
            data: {
                tickets: [
                    {"id":1, "name": "createAnniv", "assignee":"kip", "description":"Define anniversary","createdTime":"20160126", "dueDate":"20160131", "suspended":false, "recordId":1, "module":"anniv-form" },
                    {"id":2, "name": "createAnnivTask", "assignee":"kip", "description":"Define anniversary", "createdTime":"20160126", "dueDate":"20160131", "suspended":false, "recordId":2, "module":"anniv-form" },
                    {"id":3, "name": "createAnnivTask", "assignee":"joe", "description":"Define anniversary","createdTime":"20160126", "dueDate":"20160131", "suspended":false, "recordId":3, "module":"anniv-form" },
                    {"id":4, "name": "", "assignee":"mike", "description":"Define anniversary","createdTime":"20160126", "dueDate":"20160131", "suspended":false, "recordId":3, "module":"anniv-form" }
                ]
            },
            proxy: {
                type: 'memory',
                reader: {
                    type: 'json',
                    rootProperty: 'tickets'
                }
            },
            autoLoad: true
        }
    },
    
    formulas: {
        currentTicket: {
            bind: {
                bindTo: '{ticketlist.selection}',
                deep: true
            },
            get: function(ticket){
                return ticket;
            },
            set: function(ticket){
                ticket = this.set('currentTicket', ticket);
            }
        }
    }
});