Ext.define('Rhino.view.care.AdmissionMenu',{
    extend: 'Ext.menu.Menu',
    
    alias: 'widget.admissionmenu',
    
    title: 'Admissions',
    
    iconCls: 'x-fa fa-hospital',
    
    floating: false,
    
    items: [
        {
            routeId: 'admission-form',
            params: {
                openWindow: true,
                targetCfg: {
                    
                },
                windowCfg: {
                    title: 'Pre-Admission Authorization',
                    minWidth: 450,
                    minHeight: 500
                }
            },
            iconCls: 'x-fa fa-add',
            text: 'New'
        },
        {
            routeId: 'visits', //TODO create this widget
            text: 'Hospital Visits',
            iconCls: 'x-fa fa-database'
        },
        {
            routeId: 'admission-list',
            text: 'Pre-Authorizations',
            iconCls: 'x-fa fa-list-alt'
        }
    ]
});