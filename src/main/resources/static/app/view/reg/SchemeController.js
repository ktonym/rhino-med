/**
 * Created by akipkoech on 10/11/2016.
 */
Ext.define('Rhino.view.reg.SchemeController',{
    extend: 'Ext.app.ViewController',
    alias: 'controller.scheme',
    requires: ['Rhino.util.Util'],

    afterSchemeFormRender: function () {
        var me = this,
            vm = me.getViewModel(),
            rec = vm.get('current.scheme');
        me.getView().getForm().loadRecord(rec);
    },

    onSaveScheme: function () {
        var me = this,
            vm = me.getViewModel(),
            store = vm.getStore('schemes'),
            vw = me.getView(),
            form = vw.getForm(),
            rec = form.getRecord(),
            isNewRec = rec.phantom;

        /*if(rec.phantom){
            // store.add(rec);
            //rec.commit();
            console.log('Saving phantom record..');
            isNewRec = true;
        }*/

        rec.save({
            failure: function (record, operation) {
                Rhino.util.Util.showErrorMsg('Save failed.');
            },
            success: function (record, operation) {
                Rhino.util.Util.showToast('Scheme successfully saved.');
                // if(record.store === undefined){
                //     store.add(record);
                // }
                if (isNewRec){
                    debugger;
                    store.add(record);
                    Ext.Msg.alert('New record','Detected a new record');
                }

                me.closeWindow();
            },
            callback: function (record, operation, success) {
                // do smth whether the save succeeded or failed
            }
        });

    },

    onCancel: function () {
        var me = this,
            scheme = me.getViewModel().get('current.scheme');
        scheme.reject();
        me.closeWindow();
    },

    closeWindow: function () {
        var me = this,
            win = me.getView().up('window');
        if (win) {
            win.close();
        }
    }

});