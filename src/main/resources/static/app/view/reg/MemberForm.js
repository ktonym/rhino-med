/**
 * Created by akipkoech on 15/11/2016.
 */
Ext.define('Rhino.view.reg.MemberForm',{
    extend: 'Ext.form.Panel',
    alias: 'widget.member-form',
    requires: ['Rhino.view.reg.MemberController','Rhino.view.reg.MemberModel'],//
    controller: 'member',
    viewModel: {
        type: 'member'
    },
    cls: 'form-compose',
    layout: {
        type: 'vbox',
        align: 'stretch'
    },
    bodyPadding: 10,
    scrollable: true,
    defaults: {
        labelWidth: 120,
        labelSeparator: '',
        xtype: 'textfield'
    },
    listeners: {
        beforerender : function () {
            var me = this,
                vm = me.getViewModel(),
                corpId = vm.get('current.scheme.id'),
                store = vm.getStore('corpPrincipals');
            store.loadByCorporate(corpId);
            console.info('Showing contents of store..');
            console.log(store);
        }
    },
    items: [
        {
            xtype: 'hiddenfield',
            name: 'idMember',
            bind: '{current.member.idMember}'
        },
        {
            fieldLabel: 'Member No',
            name: 'memberNo',
            bind: '{current.member.memberNo}',
            disabled: true
        },
        {
            xtype: 'fieldcontainer',
            layout: 'hbox',
            fieldLabel: 'Name',
            items: [
                {
                    fieldLabel: '',
                    xtype: 'textfield',
                    flex: 1,
                    name: 'firstName',
                    hideLabel: true,
                    emptyText: 'First Name',
                    bind: '{current.member.firstName}'
                },
                {
                    fieldLabel: '',
                    xtype: 'textfield',
                    flex: 1,
                    emptyText: 'Surname',
                    name: 'surname',
                    bind: '{current.member.surname}'
                }
            ]
        },
        {
            fieldLabel: 'Other name(s)',
            name: 'otherNames',
            bind: '{current.member.otherNames}'
        },
        {
            xtype: 'datefield',
            fieldLabel: 'DoB',
            name: 'dob',
            //format: 'Ymd',
            bind: '{current.member.dob}'
        },
        {
            xtype: 'combo',
            fieldLabel: 'Sex',
            name: 'sex',
            displayField: 'text',
            valueField: 'text',
            bind: {
                value: '{current.member.sex}',
                store: '{sexes}'
            },
            queryMode: 'local'
            //,valueField: 'sex'
        },
        {
            xtype: 'combo',
            fieldLabel: 'Member Type',
            name: 'memberType',
            displayField: 'text',
            valueField: 'text',
            queryMode : 'local',
            bind: {
                store: '{memberTypes}',
                value: '{current.member.memberType}'
            } 
        },
        {
            xtype: 'combo',
            fieldLabel: 'Principal',
            name: 'principal',
            displayField: 'fullName',
            queryMode: 'local',
            //hideTrigger: false,
            bind: {
                store: '{principals}',
                value: '{current.member.idPrincipal}'
            },
            valueField: 'surname'
            /*minChars: 3,
            listConfig: {
                loadingText: 'Searching...',
                emptyText: 'No matching principals found.',
                getInnerTpl: function () {
                    return '<h3><span>{surname}, {firstName}</span></h3></br> ' +
                            '{memberNo}';
                }
            }*/
        }
    ],
    bbar: {
        overflowHandler: 'menu',
        items: [

            '->',
            {
                xtype: 'button',
                ui: 'soft-red',
                text: 'Discard',
                handler: 'onDiscardMember'
            },
            {
                xtype: 'button',
                ui: 'soft-green',
                text: 'Save',
                handler: 'onSaveMember'
            }

        ]
    }
});