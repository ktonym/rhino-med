Ext.define('Rhino.view.care.Controller',{
    extend: 'Rhino.view.BaseController',
    alias: 'controller.care',
    
    init: function(){
        this.setCurrentView('admission-list');
    },

    onGridCellItemClick: function () {
        console.log("Grid cell clicked.");
    }
    
});