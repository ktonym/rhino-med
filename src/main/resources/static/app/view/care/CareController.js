Ext.define('Rhino.view.care.Controller',{
    extend: 'Ext.app.ViewController',
    alias: 'controller.care',
    mixins: ['Rhino.util.ControllerMixin'],
    
    init: function(){
        this.setCurrentView('admission-list');
    },

    onGridCellItemClick: function () {
        console.log("Grid cell clicked.");
    }
    
});