<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<html>
<body>
<h1 align="left" style="font-size: medium;">
    <img alt="" src="${pageContext.request.contextPath}/theme/default/images/wen.png">
    试题标题：${examination.title}
    <br/>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;创建时间：${examination.createTime}
</h1>
<hr/>
<div style="height: 600px; overflow-y: scroll;">
    <table cellspacing="15"  >
        <c:forEach items="${questions}" var="question">
            <tr cellspacing="5">
                <td><b>问题${question.sequence} : ${question.title}</b></td>
            </tr>

            <tr>
                <td>
                    <c:forEach items="${question.answers}" var="answer">
                        <c:if test="${answer.result != null && answer.result != ''}">
                            <input type="checkbox"/> ${answer.sequence}: ${answer.result} &nbsp;&nbsp;&nbsp;&nbsp;
                        </c:if>
                    </c:forEach>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>


