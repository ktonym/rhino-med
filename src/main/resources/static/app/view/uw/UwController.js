Ext.define('Rhino.view.uw.UwController',{
    extend: 'Rhino.view.BaseController',
    alias: 'controller.underwriting',

    init: function(){
        this.setCurrentView('corplist');
    },
    
    onBackBtnClick: function(){
        this.setCurrentView('corplist');
    }

});