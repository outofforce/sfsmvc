<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>我的蝴蝶</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Bootstrap -->
    <link href="<%=request.getContextPath()%>/bootstrap/style/bootstrap.min.css" rel="stylesheet" media="screen">
    <!-- Le styles -->
    <link href="<%=request.getContextPath()%>/bootstrap/style/bootstrap-responsive.min.css" rel="stylesheet">
    <style type="text/css">
        .card {border-width: 1px; border-style: solid; padding: 1px;padding:1px;}
        .a {background-color: #F3F3F3; border-color: #FBFBFB;}
        .b {background-color: #D8D8D8; border-color: #E8E8E8;}
        .c {background-color: #FFF; border-color: #BBB; height:auto !important;
            height:100px; /*假定最低高度是200px*/
            min-height:100px;  ;color:#ff0000;}
    </style>
</head>
<body>



<script type="text/javascript">


</script>
<div class="navbar navbar-inverse navbar-fixed-top" >
    <div class="navbar-inner" id="topPin">
        <div class="container-fluid" >
            <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="brand" href="#">Butterfly</a>
            <div class="nav-collapse collapse">
                <p class="navbar-text pull-right">
                    Logged in as <a href="#" class="navbar-link">Username</a>
                </p>
                <ul class="nav">
                    <li class="active"><a href="#">Home</a></li>
                    <li><a href="#about">About</a></li>
                    <li><a href="#contact">Contact</a></li>
                </ul>
            </div><!--/.nav-collapse -->
        </div>
    </div>
</div>

<div class="container-fluid" >
    <div class="row-fluid" style="margin-top: 10px">
        <div class="span12">
            <h4>说点什么..</h4>
            <form action="<%=request.getContextPath()%>/postPubData.do" method="post" id="postForm">
                <textarea class="span12" rows="4" name="postContext" id="postContext"></textarea>
                <input type="hidden" name="userId" id="userId" value="<c:out value='${userId}'/>"/>
                <input type="hidden" name="ttl_type" value="3">
            </form>
            <div >
                <a class="btn btn-primary" onclick="javascript:sendMTLMsg();" >确定</a>
                <form id="imgUploadForm" enctype="multipart/form-data" method="post" action="http://192.168.1.148/postfile">
                   <input type=file id="upic"  onchange="javascript:postImgToamazonmtl()" >
                </form>
            </div>

        </div>
        <div class="row-fluid" style="margin-top: 10px" id="msgList">
            <c:forEach items="${publishList}" var="publish">
                <div class="card a">
                    <div class="card b">
                        <div class="card c">
                            <h6><img src="http://ec2-54-249-93-187.ap-northeast-1.compute.amazonaws.com:8000/getfile-${publish.headImg}" alt="headImg" width="50" height="50" style="float: left;margin-right: 10px"><c:out value="${publish.userName}" /></h6>
                            <h6 style="color: #000000"><c:out value="${publish.postContext}"/> </h6>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

</div>

<script src="<%=request.getContextPath()%>/bootstrap/js/jquery.js"></script>
<script src="<%=request.getContextPath()%>/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/bootstrap/js/jquery.form.js"></script>
<script src="<%=request.getContextPath()%>/bootstrap/js/jquery.pin.js"></script>
<script src="<%=request.getContextPath()%>/bootstrap/js/base64.js"></script>
<script type="text/javascript">
    $().ready(function(){
        $("#topPin").pin();
    })  ;
    function sendMTLMsg(){
        var option={success: showResponse};
        $("#postForm").ajaxSubmit(option);
    }
    function showResponse(responseText, statusText)  {
        var b=new Base64();
        if(b.decode(responseText)=="success"){
            var aaa="<div class='card a'>  <div class='card b'> <div class='card c'><h6><img src='<%=request.getContextPath()%>/img/touxiang.jpg' alt='touxiang' width='50' height='50' style='float: left;margin-right: 10px'>asdf</h6> <h6>发送</h6> </div></div></div>"  ;
            $("#msgList").before(aaa);
        }

    }
    function postImgToamazonmtl(){
        var option={success: imgResponse,
                    error:errResponse};
        $("#imgUploadForm").ajaxSubmit(option);
    }
    function imgResponse(responseText, statusText) {
        alert("22");
        var b=new Base64();
        alert(b.decode(responseText));
    }
    function errResponse() {
        alert(XMLResponse;
    }
</script>
</body>
</html>