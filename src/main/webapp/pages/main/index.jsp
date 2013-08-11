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
    <!-- Bootstrap -->
    <link href="../../bootstrap/style/bootstrap.min.css" rel="stylesheet" media="screen">
</head>
<body>
<div style="text-align:center">
    <h1 style="text-align: center">请录入招聘单位信息</h1>
    <table class="table table-bordered" style="width: 70%;text-align: center;margin: auto" >
        <tr>
            <td style="text-align: center" >
                单位名称
            </td  >
            <td style="text-align: left" >
                <input type="text">
            </td  >
        </tr>
        <tr>
            <td style="text-align: center" >
                单位地址
            </td >
            <td style="text-align: left" >
                <input type="text">     <input type="button" class="btn-primary" value="搜索" onclick="return mapJpg();">
            </td >
        </tr>
        <tr style="display: none;" id="displayTr">
            <td colspan="2" style="text-align: center">
                <div id="map" style="width:500px;height:320px"></div>
            </td>
        </tr>
        <tr>
            <td style="text-align: center" >
                招聘岗位
            </td >
            <td style="text-align: left" >
                <input type="text">
            </td  >
        </tr>
        <tr>
            <td style="text-align: center" >
                招聘人数
            </td >
            <td style="text-align: left" >
                <input type="text">
            </td  >
        </tr>

        <tr>
            <td style="text-align: center" >
                公司简介
            </td  >
            <td style="text-align: left">
                <textarea></textarea>
            </td >
        </tr>
    </table>

    <input type="button" class="btn-primary" value="确定" style="margin-top: 40px">
</div>

<script src="../../bootstrap/js/jquery.js"></script>
<script src="../../bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript">
    function  mapJpg(){
                    var url="<%=request.getContextPath()%>/active.do";
                    var command="userName=linyiming85@hotmail.com&passwd=123456";
                    $.ajax({
                        url:url,
                        data:command,
                        type: "get",
                        async: false,
                        success:function(html){
                            alert(html);
                        },
                        error:function(error){
                            alert(error);
                        }
                    }) ;


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