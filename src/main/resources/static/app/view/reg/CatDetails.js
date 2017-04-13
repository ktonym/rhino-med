/**
 * Created by akipkoech on 10/11/2016.
 */
Ext.define('Rhino.view.reg.CatDetails',{
    extend: 'Ext.tab.Panel',
    alias: 'widget.cat-details',
    requires: [/*'Rhino.view.reg.CategoryModel',*/'Rhino.view.reg.CategoryList','Rhino.view.reg.CategoryForm','Rhino.view.reg.CategoryBenefitList'],
    //controller: 'category',
    // viewModel: {
    //     type: 'scheme'
    // },
    activeTab: 0,
    items: [
        {
            title: 'Categories',
            xtype: 'category-list',
            iconCls: 'x-fa fa-cubes'
        },
        {
            title: 'Benefits',
            xtype: 'category-benefit-list',
            iconCls: 'x-fa fa-star-o',
            bind: {
                disabled: '{!categoryList.selection}'
            }
        },
        {
            title: 'Members',
            xtype: 'panel',
            iconCls: 'x-fa fa-group',
            bind: {
                disabled: '{!categoryList.selection}'
            }
        }
    ]
});