/**
 * Created by user on 16/02/2017.
 */
Ext.define('Rhino.view.reg.CategoryForm',{
    extend: 'Ext.form.Panel',
    alias: 'widget.category-form',
    reference: 'categoryForm',
    requires: ['Rhino.view.reg.CategoryController'/*,'Rhino.view.reg.CategoryModel'*/],//
    viewModel: {
        type: 'reg'
    },
    controller: 'category',
    cls: 'form-compose',
    layout: {
        type: 'vbox',
        align: 'stretch'
    },
    bodyPadding: 10,
    scrollable: true,

    defaults: {
        labelWidth: 120,
        labelSeparator: ''
        //,xtype: 'textfield'
    },
    beforerender : function () {
        var me = this,
            vm = me.getViewModel(),
            idt = vm.get('current.scheme.id'),
            annivStore = vm.getStore('terms');
        console.info('Showing anniversaries store..');
        console.log(annivStore);
        debugger;
    },
    items: [
        {
            xtype: 'hiddenfield',
            name: 'idCorpAnniv',
            bind: '{current.category.idCorpAnniv}'
        },
        {
            xtype: 'hiddenfield',
            name: 'idCategory',
            bind: '{current.category.idCategory}'
        },
        {
            xtype: 'textfield',
            fieldLabel: 'Category',
            name: 'cat',
            bind: '{current.category.cat}'
        },
        {
            xtype: 'textarea',
            fieldLabel: 'Description',
            name: 'description',
            bind: '{current.category.description}'
        }
    ],
    bbar: {
        overflowHandler: 'menu',
        items: [

            '->',
            {
                xtype: 'button',
                ui: 'soft-red',
                text: 'Discard',
                handler: 'onDiscardCategory'
            },
            {
                xtype: 'button',
                ui: 'soft-green',
                text: 'Save',
                handler: 'onSaveCategory'
            }

        ]
    }
});