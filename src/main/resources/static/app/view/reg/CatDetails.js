/**
 * Created by akipkoech on 10/11/2016.
 */
Ext.define('Rhino.view.reg.CatDetails',{
    extend: 'Ext.tab.Panel',
    alias: 'widget.cat-details',
    requires: [],
    activeTab: 0,
    items: [
        {
            title: 'Categories',
            xtype: 'panel'
        },
        {
            title: 'Benefits',
            xtype: 'panel'
        }
    ]
});