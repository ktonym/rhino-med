Ext.define('Rhino.view.uw.benefit.BenefitRefController',{
   extend: 'Rhino.view.uw.UwController',
    alias: 'controller.benefit-ref',
    requires: ['Rhino.model.uw.BenefitRef','Rhino.util.Util'],

    onAddBtnClick: function(btn,e,eOpts){
        this.createDialog(null);
    },

    onEditBtnClick: function (btn,e,eOpts) {
        var me = this,
            vm = me.getViewModel(),
            record = vm.get('selectedBenefit');
        me.createDialog(record);
    },

    onDelBtnClick: function(){
        var me = this,
            vm = me.getViewModel(),
            record = vm .get('selectedBenefit');

        Ext.Msg.confirm('Confirm','Are you sure you want to delete this benefit?',
            function(btn) {
                if(btn === 'yes') {
                    // record.erase();
                    // vm.set('selectedBenefit', null);
                    // Rhino.util.Util.showToast('Benefit successfully deleted!');
                    record.erase({
                        callback: function(record,operation,success){
                            var result = Rhino.util.Util.decodeJSON(operation.responseText);
                            if (success) {
                                Rhino.util.Util.showToast(result.msg);
                                me.onCancel();
                                me.refresh(); //testing to see if refreshing is necessary...
                                //vm.set('selectedBenefit',null);
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
        this.setCurrentView('benefit-ref-list');
    },
    refresh: function(){
        var me = this;
        me.getViewModel().data.benefitRefs.load();
    },
    onCancel: function(){
        var me = this;
        me.dialog = Ext.destroy(me.dialog);
    },

    createDialog: function(record){
        var me = this,
            view = me.getView(),
            rec;

        me.dialog = view.add({
            xtype: 'benefit-ref-form',
            session: true,
            viewModel: {
                schema: 'uwSchema',
                links: {
                    currentBenefit: record || {
                        type: 'Rhino.model.uw.BenefitRef',
                        create: true
                    }
                }
            },
            bind: {
                title: record ? 'Edit: <b>{currentBenefit.benefitName}</b>' : 'Add Benefit'
            }
        });

        me.dialog.down('form').loadRecord(me.dialog.getViewModel().get('currentBenefit'));

        me.dialog.show();
    }
    
});