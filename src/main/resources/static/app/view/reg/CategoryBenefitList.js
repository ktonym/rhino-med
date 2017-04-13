/**
 * Created by user on 14/02/2017.
 */
Ext.define('Rhino.view.reg.CategoryBenefitList',{
    extend: 'Ext.grid.Panel',
    alias: 'widget.category-benefit-list',
    reference: 'categoryBenefitList',
    viewModel: {
        type: 'reg'
    },
    bind: {
        selection: '{current.categoryBenefit}',
        store: '{categoryBenefits}'
    },
    listeners: {
        beforerender : function () {

            var me = this,
                vm = me.getViewModel(),
                idt = vm.get('current.category.idCategory'),
                catBenStore = vm.getStore('categoryBenefits');
            //debugger;
            //catBenStore.getProxy().setUrl('uw/corpbenefit/findByCategory');
            if(idt){
                catBenStore.doLoadByCategory(idt);
            }else {
                Ext.Msg.alert('No category selected', 'Please select a category to view benefits');
            }
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
                click: 'onAddCategoryBenefit'
            },
            bind: {
                disabled: '{!categoryList.selection}'
            }
        },
        {
            iconCls: 'x-fa fa-newspaper-o',
            text: 'Details',
            listeners: {
                click: 'onCategoryBenefitDetails'
            },
            bind: {
                disabled: '{!categoryBenefitList.selection}'
            }
        },
        {
            iconCls: 'x-fa fa-remove',
            text: 'Delete',
            listeners: {
                click: 'onDelCorpBenefitBtnClick'
            },
            bind: {
                disabled: '{!categoryBenefitList.selection}'
            }
        }
    ],
    columns: [
        /*{
            dataIndex: 'idCorpBenefit',
            text: 'ID',
            width: 60
        },*/
        {
            dataIndex: 'cat',
            text: 'Category',
            width: 100
        },
        {
            dataIndex: 'benefitName',
            text: 'Benefit',
            flex: 1
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
        },
        {
            dataIndex: 'waitingPeriod',
            text: 'Waiting Period(Days)',
            flex: 1
        },
        {
            dataIndex: 'idParentCorpBenefit',
            text: 'Parent Benefit',
            flex: 2
        }
        /*{
            dataIndex: 'sharing',
            text: 'Shared',
            flex: 1
        },
        {
            dataIndex: 'requiresPreAuth',
            text: 'Pre-Auth Needed',
            flex: 1
        }*/
    ]
});