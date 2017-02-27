/**
 * This class is the controller for the main view for the application. It is specified as
 * the "controller" of the Main view class.
 *
 */
Ext.define('Rhino.view.main.MainController', {
    extend: 'Ext.app.ViewController',

    alias: 'controller.main',

    requires: ['Rhino.util.Util'],

    listen : {
        controller : {
            '#' : {
                unmatchedroute : 'onRouteChange'
            }
        }
    },
    
    routes: {
        ':node': 'onRouteChange'
        /*{
            before: 'checkSession',
            action: 'onRouteChange',
            conditions:{
                ':node': '([%a-zA-Z0-9\\.\\-\\_\\s,]+)'
            }
        }*/
    },
    
    setCurrentView: function(hashTag) {
        hashTag = (hashTag||'').toLowerCase();
        
        var me = this, 
            refs = me.getReferences(),
            mainCard = refs.mainCardPanel,
            mainLayout = mainCard.getLayout(),
            navigationList = refs.navigationTreeList,
            viewModel = me.getViewModel(),
            vmData = viewModel.getData(),
            store = navigationList.getStore(),
            node = store.findNode('routeId', hashTag),
            view = node ? node.get('view') : null,
            lastView = vmData.currentView,
            existingItem = mainCard.child('component[routeId=' + hashTag + ']'),
            newView;
        
        if (lastView && lastView.isWindow){
            lastView.destroy();
        }
            
        lastView = mainLayout.getActiveItem();
        
        if(!existingItem){
            newView = Ext.create('Rhino.view.' + (view || 'pages.Error404Window'),{
                hideMode: 'offsets',
                routeId: hashTag
            });
        }
        
        
        if (!newView || !newView.isWindow){
            // we're not adding to the card layout if
            // it already exists, or it's a window
            if (existingItem){
                // activate the view since it exists
                if (existingItem !== lastView){
                    mainLayout.setActiveItem(existingItem);
                }
                newView = existingItem;
            } 
            else {
                // add and make it active
                Ext.suspendLayouts();
                mainLayout.setActiveItem(mainCard.add(newView));
                Ext.resumeLayouts(true);
            }
            
        }
        
        navigationList.setSelection(node);
        
        if (newView.isFocusable(true)){
            newView.focus();
        }
        
        vmData.currentView = newView;
        
    },
    
    onNavigationTreeSelectionChange: function(tree, node){
        if( node && node.get('view')){
            this.redirectTo(node.get("routeId"));
        }
    },
    
    onToggleNavigationSize: function() {
        var me = this,
            refs = me.getReferences(),
            navigationList = refs.navigationTreeList,
            wrapContainer = refs.mainContainerWrap,
            collapsing = !navigationList.getMicro(),
            new_width = collapsing ? 64 : 250;
        
        if (Ext.isIE9m || !Ext.os.is.Desktop){
            Ext.suspendLayouts();
            
            refs.rhinoLogo.setWidth(new_width);
            
            navigationList.setWidth(new_width);
            navigationList.setMicro(collapsing);
            
            Ext.resumeLayouts();
            
            wrapContainer.layout.animatePolicy = wrapContainer.layout.animate = null
            wrapContainer.updateLayout();
        }
        else {
            if(!collapsing){
                // We're expanding..
                navigationList.setMicro(false);
            }
            
            // Start this layout first since it does not require a layout
            refs.rhinoLogo.animate({
                dynamic: true,
                to: {width: new_width}
            });
        
            // Directly adjust the width config and then run the main wrap container layout
            // as the root layout (it and its chidren). This will cause the adjusted size to
            // be flushed to the element and animate to that new size.
            navigationList.width = new_width;
            wrapContainer.updateLayout({isRoot: true});
            // We need to switch to micro mode on the navlist *after* the animation (this
            // allows the "sweep" to leave the item text in place until it is no longer
            // visible.
            if(collapsing){
                navigationList.on({
                    afterlayoutanimation: function(){
                        navigationList.setMicro(true);
                    },
                    single: true
                });
            }            
        } 
    },

    onMainViewRender: function(){
        if (!window.location.hash) {
            this.redirectTo("tickets");
        }
    },
    
    onRouteChange: function(id){
        this.setCurrentView(id)
    },
    
    onSearchRouteChange: function(){
        this.setCurrentView('search');
    },

    onRegRouteChange: function () {
        this.setCurrentView('registration');
    },
    
    onUwRouteChange: function(){
        this.setCurrentView('underwriting');
    },
    
    onClmRouteChange: function(){
        this.setCurrentView('claims');
    },
    
    onFinRouteChange: function(){
        this.setCurrentView('finance');
    },
    
    onCareRouteChange: function(){
        this.setCurrentView('care');
    },
    
    onLogout: function(){
        var me = this;
        Ext.Msg.confirm('End session','Are you sure you want to log out of this application?',
            function (key) {
                if(key === 'yes'){
                    Ext.Ajax.request({
                        url: 'logout',
                        scope: me,
                        success: 'onSuccessfulLogout',
                        failure: 'onFailedLogout'
                    });
                }
            }
        );
    },

    onFailedLogout: function (conn,response,options,eOpts) {
        Rhino.util.Util.showErrorMsg(conn.responseText);
    },

    onSuccessfulLogout: function(conn,response,options,eOpts){
        var result = Rhino.util.Util.decodeJSON(conn.responseText);
        console.log(result);
        if (result.success) {
            sessionStorage.removeItem('RhinoLoggedIn');
            this.getView().destroy();
            Ext.create({
                xtype: 'login'
            });
        } else {
            Rhino.util.Util.showErrorMsg(result.msg);
        }
    },
    
    checkSession: function () {
        var args   = Ext.Array.slice(arguments),
            action = args[args.length - 1];

        Ext.Ajax.request({url:'/session', method: 'GET',
            success: function(conn,res,opts,eOpts){
                var result = Ext.JSON.decode(conn.responseText);
                if(result.success){
                    action.resume();
                }
            }
        });


    }
    
});
