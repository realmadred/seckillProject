/**
 * Created by Madrid on 2016/6/12.
 */
//分包
var seckill = {
    url: {
        listUrl: function () {
            return "seckill/list";
        },
        detailUrl: function () {
            return "seckill/detail/";
        }
    },
    getList: function () {
        $.ajax({
            url: basePath + seckill.url.listUrl(),
            type: "get",
            dataType: "json",
            success: function (data) {
                //获取table
                var $tab = $("#main_tab_body");
                if (data && data.length>0) {
                    $.each( data, function(i, d){
                        var starttime = getSmpFormatDate(new Date(d.starttime),true);
                        var endtime = getSmpFormatDate(new Date(d.endtime),true);

                        var $tr = $(" <tr>"+
                          " <td>"+(i+1)+"</td>"+
                          " <td>"+ d.name+"</td>"+
                          " <td>"+ d.number+"</td>"+
                          " <td>"+ d.price+"</td>"+
                          " <td>"+ starttime+"</td>"+
                          " <td>"+ endtime+"</td>"+
                          " <td><a class='btn btn-primary'>查看</a></td>"+
                          " </tr>");

                        //绑定数据到tr
                        $tr.data("id", d.id);
                        //添加
                        $tab.append($tr);
                    });
                }else{
                    alert("没有数据！")
                }
            },
            error: function () {
                alert("error");
            }
        })
    }
}


$(function () {
    //$('[type="checkbox"]').bootstrapSwitch();
    //发送请求获取商品列表
    seckill.getList();

    //绑定单击事件
    $("#main_tab_body").on("click","tr a",function(){
        //获取tr
        var $tr = $(this).parent().parent();
        //获取数据
        var id = $tr.data("id");
        //跳转到强袭页面
        window.location.href = seckill.url.detailUrl()+id;

    });
})