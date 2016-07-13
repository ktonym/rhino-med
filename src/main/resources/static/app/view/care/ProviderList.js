Ext.define('Rhino.view.care.ProviderList',{
    extend: 'Ext.grid.Panel',
    
    alias: 'widget.provider-list',

    requires: ['Rhino.model.care.Admission'],
    
    cls: 'email-inbox-panel shadow-panel',
    
    viewModel: {
        type: 'care'
    },
    
    bind: {
        store: '{providers}'
    },
    
    viewConfig: {
        preserveScrollOnRefresh: true,
        preserveScrollOnReload: true
    },
    
    listeners: {
        cellclick: 'onGridCellItemClick'
    },
    
    headerBorders: false,
    rowLines: false,
    
    columns: [
        {
            dataIndex: 'idProvider',
            text: 'Provider ID',
            width: 60
        },
        {
            dataIndex: 'providerName',
            text: 'Name',
            flex: 2
        },
        {
            dataIndex: 'providerType',
            text: 'Type',
            flex: 1
        },
        {
            dataIndex: 'email',
            text: 'Email',
            flex: 1
        },
        {
            dataIndex: 'tel',
            text: 'Tel',
            flex: 1
        },
        {
            dataIndex: 'town',
            text: 'Town',
            flex: 1
        }
    ]
    
    
});