 $(function() {
        $('#feedDatasourceList').jqGrid({
            url: '/app/action/feed/datasources/list',
            datatype: 'json',
            colNames:['Name','Description','Actions'],
            colModel:[
                {name:'feedDataSourceId',index:'feedDataSourceId',width:100},
                {name:'description',index:'description',width:100},
                {index:'actions',width:100,sortable:false}
            ],
            caption: 'Feed Data Source List',
            rowNum: 25,
            rowList:[25,50,100],
            imgpath: '/app/styles/jqGrid/themes/basic/images',
            pager: jQuery('#pager'),
            sortname: 'name',
            sortorder: 'asc',
            width: 400,
            height: 200,
            loadComplete: function() {

              $('a[title=delete]').click(function() {
                alert("Going to delete " + this.id);
              });

              $('#feedDatasourceList a').css("cursor", "pointer");
            }
        }).navGrid('#pager', {edit:false, add:false, del:false},{},{},{},{sopt:['bw','eq','ew','cn']});
    });