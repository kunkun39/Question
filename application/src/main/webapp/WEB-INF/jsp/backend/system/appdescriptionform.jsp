<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>川网用户反馈平台</title>
    <script src="${pageContext.request.contextPath}/js/jquery-1.7.2.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-ui/jquery-ui-1.8.16.custom.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/jstree/jquery.jstree.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/popup/modal.popup.js" type="text/javascript"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/theme/default/jquery-ui-1.8.22.custom.css" type="text/css"/>
</head>
<body>
<div class="action">
    &nbsp;
</div>

<table cellpadding="0" cellspacing="0" width="100%" class="box">
    <tr>
        <td width="200" valign="top" style="background: #e8e8e8;border-right: 1px solid #CCC;">
            <jsp:include page="../examination.jsp"/>
        </td>
        <td valign="top">
            <spring-form:form commandName="appDescription" method="POST" cssClass="form">
                <table cellspacing="0" width="100%">
                    <tr>
                        <td width="200px;" valign="top">
                             系统描述
                        </td>
                        <td>
                            <spring-form:textarea path="description" cols="80" rows="10"/>
                        </td>
                    </tr>

                    <tr>
                        <td width="200px;">
                        </td>
                        <td>
                        	<button name="" type="submit" class="thoughtbotform">保存</button>
                        </td>
                    </tr>
                </table>
            </spring-form:form>
        </td>
    </tr>
</table>

</body>
</html>