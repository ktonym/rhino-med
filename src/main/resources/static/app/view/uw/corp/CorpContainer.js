Ext.define('Rhino.view.uw.corp.CorpContainer',{
    extend      : 'Ext.tab.Panel',
    alias       : 'widget.corp-container',
    requires    : ['Rhino.view.uw.corp.CorpList','Rhino.view.uw.corp.CorpAnnivList'],
    // controller  : 'underwriting',
    viewModel   : {
        type: 'corporate'
    },
    activeTab   : 0,
    items       : [
        {
            xtype: 'container',
            closable: false,
            //iconCls: 'fa fa-home fa-lg tabIcon',
            title: 'Schemes',
            layout: { type:  'vbox', align: 'stretch'},
            items: [
                {
                    xtype: 'corplist',
                    flex: 2
                },
                {
                    xtype: 'corp-anniv-list',
                    flex: 1
                }
            ]
        }
    ]
});
