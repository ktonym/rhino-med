Ext.define('Rhino.view.uw.corp.CorpContainer',{
    extend      : 'Ext.tab.Panel',
    alias       : 'widget.corp-container',
    requires    : ['Rhino.view.uw.corp.CorpList'],
    controller  : 'corporate',
    viewModel   : {
        type: 'corporate'
    },
    activeTab   : 0,
    items       : [
        {
            xtype: 'corplist',
            closable: false,
            iconCls: 'fa fa-home fa-lg tabIcon',
            title: 'Corporate Plans'
        }
    ]
});
