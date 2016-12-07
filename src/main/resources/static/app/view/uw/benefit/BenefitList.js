Ext.define('Rhino.view.uw.benefit.BenefitList',{
    extend: 'Ext.grid.Panel',
    alias: 'widget.benefit-ref-list',
    reference: 'benefitRefList',
    controller: 'benefit-ref',
    viewModel: {
        type: 'benefit-ref'
    },
    session: true,
    bind: '{benefitRefs}',
    cls: 'email-inbox-panel shadow-panel',
    title: 'Benefit Definitions',
    tbar: [
        {
            iconCls: 'x-fa fa-plus',
            listeners: {
                click: 'onAddBtnClick'
            }
        },
        {
            iconCls: 'x-fa fa-edit',
            listeners: {
                click: 'onEditBtnClick'
            },
            bind: {
                disabled: '{!benefitRefList.selection}'
            }
        },
        {
            iconCls: 'x-fa fa-minus',
            listeners: {
                click: 'onDelBtnClick'
            },
            bind: {
                disabled: '{!benefitRefList.selection}'
            }
        }
    ],
    columns: [
        {
            dataIndex: 'benefitCode',
            text: '#',
            width: 50
        },
        {
            dataIndex: 'benefitName',
            text: 'Name',
            flex: 1
        },
        {
            dataIndex: 'description',
            text: 'Description',
            flex: 2
        }
    ]
});