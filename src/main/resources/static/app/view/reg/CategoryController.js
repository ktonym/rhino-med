/**
 * Created by user on 16/02/2017.
 */
Ext.define('Rhino.view.reg.CategoryController',{
    extend: 'Ext.app.ViewController',
    alias: 'controller.category',

    onAddCategory: function () {
        var me = this,
            vw = me.getView(),
            vm = me.getViewModel(),
            annivId = vm.get('current.anniv.idCorpAnniv'),
            rec = Ext.create('Rhino.model.uw.Category',{
               idCorpAnniv: annivId
            });
        
        
    },

    onEditCategory: function (id) {

    }

});