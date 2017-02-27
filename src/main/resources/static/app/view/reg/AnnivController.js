/**
 * Created by akipkoech on 28/10/2016.
 */
Ext.define('Rhino.view.reg.AnnivController',{
    extend: 'Ext.app.ViewController',
    alias: 'controller.anniv',


    onSaveAnniv: function () {
        // console.log('Clicked on saveAnniv...');
        var me = this,
            vw = me.getView(),
            vm = me.getViewModel(),
            rec = vm.get('current.anniv'),
            store = vm.getStore('anniversaries'),
            result;
        rec.save({
            callback: function (record,operation,success) {
                result = Rhino.util.Util.decodeJSON(operation.responseText);
                if(success){
                    Rhino.util.Util.showToast('Policy term saved successfully.');
                    if(record.store===undefined){
                        store.add(record);
                    }
                    me.closeWindow();
                } else{
                    Rhino.util.Util.showErrorMsg(result.msg);
                    debugger;
                }
            }
            /*success: function (record,operation) {
                Rhino.util.Util.showToast('Policy term saved successfully.');
                if(record.store===undefined){
                    store.add(record);
                }
                me.closeWindow();
            },
            failure: function (record,operation) {
                response = Rhino.util.Util.decodeJSON(operation.responseText);
                Rhino.util.Util.showErrorMsg(response.msg);
                debugger;
            }*/
        });

    },

    onDiscardAnniv: function () {
        console.log('Clicked on discardAnniv...');
        this.closeWindow();
    },

    afterAnnivFormRender: function () {
        // Ext.Msg.alert('Init','Anniv Form rendered...');
        var me = this,
            vw = me.getView(),
           // rec = vw.getForm().getRecord,
            vm = me.getViewModel(),
            rec = vm.get('current.anniv');
        //debugger;
        vw.getForm().loadRecord(rec);
    },

    closeWindow: function () {
        var me = this,
            win = me.getView().up('window');
        if (win) {
            win.close();
        }
    }
});