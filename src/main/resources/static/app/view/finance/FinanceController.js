Ext.define('Rhino.view.finance.FinanceController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.finance',
    mixins: ['Rhino.util.ControllerMixin'],

    init: function () {
        this.setCurrentView('invoices');
    },

    onMenuClick: function(menu, item){
        if(item){
            this.setCurrentView(item.routeId, item.params);
        }
    }
    
});
