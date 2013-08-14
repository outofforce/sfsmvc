<%--
  Created by IntelliJ IDEA.
  User: linyiming
  Date: 13-8-7
  Time: 下午4:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<script type="text/javascript">
    function initialize() {
        var mp = new BMap.Map('map');
        mp.addControl(new BMap.NavigationControl());  //初始化地图控件
        mp.addControl(new BMap.ScaleControl());
        mp.addControl(new BMap.OverviewMapControl());
        mp.addControl(new BMap.NavigationControl());
        var point=new BMap.Point(116.404, 39.915);
        mp.centerAndZoom(point, 15);//初始化地图中心点
        var marker = new BMap.Marker(point); //初始化地图标记
        marker.enableDragging(); //标记开启拖拽

        var gc = new BMap.Geocoder();//地址解析类
        gc.get
//添加标记拖拽监听
        marker.addEventListener("dragend", function(e){
            //获取地址信息
            gc.getLocation(e.point, function(rs){
                showLocationInfo(e.point, rs);
            });
        });

//添加标记点击监听
        marker.addEventListener("click", function(e){
            gc.getLocation(e.point, function(rs){
                showLocationInfo(e.point, rs);
            });
        });
        mp.addOverlay(marker); //将标记添加到地图中
    }
    //显示地址信息窗口
    function showLocationInfo(pt, rs){
        var opts = {
            width : 250,     //信息窗口宽度
            height: 100,     //信息窗口高度
            title : ""  //信息窗口标题
        }

        var addComp = rs.addressComponents;
        var addr = "当前位置：" + addComp.province + ", " + addComp.city + ", " + addComp.district + ", " + addComp.street + ", " + addComp.streetNumber + "<br/>";
        addr += "纬度: " + pt.lat + ", " + "经度：" + pt.lng;
        //alert(addr);

        var infoWindow = new BMap.InfoWindow(addr, opts);  //创建信息窗口对象
        marker.openInfoWindow(infoWindow);
    }
    function loadScript() {
        var script = document.createElement("script");
        script.src = "http://api.map.baidu.com/api?v=1.5&ak=522b14d8d1411f4bfb0876a8e8b3ec08&callback=initialize";//此为v1.5版本的引用方式
        // http://api.map.baidu.com/api?v=1.5&ak=您的密钥&callback=initialize"; //此为v1.4版本及以前版本的引用方式
        document.body.appendChild(script);
    }

    window.onload = loadScript;
</script>
<head>
    <title>招聘单位信息录入</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        body {
            padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
        }
            /* Wrapper for page content to push down footer */
        #wrap {
            min-height: 100%;
            height: auto !important;
            height: 100%;
            /* Negative indent footer by it's height */
            margin: 0 auto -60px;
        }

            /* Set the fixed height of the footer here */
        #push,
        #footer {
            height: 60px;
        }
        #footer {
            background-color: #f5f5f5;
        }

            /* Lastly, apply responsive CSS fixes as necessary */
        @media (max-width: 767px) {
            #footer {
                margin-left: -20px;
                margin-right: -20px;
                padding-left: 20px;
                padding-right: 20px;
            }
        }



            /* Custom page CSS
            -------------------------------------------------- */
            /* Not required for template or sticky footer method. */

        .container {
            width: auto;
            max-width: 680px;
        }
        .container .credit {
            margin: 20px 0;
        }
    </style>
    <!-- Bootstrap -->
    <link href="<%=request.getContextPath()%>/bootstrap/style/bootstrap.min.css" rel="stylesheet" media="screen">
</head>
<body>
<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container">
            <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="brand" href="#">就业信息管理系统</a>
            <div class="nav-collapse collapse">
                <ul class="nav">
                    <li><a onclick="javascript:showTab('tab1')">单位信息</a></li>
                    <li><a onclick="javascript:showTab('tab2')">招聘信息</a></li>
                    <li><a href="#contact">Contact</a></li>
                </ul>
            </div><!--/.nav-collapse -->
        </div>
    </div>
</div>

<div id="wrap">
    <div class="container">
        <div id="tab1" class="showTable">
           <jsp:include page="../func/tab1.jsp"/>
        </div>
        <div id="tab2" class="showTable" style="display: none">
           <jsp:include page="../func/tab2.jsp"/>
        </div>
    </div>

<div id="push"></div>
</div>

<div id="footer">
    <div class="container">
        <p class="muted credit">Example courtesy <a href="http://martinbean.co.uk">Martin Bean</a> and <a href="http://ryanfait.com/sticky-footer/">Ryan Fait</a>.</p>
    </div>
</div>
<script src="<%=request.getContextPath()%>/bootstrap/js/jquery.js"></script>
<script src="<%=request.getContextPath()%>/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/bootstrap/js/jquery.form.js"></script>
<script type="text/javascript">

    function showTab(tab){
        $(".showTable").each(function(){
           if($(this).attr("id")==tab){
               $(this).show();
           }else{
               $(this).hide();
           }
        });
    }
    function ajaxSub(formId){
        var options={success:showResponse};
        $("#"+formId).ajaxSubmit(options);
    }
    function showResponse(xml){
        if(xml=="success"){
            alert("数据已成功写入");
            window.top.location.reload();
        }else{
            alert("数据写入失败，请重新提交");
        }
    }
    function  mapJpg(){
                    var url="<%=request.getContextPath()%>/register.do";
                    var command="userName=linyiming85@hotmail.com&passwd=123456";
                    alert(url+"?"+command);
//                    $.ajax({
//                        url:url,
//                        data:command,
//                        type: "get",
//                        async: false,
//                        success:function(html){
//                            alert(html);
//                        },
//                        error:function(error){
//                            alert(error);
//                        }
//                    }) ;


    }
    function getXmlHttpString(param){
        var xml = getXMLHttpRequest();
        xml.open("POST", param, false);
        xml.send();
        var str = unescape(xml.responseText);
        return arrstr = str;
    }

    function getXMLHttpRequest()
    {
        var xml;
        if (window.XMLHttpRequest)
        {
            //Mozilla �????
            xml = new XMLHttpRequest();
            if (xml.overrideMimeType)
            {
                //设置MiME类�?
                xml.overrideMimeType("text/xml");
            }
        }
        else
        {
            if (window.ActiveXObject)
            {
                // IE�????
                try
                {
                    xml = new ActiveXObject("Msxml2.XMLHTTP");
                }
                catch (e)
                {
                    try
                    {
                        xml = new ActiveXObject("Microsoft.XMLHTTP");
                    }
                    catch (e)
                    {
                    }
                }
            }
        }
        return xml;
    }
</script>
</body>
</html>