$(function() {
  var lastsel;
  $('#feedGroupList').jqGrid({
    url: appContext + '/action/feed/group/list',
    datatype: 'json',
    colNames:['Name','Concurrent Runs','Failed State Runs','Collect Phase Stats','Actions'],
    colModel:[
      {name:'feedGroup.feedGroupId',index:'feedGroup.feedGroupId',width:100,editable:false},
      {name:'feedGroup.allowConcurrentRuns',index:'feedGroup.allowConcurrentRuns',width:150},
      {name:'feedGroup.allowFailedStateRuns',index:'feedGroup.allowFailedStateRuns',width:150},
      {name:'feedGroup.collectPhaseStats',index:'feedGroup.collectPhaseStats',width:150},
      {name:'actions',index:'actions',width:50,sortable:false}
    ],
    caption: 'Feed Group List',
    rowNum: 10,
    rowList:[10,25,50],
    imgpath: appContext + '/styles/jqGrid/themes/green/images',
    pager: $('#pager'),
    sortname: 'name',
    sortorder: 'asc',
    width: 650,
    height: 150,
    loadComplete: function() {
      var ids = jQuery("#feedGroupList").getDataIDs();
      for (var i = 0; i < ids.length; i++) {
        var row_id = ids[i];
        be = "<img title='edit_row' id='" + row_id + "' src='" + appContext + "/images/row_edit.gif' />";
        bd = "<img title='del_row' id='" + row_id + "' src='" + appContext + "/images/row_delete.gif' />";
        jQuery("#feedGroupList").setRowData(ids[i], {actions:be + bd})
      }
      $('img[title=edit_row]').click(function() {
        window.location = appContext + "/action/feed/group/edit/" + this.id;
      });
      $('img[title=del_row]').click(function() {
        var id = this.id;

        confirmDelete("Really Delete?", function() {
          var params = {'feedGroup.feedGroupId':id};
          $.post(appContext + '/action/feed/group/delete/', params, function(data) {
            $('#feedGroupList').delRowData(id);
          });
        });
      });
    }
  });
});

function confirmDelete(message, callback) {
  $('#confirm').modal({
    close:false,
    overlayId:'confirmModalOverlay',
    containerId:'confirmModalContainer',
    onShow: function (dialog) {
      dialog.data.find('.message').append(message);
			// if the user clicks "yes"
      dialog.data.find('.yes').click(function () {
        // call the callback
        if ($.isFunction(callback)) {
          callback.apply();
        }
				// close the dialog
        $.modal.close();
      });
    }
  });
}