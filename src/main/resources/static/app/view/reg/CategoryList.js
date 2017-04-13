/**
 * Created by akipkoech on 07/11/2016.
 */
Ext.define('Rhino.view.reg.CategoryList',{
    extend: 'Ext.grid.Panel',
    alias: 'widget.category-list',
    reference: 'categoryList',
    cls: 'email-inbox-panel shadow-panel',
    //requires: ['Rhino.view.reg.CategoryModel'],
    viewModel: {
        type: 'reg'
    },
    //controller: 'category',
    bind: {
        store: '{categories}',
        selection: '{current.category}'
    },
    listeners: {
        beforerender : function () {
            var me = this,
                vm = me.getViewModel(),idt;
            //debugger;
            idt = vm.get('current.anniv.idCorpAnniv');
            vm.getStore('categories').doLoadByAnniv(idt);
        }
    },
    tbar: [
        {
            iconCls: 'x-fa fa-angle-left',
            text: 'Back',
            listeners: {
                click: 'onAnnivsClick'
            }
        },
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
        },
        {
            iconCls: 'x-fa fa-binoculars',
            text: 'Search and add member',
            listeners: {
                click: 'onSearchToAddMember'
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
            text: 'Category',
            flex: 1
        },
        {
            dataIndex: 'description',
            text: 'Description',
            flex: 3
        }
    ]
});