/**
 * Created by akipkoech on 26/10/2016.
 */
Ext.define('Rhino.view.reg.RegMenu',{
    extend: 'Ext.menu.Menu',
    alias: 'widget.regmenu',
    title: 'Registration',
    //iconCls: 'x-fa fa-book',
    floating: false,
    items: [
        {
            routeId: 'scheme-list',
            text: 'Schemes',
            iconCls: 'x-fa fa-building'
        }
    ]
});