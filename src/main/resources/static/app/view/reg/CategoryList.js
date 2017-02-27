/**
 * Created by akipkoech on 07/11/2016.
 */
Ext.define('Rhino.view.reg.CategoryList',{
    extend: 'Ext.grid.Panel',
    alias: 'widget.category-list',
    reference: 'categoryList',
    cls: 'email-inbox-panel shadow-panel',
    /*viewModel: {
        type: 'scheme'
    },*/
    bind: {
        store: '{categories}',
        selection: '{current.category}'
    },
    tbar: [
        {
            iconCls: 'x-fa fa-plus',
            text: 'Add',
            listeners: {
                click: 'onAddCategory'
            }
        },
        {
            iconCls: 'x-fa fa-newspaper-o',
            text: 'Details',
            listeners: {
                click: 'onDetailsClick'
            },
            bind: {
                disabled: '{!categoryList.selection}'
            }
        },
        {
            iconCls: 'x-fa fa-remove',
            text: 'Delete',
            listeners: {
                click: 'onDelCatBtnClick'
            },
            bind: {
                disabled: '{!categoryList.selection}'
            }
        }
    ],
    columns: [
        {
            dataIndex: 'idCategory',
            text: 'ID',
            width: 60
        },
        {
            dataIndex: 'cat',
            text: 'Category'
        },
        {
            dataIndex: 'description',
            text: 'Description'
        }
    ]
});