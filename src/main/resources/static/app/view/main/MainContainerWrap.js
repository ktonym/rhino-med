Ext.define('Rhino.view.main.MainContainerWrap',{
    extend: 'Ext.container.Container',
    xtype: 'maincontainerwrap',
    
    requires: [
        'Ext.layout.container.HBox'
    ],
    
    scrollable: 'y',
    
    layout: {
        type: 'hbox',
        align: 'stretchmax',
        
        // Tell the layout to animate the x/width of the child items
        animate: true,
        animatePolicy: {
            x:  true,
            width: true
        }
    },
           
    beforeLayout: function(){
        
        var me = this,
            height= Ext.Element.getViewportHeight() - 64, // offset by topmost toolbar height
            //Use itemId/getComponent instead of "reference" because the initial layout occurs too early for the reference to be resolved
            navTree = me.getComponent('navigationTreeList');
    
        me.minHeight = height;
        navTree.setStyle({
            'min-height': height + 'px'
        });
    
        me.callParent(arguments);
    }
    
});