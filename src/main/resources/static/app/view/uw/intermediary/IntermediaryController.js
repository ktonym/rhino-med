Ext.define('Rhino.view.uw.intermediary.IntermediaryController',{
   extend: 'Rhino.view.uw.UwController',
    alias: 'controller.intermediary',
    requires: ['Rhino.model.uw.Intermediary','Rhino.util.Util'],

    createDialog: function(record) {
        var me = this,
            view = me.getView(),
            rec;

        me.dialog = view.add({
            xtype: 'intermediary-form',
            //$initParent: view,
            viewModel:{
               schema: 'uwSchema',
                links: {
                    currentIntermediary: record || {
                        type: 'Rhino.model.uw.Intermediary',
                        create : true
                    }
                }
            },
            session: true,
            bind: {
                title: record ? 'Edit : {currentIntermediary.name} ' : 'Add Intermediary'
            }
        });

        if(record === null){
            // rec = Ext.create('Rhino.model.uw.Intermediary'); //use session instead..
            rec = view.getSession().createRecord('Intermediary');
        }else{
            rec = record;
        }

        me.dialog.down('form').loadRecord(rec);
        me.dialog.show();
    },
    
    onAddBtnClick: function(){
        this.createDialog(null);
    },

    onEditBtnClick: function(){
        var me = this,
            vm = me.getViewModel(),
            record = vm.get('selectedIntermediary');
        me.createDialog(record);
    },

    onDelBtnClick: function(){
        var me = this,
            vm = me.getViewModel(),
            record = vm.get('selectedIntermediary');

        Ext.Msg.confirm('Confirmation','Are you sure you want to delete this intermediary?',
                function(id){
                    if (id === 'yes'){
                        record.erase({
                            callback: function(record,operation,success){
                                var result = Rhino.util.Util.decodeJSON(operation.responseText);
                                if (success) {
                                    Rhino.util.Util.showToast(result.msg);
                                    me.onCancel();
                                    me.refresh();
                                } else {
                                    Rhino.util.Util.showErrorMsg(result.msg);
                                }
                            }
                        });
                    }
                }
        );

    },
    
    onDiscardClick: function(){

            var me = this;
            me.dialog = Ext.destroy(me.dialog);

    },

    onSaveIntermediaryClick: function(){
        var me = this,
            form = me.dialog.down('form'),
            record = form.getRecord(),
            msg;

        form.updateRecord(record);

        record.save({
            callback: function(record,operation,success){
                var result = Rhino.util.Util.decodeJSON(operation.responseText);
                if (success) {
                    Rhino.util.Util.showToast('Success! Intermediary saved.');
                    me.onCancel();
                    me.refresh();
                } else {
                    Rhino.util.Util.showErrorMsg(result.msg);
                }
            }
        });
    },

    refresh: function(){
        var me = this;
        me.getViewModel().data.intermediaries.load();
    },

    onCancel: function(){
        var me = this;
        me.dialog = Ext.destroy(me.dialog);
    }

});