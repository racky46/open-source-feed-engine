$(function() {
  $('#feedJobsList').jqGrid({
    url: appContext + '/action/feed/jobs/list',
    datatype: 'json',
    colNames:['Feed Job ID','Feed File ID','Feed ID', 'Start Time', 'Feed File Name', 'Feed Job State'],
    colModel:[
      {name:'feedJob.feedJobId',index:'feedJob.feedJobId',width:80,sortable:false},
      {name:'feedJob.feedFile.feedFileId',index:'feedJob.feedFile.feedFileId',width:80,sortable:false},
      {name:'feedJob.processingStart',index:'feedJob.processingStart',width:140,sortable:false},
      {name:'feedJob.feedFile.feedFileId',index:'feedJob.feedFile.feedFileId',width:110,sortable:false},
      {name:'feedJob.feedFile.feedFileName',index:'feedJob.feedFile.feedFileName',width:110,sortable:false},
      {name:'feed_job_state_id', index:'feed_job_state_id', width:90}
    ],
    caption: 'Feed Job List',
    rowNum: 10,
    rowList:[10,25,50],
    imgpath: appContext + '/styles/jqGrid/themes/green/images',
    pager: $('#pager'),
    sortname: 'feed_job_state_id',
    sortorder: 'asc',
    width: 900,
    height: 150
  });
});