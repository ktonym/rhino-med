Ext.define('Rhino.view.BaseController',{
   extend: 'Ext.app.ViewController',
    alias: 'controller.base',
    requires: ['Rhino.util.Util'],
    
    setCurrentView: function(view,params){
        var contentPanel = this.getView().down('#contentPanel');
        
        //We skip rendering for the following scenarios:
        // * There is no contentPanel
        // * view xtype is not specified
        // * current view is the same
        if(!contentPanel || view === '' || (contentPanel.down() && contentPanel.down().xtype === view)){
            return false;
        }
        
        if (params && params.openWindow){
            var cfg = Ext.apply({
                xtype: 'uwwindow',
                items: [
                    Ext.apply({
                        xtype: view
                    }, params.targetCfg)
                ]
            }, params.windowCfg);
            
            Ext.create(cfg);
        } else {
            Ext.suspendLayouts();
            
            contentPanel.removeAll(true);
            contentPanel.add(
                Ext.apply({
                    xtype: view
                }, params)
            );
            
            Ext.resumeLayouts(true);
            
        }
        
    },
    
    onMenuClick: function(menu, item){
        if(item){
           this.setCurrentView(item.routeId, item.params); 
        }
    }

});
