/**
 * Created by akipkoech on 15/11/2016.
 */
Ext.define('Rhino.view.reg.MemberController',{
    extend: 'Ext.app.ViewController',
    alias: 'controller.member',

    onDeletePrincipal: function () {
        
    },

    onDeleteMember: function () {
        
    },

    afterPrincipalFormRender: function () {
        var me = this,
            vm = me.getViewModel(),
            rec = vm.get('current.principal');
        me.getView().getForm().loadRecord(rec);
    },

    onSavePrincipal: function () {
        var me = this,
            vm = me.getViewModel(),
            vw = me.getView(),
            store = vm.getStore('corpPrincipals'),
            form = vw.getForm(),
            rec = form.getRecord();

        // if(rec.phantom){
        //     // store.add(rec);
        //     //rec.commit();
        //     console.log('Saving phantom record..');
        // }

        rec.save({
            failure: function (record, operation) {
                Rhino.util.Util.showErrorMsg('Save failed.');
            },
            success: function (record, operation) {
                Rhino.util.Util.showToast('Principal successfully saved.');
                // me.reloadPrincipals(record);
                console.log(store);
                debugger;
                //store.add(record);
                // if(typeof record.store === 'undefined'){
                //     store.add(record);
                // }

                me.closeWindow();
            }/*,
            callback: function (record, operation, success) {
                // do smth whether the save succeeded or failed
            }*/
        });
    },

    onSaveMember: function () {

    },

    onDiscardPrincipal: function () {
        var me = this,
            principal = me.getViewModel().get('current.principal');
        principal.reject();
        me.closeWindow();
    },

    onCancel: function () {

    },

    closeWindow: function () {
        var me = this,
            win = me.getView().up('window');
        if (win) {
            win.close();
        }
    },

    reloadPrincipals: function (rec) {
        var me = this,
            vm = me.getViewModel(),
            id = vm.get('current.scheme.id'),
            store = vm.getStore('corpPrincipals');
        if(rec.store==='undefined'){
            store.add(rec);
        }
        //store.loadByCorporate(id);
        debugger;
    }
});