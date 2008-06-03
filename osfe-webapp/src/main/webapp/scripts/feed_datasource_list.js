 $(function() {
        $('#feedDatasourceList').jqGrid({
            url: '/app/action/feed/datasources/list',
            datatype: 'json',
            colNames:['Name','Description'],
            colModel:[
                {name:'feedDataSourceId',index:'feedDataSourceId',width:100},
                {name:'description',index:'description',width:100}
            ],
            caption: 'Feed Data Source List',
            rowNum: 25,
            rowList:[25,50,100],
            imgpath: '/app/styles/jqGrid/themes/basic/images',
            pager: jQuery('#pager'),
            sortname: 'name',
            sortorder: 'asc',
            width: 400,
            height: 200
        });
    });