<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="ch" uri="http://www.chanhong.com" %>
<html>
<head>
    <title>川网用户反馈平台</title>
    <script src="${pageContext.request.contextPath}/js/jquery-1.7.2.js" type="text/javascript"></script>
    <script type="text/javascript">
        function exportExaminationStatistis() {
            var examinationId = jQuery("#examinationId").val();
            if(examinationId != undefined) {
                window.location.href = "${pageContext.request.contextPath}/backend/examinationstatisticdownload.html?examinationId=" + examinationId;
            }
        }
    </script>
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
            <div style="float: left; padding-right: 5px; padding-top: 5px; padding-left: 5px;">
            </div>
            <form action="${pageContext.request.contextPath}/backend/examinationstatistic.html" class="search_form" method="POST">
                <div class="search">
                    <span><label>调查问卷:</label>
                        <select id="examinationId" name="examinationId" class="text">
                            <c:forEach items="${examinations}" var="examination">
                                <option value="${examination.id}" <c:if test="${examination.id == examinationId}">selected="true"</c:if>>${examination.id} : ${examination.title}</option>
                            </c:forEach>
                        </select>
                    </span>
                    <input type="button" value="统计" onclick="this.form.submit();"/>
                    <input type="button" value="导出" onclick="exportExaminationStatistis();"/>
                </div>
            </form>

            <c:if test="${questions != null}">
                <table width="100%" cellpadding="0" cellspacing="0" class="list">
                    <tbody>
                        <c:forEach items="${questions}" var="question">
                            <tr class="r2">
                                <c:set var="turns" value="${!turns}"/>
                                <td colspan="2">&nbsp;&nbsp;问题${question.sequence}:${question.title}</td>
                            </tr>
                            <c:forEach items="${question.answers}" var="answer">
                                <c:if test="${answer.result != null && answer.result != ''}">
                                <tr>
                                    <td width="20%">&nbsp;&nbsp;${answer.answer}</td>
                                    <td>
                                        <c:if test="${question.totalAnswerTimes > 0}">
                                            <ch:percentage value="${answer.answerTimes*100/question.totalAnswerTimes}" parameter="total"/>
                                        </c:if>
                                        <c:if test="${question.totalAnswerTimes <= 0}">
                                            <ch:percentage value="0" parameter="total"/>
                                        </c:if>
                                        <img src="${pageContext.request.contextPath}/images/sta_bg.png" style="height:12px; width: ${total * 3}px;">
                                        总${answer.answerTimes}次, 占 ${total}%
                                    </td>
                                </tr>
                                </c:if>
                            </c:forEach>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </td>
    </tr>
</table>
</body>
</html>