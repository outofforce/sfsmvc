<%--
  Created by IntelliJ IDEA.
  User: linyiming
  Date: 13-8-13
  Time: 下午9:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<h1 style="text-align: center">请录入单位信息</h1>
<form id="companyInfoForm" action="<%=request.getContextPath()%>/insertCompanyInfo.do" method="post" >
<table class="table table-bordered" style="width: 70%;text-align: center;margin: auto" >
    <tr>
        <td style="text-align: center" >
            单位编码
        </td  >
        <td style="text-align: left" >
                <input class="span5" id="appendedInputButtons" type="text" id="companyCode" name="companyCode">
        </td  >
        </tr>
    <tr>
        <td style="text-align: center" >
            单位名称
        </td  >
        <td style="text-align: left" >
                <input class="span5" id="appendedInputButtons" type="text" id="companyName" name="companyName">
        </td  >
    </tr>
    <tr>
        <td style="text-align: center" >
            单位地址
        </td >
        <td style="text-align: left" >
            <input type="text" class="span5" placeholder="地址" id="companyAddr" name="companyAddr">
        </td >
    </tr>
    <tr>
        <td style="text-align: center" >
            公司简介
        </td  >
        <td style="text-align: left">
            <textarea rows=10 class="span5" id="companyDesc" name="companyDesc"></textarea>
        </td >
    </tr>
</table>
<div style="text-align: center">
    <input type="button" class="btn-primary" onclick="javascript:ajaxSub('companyInfoForm')" value="确定" style="margin-top: 40px;text-align: center">
</div>
</form>
