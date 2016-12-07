/**
 * Created by akipkoech on 28/10/2016.
 */
Ext.define('Rhino.view.reg.AnnivController',{
    extend: 'Ext.app.ViewController',
    alias: 'controller.anniv',
    onSaveAnniv: function () {
        // console.log('Clicked on saveAnniv...');
        var me = this,
            record = me.getView().getForm().getRecord;
        record.save();
        debugger;
        this.closeWindow();
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