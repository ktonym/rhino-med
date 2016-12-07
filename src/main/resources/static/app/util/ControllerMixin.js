Ext.define('Rhino.util.ControllerMixin',{
    extend: 'Ext.Mixin',
    mixinConfig: {
        id: 'controllermixin'
    },
    setCurrentView: function(view,params){
        var contentPanel = this.getView().down('#contentPanel');

        //We skip rendering for the following scenarios:
        // * There is no contentPanel
        // * view xtype is not specified
        // * current view is the same

        if(!contentPanel || view === '' || (contentPanel.down() && contentPanel.down().xtype === view)){
            console.info('Did not get a #contentPanel!!');
            return false;
        }

        if (params && params.openWindow){
            var cfg = Ext.apply({
                xtype: 'uwwindow',
                items: [
                    Ext.apply({
                        xtype: view
                    }, params.targetCfg)
                ]
            }, params.windowCfg);

            Ext.create(cfg);
        } else {
            Ext.suspendLayouts();

            contentPanel.removeAll(true);
            contentPanel.add(
                Ext.apply({
                    xtype: view
                }, params)
            );

            Ext.resumeLayouts(true);

        }

    },

    onSaveBtnClick: function(){
        var me = this,
            form = me.dialog.down('form'),
            record = form.getRecord(),
            msg;

        form.updateRecord(record);

        record.save({
            callback:  function(record, operation, success) {
                var result = Rhino.util.Util.decodeJSON(operation.responseText);
                if (success) {
                    Rhino.util.Util.showToast('Success! Record saved.');
                    me.onCancel();
                    me.refresh();
                } else {
                    Rhino.util.Util.showErrorMsg(result.msg);
                }
            }
        });

    }
});