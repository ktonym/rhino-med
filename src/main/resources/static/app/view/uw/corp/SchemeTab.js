
Ext.define('Rhino.view.uw.corp.SchemeTab',{
    extend:  'Ext.tab.Panel',
    alias:   'widget.scheme-tab',
    requires: ['Rhino.view.uw.corp.CorpAnnivList','Rhino.view.uw.corp.CorpMembers'],
    viewModel: {
        type: 'corp'
    },
    cls: 'shadow-panel',
    activeTab: 0,
    items: [
        {
            xtype: 'corp-anniv-list',
            closable: false
        },
        {
            xtype: 'corp-members'
        }
    ]

});