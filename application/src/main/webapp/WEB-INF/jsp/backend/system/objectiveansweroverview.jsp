<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="ch" uri="http://www.chanhong.com" %>
<html>
<head>
    <title>川网用户反馈平台</title>

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
            <form action="#" class="search_form" method="POST">
                <div class="search">
                    <span><label>问题:${question.title}</span>
                </div>
            </form>

            <table width="100%" cellpadding="0" cellspacing="0" class="list">
                <thead>
                <td width="10%">&nbsp;&nbsp;编号</td>
                <td width="50%">回答</td>
                </thead>

                <tbody>
                <c:set var="turns" value="true"/>
                <c:forEach items="${objectiveAnswerList}" var="objectiveAnswer" varStatus="varStatus">
                    <c:set var="color" value="${turns ? 'r1' :'r2'}"/>
                    <tr class="${color}" onmouseover="this.className='over'" onmouseout="this.className='${color}'">
                        <c:set var="turns" value="${!turns}"/>
                        <td>&nbsp;&nbsp;${varStatus.count}</td>
                        <td>&nbsp;&nbsp;${objectiveAnswer.result}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <div class="paging">
                <ch:paging urlMapping="${pageContext.request.contextPath}/backend/objectiveansweroverview.html"
                           showGoTo="false" paging="${paging}"/>
            </div>
        </td>
    </tr>
</table>
</body>
</html>