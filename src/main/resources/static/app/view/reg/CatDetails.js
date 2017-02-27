/**
 * Created by akipkoech on 10/11/2016.
 */
Ext.define('Rhino.view.reg.CatDetails',{
    extend: 'Ext.tab.Panel',
    alias: 'widget.cat-details',
    requires: ['Rhino.view.reg.CategoryList','Rhino.view.reg.CorpBenefitList','Rhino.view.reg.CategoryController'],
    controller: 'category',
    activeTab: 0,
    items: [
        {
            title: 'Categories',
            xtype: 'category-list'
        },
        {
            title: 'Benefits',
            xtype: 'corp-benefit-list',
            bind: {
                disabled: '{!categoryList.selection}'
            }
        },
        {
            title: 'Members',
            xtype: 'panel',
            bind: {
                disabled: '{!categoryList.selection}'
            }
        }
    ]
});