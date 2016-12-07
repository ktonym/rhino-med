Ext.define('Rhino.store.CorpAnniv',{
    extend: 'Ext.data.Store',
    requires: ['Rhino.model.uw.CorpAnniv'],
    model: 'Rhino.model.uw.CorpAnniv',
    loadByCorporate: function(idCorp){
        this.load({
            params: {
                idCorporate: idCorp
            }
        });
    }
});