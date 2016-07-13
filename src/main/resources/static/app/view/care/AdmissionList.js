Ext.define('Rhino.view.care.AdmissionList',{
    extend: 'Ext.grid.Panel',
    
    alias: 'widget.admission-list',

    requires: ['Rhino.model.care.Admission'],
    
    cls: 'email-inbox-panel shadow-panel',
    
    viewModel: {
        type: 'care'
    },
    
    bind: {
        store: '{admissions}'    
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
            dataIndex: 'id',
            text: 'Pre-auth ID',
            width: 60
        },
        {
            dataIndex: 'memberNo',
            text: 'Member No.',
            flex: 1
        },
        {
            dataIndex: 'fullName',
            text: 'Name',
            flex: 2
        },
        {
            dataIndex: 'provider',
            text: 'Hospital',
            flex: 1
        },
        {
            dataIndex: 'diagnosis',
            text: 'Diagnosis',
            flex: 1
        },
        {
            dataIndex: 'admissionDate',
            text: 'Admission Date',
            flex: 1
        }
    ]
    
    
});