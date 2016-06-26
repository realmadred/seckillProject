var $countDown = $("#counDown");

//全局变量
var id;
var md5;

//执行秒杀的方法
var seckillDetail = {
    url:{
        excuteSecUrl: function (id,phone,md5) {
            return "seckill/excution/"+id+"/"+phone+"/"+md5;
        }
    },
    showSeckillMsg : function(msg,t_class){
        $("#excute_div").empty().append("<a class='btn btn-"+t_class+" btn-lg'>"+msg+"</a>").fadeIn(1000);
    },
    ajaxSeckill: function (id,phone,md5) {
        $.ajax({
            url:basePath+seckillDetail.url.excuteSecUrl(id,phone,md5),
            type:"post",
            dataType:"json",
            success: function (data) {
                console.log(data);
                if(data == SECKILL_SUCCESS_NO_START){
                    var msg = "非法秒杀！";
                    seckillDetail.showSeckillMsg(msg,"danger");
                }else if(data == SECKILL_SUCCESS_REPEAT){
                    var msg = "重复秒杀！";
                    seckillDetail.showSeckillMsg(msg,"danger");
                }else if(data == SECKILL_SUCCESS_SUCCESS){
                    var msg = "秒杀成功！";
                    seckillDetail.showSeckillMsg(msg,"success");
                }else if(data == SECKILL_SUCCESS_FAILD){
                    var msg = "秒杀失败！";
                    seckillDetail.showSeckillMsg(msg,"success");
                }else if(data == SECKILL_SUCCESS_END){
                    var msg = "秒杀结束！";
                    seckillDetail.showSeckillMsg(msg,"danger");
                }else{
                    var msg = "秒杀错误！";
                    seckillDetail.showSeckillMsg(msg,"danger");
                }
            },
            error: function () {
                alert("系统繁忙！");
            }
        });
    },
    countDown: function (time,t) {
        $countDown = $("#counDown");
        $countDown.countdown(
            time, {elapse: true}).on('update.countdown',
            function (event) {
                var $this = $(this);
                if (event.elapsed) {
                    $this.html(event.strftime("<span class='glyphicon glyphicon-time' aria-hidden='true'></span>秒杀"));
                } else {
                    if(t == TIME_TO_START){
                        $this.text(event.strftime(TIME_FORMAT_START));
                    }else if(t == TIME_TO_END){
                        $countDown.removeClass("btn-primary").addClass("btn-danger")
                        $this.text(event.strftime(TIME_FORMAT_END));
                    }
                }

            });
    },
    //获取最后的一个参数
    getUrlLastParam: function () {
        var urlStr = window.location.href;
        var index = urlStr.lastIndexOf("/");
        var id = urlStr.substring(index + 1);
        return id;
    },
    getDetail: function () {
        //发送请求
        $.getJSON(basePath + "seckill/" + id, function (data) {
            console.log(data);
            var $det1 = $("#detail1");
            var $det2 = $("#detail2");
            //获取状态
            var state = data.state;
            //获取时间差
            var diffTime = data.diffTime;
            //价格和名称
            var detalil = data.price + "元秒杀" + data.name;
            //显示商品详情
            $det1.html(detalil);
            //获取库存
            var remian = data.number;
            //显示秒杀数量
            $det2.html("库存："+remian+" 件");

            //获取当前时间
            var nowTime = new Date().getTime();

            if (state == SECKILL_STATE_NO_START) {
                //秒杀开没有开始,显示倒计时
                seckillDetail.countDown(nowTime + diffTime,TIME_TO_START);
            } else if (state == SECKILL_STATE_START) {
                //秒杀已经开始
                seckillDetail.countDown(nowTime + diffTime,TIME_TO_END);
                //显示秒杀按钮
                var $btn = $("<button class='btn btn-info btn-lg'>"
                    + "<span class='glyphicon glyphicon-time' aria-hidden='true'></span>"
                    + " 秒杀"
                    + "</button>");
                //添加到div
                $("#excute_div").append($btn);
                //绑定数据
                $btn.data("md5",data.md5);
                $btn.data("id",id);
                //绑定单击事件,只绑定一次
                $btn.one("click",seckillDetail.excuteSeckill);
            } else if (state >= SECKILL_STATE_END) {
                //秒杀结束
                $det2.html("秒杀结束！");
                $("#counDown").hide();
            }
        });
    },
    excuteSeckill: function () {
        //执行秒杀
        var $btn = $(this);
        //获取绑定的数据
        md5 = $btn.data("md5");
        var id = $btn.data("id");
        //获取cookie保存的电话信息
        var phone = $.cookie("phone");
        if(!phone){
            //显示model
            showModel();
        }else{
            //执行秒杀
            seckillDetail.ajaxSeckill(id,phone,md5);
        }
    }
}


$(function () {
    //获取id
    id = seckillDetail.getUrlLastParam()
    //获得详细信息
    seckillDetail.getDetail();

    //绑定弹出框的保存按钮事件
    $("#save_model").click(savePhone);

    //model显示后回调函数
    $('#myModal').on('shown.bs.modal', function (e) {
        var $input = $("#input_phone");
        $input.focus();//获取焦点
    });

    //关闭model后回调
    $('#myModal').on('hidden.bs.modal', function (e) {
        var phone = $.cookie("phone");
        if(!checkPhone(phone)){
            //绑定单击事件
            $("#excute_div button").one("click",seckillDetail.excuteSeckill);
            return false;
        }
        //执行秒杀
        seckillDetail.ajaxSeckill(id,phone,md5);
    });
});

//检查手机号码的格式
function checkPhone(phone) {
    var reg = /^1[34578]\d{9}$/;
    if(reg.test(phone)){
        return true;
    }
    return false;
}

//弹出框保存按钮事件
function savePhone() {
    var $input = $("#input_phone");
    //获取文本框的值
    var str = $.trim($input.val());
    if(str == ""){
        $("#span_msg").hide().html("请输入手机号码！").fadeIn(1000);
        $input.focus();
    }else if (!checkPhone(str)){
        $("#span_msg").hide().html("请输入正确的手机号码！").fadeIn(1000);
        $input.focus();
    }else{
        $.cookie("phone",str, { expires: 1, path: '/seckillContext/'});
        //关闭模态框
        $('#myModal').modal('hide');
    }
}

//显示model
function showModel(){
    //需要输入手机号码
    $("#myModal").modal({
        keyboard: false,
        backdrop:"static"
    });
}