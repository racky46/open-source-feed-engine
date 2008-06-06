$(function() {
  var lastsel;
  $('#feedList').jqGrid({
    url: appContext + '/action/feeds/list',
    datatype: 'json',
    colNames:['Feed ID','From Data Source', 'To Data Source', 'Feed Type', 'Feed Protocol', 'Feed Direction', 'Feed Group','Actions'],
    colModel:[
      {name:'feed.feedId',index:'feed.feedId',width:100},
      {name:'feed.fromDataSource.feedDataSourceId',index:'feed.fromDataSource.feedDataSourceId',width:120},
      {name:'feed.toDataSource.feedDataSourceId', index:'feed.toDataSource.feedDataSourceId', width:120},
      {name:'feed.feedType.feedTypeId', index:'feed.feedType.feedTypeId', width: 100},
      {name:'feed.feedProtocol.feedProtocolId', index:'feed.feedProtocol.feedProtocolId', width: 100},
      {name:'feed.feedDirection.feedDirectionId', index:'feed.feedDirection.feedDirectionId', width: 100},
      {name:'feed.feedGroup.feedGroupId', index:'feed.feedGroup.feedGroupId', width: 100},
      {name:'actions',index:'actions',width:55,sortable:false}
    ],
    caption: 'Feed Data Source List',
    rowNum: 10,
    rowList:[10,25,50],
    imgpath: appContext + '/styles/jqGrid/themes/green/images',
    pager: $('#pager'),
    sortname: 'feed.feedId',
    sortorder: 'asc',
    width: 910,
    height: 150,
    loadComplete: function() {
      var ids = $("#feedList").getDataIDs();
      for (var i = 0; i < ids.length; i++) {
        var row_id = ids[i];
        be = "<img title='edit_row' id='" + row_id + "' src='" + appContext + "/images/row_edit.gif' />";
        bd = "<img title='del_row' id='" + row_id + "' src='" + appContext + "/images/row_delete.gif' />";
        jQuery("#feedList").setRowData(ids[i], {actions:be + bd})
      }
      $('img[title=edit_row]').click(function() {
        window.location = appContext + "/action/feeds/edit/" + this.id;
      });
      $('img[title=del_row]').click(function() {
        var id = this.id;

        confirmDelete("Really Delete?", function() {
          var params = {'feed.feedId':id};
          $.post(appContext + '/action/feeds/delete/', params, function(data) {
            $('#feedList').delRowData(id);
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