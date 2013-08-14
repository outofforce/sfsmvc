<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%--
  Created by IntelliJ IDEA.
  User: linyiming
  Date: 13-8-13
  Time: 下午9:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<h1 style="text-align: center">请录入招聘信息</h1>
<form id="recruitInfoForm" action="<%=request.getContextPath()%>/insertRecruitInfo.do" method="post" >
<input type="hidden" id="compNameVaule" value="<%List<Map> ComInfoList=(List)request.getAttribute("ComInfoList");
              for(int i=0;i<ComInfoList.size();i++){
                Map map=ComInfoList.get(i);
                if(i<(ComInfoList.size()-1)) {
            %><%=map.get("company_name")%>|<%=map.get("company_code")%>,<%
} else{%><%=map.get("company_name")%>|<%=map.get("company_code")%><%
        }}
%>">
<table class="table table-bordered" style="width: 70%;text-align: center;margin: auto" >
    <tr>
        <td style="text-align: center" >
            单位名称
        </td  >
        <td style="text-align: left" >
             <input class="span5" id="companyName" type="text" name="companyName" data-source='[<%
              for(int i=0;i<ComInfoList.size();i++){
                Map map=ComInfoList.get(i);
                if(i<(ComInfoList.size()-1)) {
            %>"<%=map.get("company_name")%>",<%
              } else{%>"<%=map.get("company_name")%>"<%
              }}
            %>]' data-items="4" data-provide="typeahead" style="margin: 0 auto;" onchange="javascript:CompNameChg(this)">
        </td  >
    <tr>
        <td style="text-align: center" >
            单位编码
        </td  >
        <td style="text-align: left" >
            <input class="span5" id="companyId" type="text" name="companyId">
        </td  >
    </tr>
    <tr>
        <td style="text-align: center" >
            单位地址
        </td >
        <td style="text-align: left" >
            <input type="text" class="span2" placeholder="工作地点" id="baseCode" name="baseCode">
            <input type="text" class="span3" placeholder="地址" id="compAddr" name="compAddr" >
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
            <input type="text" class=span5 id="position" name="position">
        </td  >
    </tr>
    <tr>
        <td style="text-align: center" >
            招聘人数
        </td >
        <td style="text-align: left" >
            <input type="text" class=span5 id="recruitNum" name="recruitNum">
        </td  >
    </tr>
    <tr>
        <td style="text-align: center" >
            岗位要求
        </td  >
        <td style="text-align: left">
            <textarea rows=10 class="span5" id="jobDesc" name="jobDesc"></textarea>
        </td >
    </tr>
</table>
<div style="text-align: center">
    <input type="button" class="btn-primary"  value="确定" onclick="javascript:ajaxSub('recruitInfoForm')" style="margin-top: 40px;text-align: center">
</div>
</form>
</body>
</html>