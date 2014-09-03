<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<html>
<body>
	<h1 align="left" style="font-size: medium;">
		<img alt=""	src="${pageContext.request.contextPath}/theme/default/images/wen.png">
		<c:if test="${question.id > 0}">
            编辑问题
        </c:if>
        <c:if test="${question.id <= 0}">
            添加问题
        </c:if>
	</h1>
	<hr />
	<form id="questionForm" action="${pageContext.request.contextPath}/backend/questionform.html?method=save" method="POST">
		<input type="hidden" name="questionId" value="${question.id}"/>
		<input type="hidden" name="examinationId" value="${examinationId}"/>
        <input type="hidden" name="filterName" value="${filterName}"/>
        <input type="hidden" name="current" value="${current}"/>
		<table >
			<tr>
                <td>问题名称:</td>
                <td>
                    <input type="text" id="title" name="title" value="${question.title}" style="width: 400px; height: 25px;"/>
                </td>
            </tr>
            <tr>
                <td>问题类型:</td>
                <td>
                    <select name="questionType" style="width: 400px; height: 25px;">
                        <option value="SINGLE" <c:if test="${question.questionType == 'SINGLE'}">selected="true"</c:if>>单选</option>
                        <option value="MUTI" <c:if test="${question.questionType == 'MUTI'}">selected="true"</c:if>>多选</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>选项A:</td>
                <td>
                    <input type="text" id="ASelection" name="ASelection" value="${question.selectionA}" style="width: 250px; height: 25px;"/>
                </td>
            </tr>
            <tr>
                <td>选项B:</td>
                <td>
                    <input type="text" id="BSelection" name="BSelection" value="${question.selectionB}" style="width: 250px; height: 25px;"/>
                </td>
            </tr>
            <tr>
                <td>选项C:</td>
                <td>
                    <input type="text" id="CSelection" name="CSelection" value="${question.selectionC}" style="width: 250px; height: 25px;"/>
                </td>
            </tr>
            <tr>
                <td>选项D:</td>
                <td>
                    <input type="text" id="DSelection" name="DSelection" value="${question.selectionD}" style="width: 250px; height: 25px;"/>
                </td>
            </tr>
			<tr>
                <td></td>
                <td><input type="button" value="保存" onclick="saveQuestionForm();"></td>
            </tr>
		</table>
	</form>
</body>
</html>