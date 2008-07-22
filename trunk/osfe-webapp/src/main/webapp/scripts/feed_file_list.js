$(function() {
  $('#feed_file_list').jqGrid({
    url: appContext + '/action/feed/files/list',
    datatype: 'json',
    colNames:['Feed File ID','Feed ID', 'Feed File Name', 'Date', 'Time', 'State', 'Action'],
    colModel:[
      {name:'feedFile.feedFileId',index:'feedJob.feedJobId',width:80,sortable:false},
      {name:'feedFile.feed.feedId',index:'feedFile.feed.feedId',width:130,sortable:false},
      {name:'feedFile.feedFileName',index:'feedFile.feedFileName',width:270,sortable:false},
      {name:'feedFile.feedFileDate',index:'feedFile.feedFileDate',width:60,sortable:false},
      {name:'feedFile.feedFileTime',index:'feedFile.feedFileTime',width:60,sortable:false},
      {name:'feedFile.feedFileState.feedFileStateId',index:'feedFile.feedFileState.feedFileStateId',width:60,sortable:false},
      {name:'action', index:'action', width:80,sortable:false,search:false}
    ],
    caption: 'Feed File List',
    rowNum: 10,
    rowList:[10,25,50],
    imgpath: appContext + '/styles/jqGrid/themes/green/images',
    pager: $('#pager'),
    sortname: 'feed_job_state_id',
    sortorder: 'asc',
    width: 980,
    height: 150,
    onSelectRow: function(ids) {
      if (ids == null) {
        alert("Please select a row");
      } else {

        jQuery('#detail_container').show();
        jQuery('#feed_file_list_detail').setGridParam({url:appContext + "/action/feed/files/details/" + ids}).setCaption("Feed File Details: " + ids).trigger('reloadGrid');
      }
    },
    loadComplete: function() {
      $('a[rel=stats]').boxy();
    }
  }).navGrid('#pager',{edit:false, add:false, del:false},{},{},{},{sopt:['bw','eq','ew','cn']});

  $('#feed_file_list_detail').jqGrid({
    url: appContext + '/action/feed/files/details/0',
    datatype: 'json',
    colNames:['Feed Job ID', 'State', 'Start Time', 'End Time', 'Failure Code', 'Failed Row #', 'Failure Message'],
    colModel:[
      {name:'feedJob.feedJobId',index:'feedJob.feedJobId',width:80,sortable:false},


      {name:'feed_job_state_id',index:'feed_job_state_id',width:100,sortable:true},
      {name:'feedJob.processingStart',index:'feedJob.processingStart',width:110,sortable:false},
      {name:'feedJob.processingEnd',index:'feedJob.processingEnd',width:120,sortable:false},
      {name:'feedJob.failureCode',index:'feedJob.failureCode',width:100,sortable:false},
      {name:'feedJob.failureRowNumber',index:'feedJob.failureRowNumber',width:100,sortable:false},
      {name:'feedJob.failureMessage',index:'feedJob.failureMessage',width:110,sortable:false}
    ],
    caption: 'Feed Job List',
    rowNum: 10,
    rowList:[10,25,50],
    imgpath: appContext + '/styles/jqGrid/themes/green/images',
    pager: $('#pager_detail'),
    sortname: 'feed_job_state_id',
    sortorder: 'asc',
    width: 980,
    height: 150
  });


});