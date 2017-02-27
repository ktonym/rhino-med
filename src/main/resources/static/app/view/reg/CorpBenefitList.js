/**
 * Created by user on 14/02/2017.
 */
Ext.define('Rhino.view.reg.CorpBenefitList',{
    extend: 'Ext.grid.Panel',
    alias: 'widget.category-benefit-list',
    reference: 'corpBenefitList',
    bind: {
        selection: '{current.corpBenefit}',
        store: '{corpBenefits}'
    },
    tbar: [
        {
            iconCls: 'x-fa fa-plus',
            text: 'Add',
            listeners: {
                click: 'onAddCorpBenefitBtnClick'
            }
        },
        {
            iconCls: 'x-fa fa-newspaper-o',
            text: 'Details',
            listeners: {
                click: 'onCorpBenefitDetailsClick'
            },
            bind: {
                disabled: '{!corpBenefitList.selection}'
            }
        },
        {
            iconCls: 'x-fa fa-remove',
            text: 'Delete',
            listeners: {
                click: 'onDelCorpBenefitBtnClick'
            },
            bind: {
                disabled: '{!corpBenefitList.selection}'
            }
        }
    ],
    columns: [
        {
            dataIndex: 'idCorpBenefit',
            text: 'ID',
            width: 60
        },
        {
            dataIndex: 'cat',
            text: 'Category',
            width: 100
        },
        {
            dataIndex: 'upperLimit',
            text: 'Limit',
            flex: 1
        },
        {
            dataIndex: 'memberType',
            text: 'Member Type',
            flex: 1
        },
        {
            dataIndex: 'benefitType',
            text: 'Benefit Type',
            flex: 1
        }
    ]
});



