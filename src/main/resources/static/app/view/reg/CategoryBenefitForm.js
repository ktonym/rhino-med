/**
 * Created by user on 14/03/2017.
 */
Ext.define('Rhino.view.reg.CategoryBenefitForm',{
    extend: 'Ext.form.Panel',
    alias: 'widget.category-benefit-form',
    reference: 'categoryBenefitForm',
    requires: ['Rhino.view.reg.CategoryController'],
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
    },
    listeners: {
        beforerender: 'onRenderCatBenForm'
    },
    items: [
        {
            xtype: 'hiddenfield',
            name: 'idCorpBenefit',
            bind: '{current.category-benefit.idCorpBenefit}'
        },
        {
            xtype: 'hiddenfield',
            name: 'idCategory',
            bind: '{current.category-benefit.idCategory}'
        },
        {
            xtype: 'textfield',
            fieldLabel: 'Category',
            name: 'cat',
            bind: '{current.category.cat}',
            readOnly: true
        },
        {
            xtype: 'fieldcontainer',
            layout: 'hbox', fieldLabel: 'Benefit,Limit',
            defaults: { flex: 1},
            items: [
                {
                    xtype: 'combo',
                    fieldLabel: '',
                    hideLabel: 'true',
                    name: 'benefitCode',
                    displayField: 'benefitName',
                    valueField: 'benefitCode',
                    queryMode: 'local',
                    bind: {
                        value: '{current.category-benefit.benefitCode}',
                        store: '{benefitRefs}'
                    }
                },
                {
                    xtype: 'numberfield',
                    fieldLabel: '',
                    hideLabel: 'true',
                    emptyText: 'KES',
                    name: 'upperLimit',
                    bind: '{current.category-benefit.upperLimit}'
                }
            ]
        },
        {
            xtype: 'numberfield',
            fieldLabel: 'Waiting period',
            name: 'waitingPeriod',
            bind: '{current.category-benefit.waitingPeriod}'
        },
        {
            xtype: 'combo',
            fieldLabel: 'Member Type',
            name: 'memberType',
            displayField: 'text',
            valueField: 'text',
            bind: {
                value: '{current.category-benefit.memberType}',
                store: '{memberTypes}'
            },
            queryMode: 'local'
        },
        {
            xtype: 'combo',
            fieldLabel: 'Benefit Type',
            name: 'benefitType',
            displayField: 'text',
            valueField: 'text',
            bind: {
                value: '{current.category-benefit.benefitType}',
                store: '{benefitTypes}'
            },
            queryMode: 'local'
        },
        {
            xtype: 'checkboxgroup',
            fieldLabel: 'Optional items',
            columns: 2,
            vertical: true,
            items: [
                {
                    boxLabel: 'Requires Pre-Auth',
                    name: 'requiresPreAuth',
                    bind: '{current.benefit-category.requiresPreAuth}'
                },
                {
                    boxLabel: 'Shared',
                    name: 'sharing',
                    bind: '{current.benefit-category.sharing}'
                }
            ]
        },
        {
            xtype: 'combo',
            fieldLabel: 'Parent Benefit',
            name: 'idParentCorpBenefit',
            displayField: 'text',
            valueField: 'idCorpBenefit',
            bind: {
                value: '{current.category-benefit.idParentCorpBenefit}',
                store: '{categoryBenefits}'
            },
            queryMode: 'local'
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
                handler: 'onDiscardCatBenefit'
            },
            {
                xtype: 'button',
                ui: 'soft-green',
                text: 'Save',
                handler: 'onSaveCategoryBenefit'
            }

        ]
    }
});