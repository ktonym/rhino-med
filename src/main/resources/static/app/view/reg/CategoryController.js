/**
 * Created by user on 16/02/2017.
 */
Ext.define('Rhino.view.reg.CategoryController',{
    extend: 'Ext.app.ViewController',
    alias: 'controller.category',


    onSaveCategoryBenefit: function () {
        var me = this,
            vm = me.getViewModel(),
            vw = me.getView(),
            //store = vm.getStore('categoryBenefits'),
            rec = vm.get('current.category-benefit'),
            isNewRec = rec.phantom,
            result;
        rec.save({
           callback: function (record, operation,success) {
               result = Rhino.util.Util.decodeJSON(operation.responseText);
               if(success){
                   Rhino.util.Util.showToast('Benefit successfully added to category.');
                   if(isNewRec) {
                       //store.add(record);
                       me.fireEvent('catbenefitadded',record);
                   }/* else {
                       store.update(record);
                   }*/
                   me.closeWindow();
               } else {
                   Rhino.util.Util.showErrorMsg(result.msg);
               }
           }
        });
    },

    onSaveCategory: function () {
        var me = this,
            vm = me.getViewModel(),
            vw = me.getView(),
            //store = vm.getStore('categories'),
            rec = vm.get('current.category'),
            isNewRec = rec.phantom,
            result;
        rec.save({
            callback: function (record, operation, success) {
                result = Rhino.util.Util.decodeJSON(operation.responseText);
                if (success) {
                    Rhino.util.Util.showToast('Category successfully saved.');
                    if(isNewRec){
                        me.fireEvent('categoryAdded',record);
                    }
                    me.closeWindow();
                } else {
                    Rhino.util.Util.showErrorMsg(result.msg);
                }
            }
        });
        
    },

    onDiscardChanges: function (vmRec) {
        var me = this,
            vm = me.getViewModel(),
            rec = vm.get(vmRec);

        if(rec.dirty){
            Ext.Msg.confirm('Revert changes?','The window has unsaved changes, do you want to close and lose them?',
                function (btn) {
                    if(btn==='yes'){
                        rec.reject();
                        me.closeWindow();
                    }
                }
            );
        } else {
            me.closeWindow();
        }

    },

    onDiscardCategory: function () {
        this.onDiscardChanges('current.category');
    },

    onDiscardCatBenefit: function () {
        this.onDiscardChanges('current.category-benefit');
    },

    // onSearchToAddMember: function () {
    //
    // },

    closeWindow: function () {
        var me = this,
            win = me.getView().up('window');
        if (win) {
            win.close();
        }
    },

    onRenderCatBenForm: function () {
        var me = this,
            vm = me.getViewModel(),
            benRefStore = vm.getStore('benefitRefs');
        benRefStore.load();
    }


});