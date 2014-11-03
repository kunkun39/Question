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
            <table  width="100%" cellpadding="0" cellspacing="0" class="list">
               <thead>
               <td width="50%"> 问题:${objectiveAnswerList.get(0).questionDTO.title}  </td>
               <td width="50%"></td>
               </thead>

                  <tbody>
                <c:forEach items="${objectiveAnswerList}" var="objectiveAnswer" varStatus="varStatus">
                    <tr>
                        <td>${varStatus.index+1}</td>
                        <td>&nbsp;&nbsp;${objectiveAnswer.result}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <div class="paging">
                <ch:paging urlMapping="${pageContext.request.contextPath}/backend/objectiveansweroverview.html" showGoTo="false" paging="${paging}"/>
            </div>

        </td>
    </tr>
</table>
</body>
</html>