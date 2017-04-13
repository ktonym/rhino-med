/**
 * Created by user on 22/03/2017.
 */
Ext.define('Rhino.store.PolicyMembers',{
    extend: 'Ext.data.Store',
    alias: 'store.policy-members',
    requires: ['Rhino.model.uw.MemberAnniv'],
    model: 'Rhino.model.uw.MemberAnniv',
    autoLoad: false,
    loadByCorpAnniv: function(idCorpAnniv){
        this.load({
            params: {
                idCorpAnniv: idCorpAnniv
            }
        });
    }
});