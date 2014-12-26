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
                <td valign="top">问题类型:</td>
                <td>
                    <select id="questionType" name="questionType" style="width: 405px; height: 30px;">
                        <option value="SINGLE" <c:if test="${question.questionType == 'SINGLE'}">selected="true"</c:if>>单选题</option>
                        <option value="MUTI" <c:if test="${question.questionType == 'MUTI'}">selected="true"</c:if>>多选题</option>
                        <option value="OBJECTIVE" <c:if test="${question.questionType == 'OBJECTIVE'}">selected="true"</c:if>>主观题</option>
                    </select>
                    <br/>
                    <br/>
                    主观题不用填写选项内容
                </td>
            </tr>

			<tr>
                <td>问题名称:</td>
                <td>
                    <input type="text" id="questionTitle" name="questionTitle" value="${question.title}" style="width: 400px; height: 25px;"/>
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
                <td>选项E:</td>
                <td>
                    <input type="text" id="ESelection" name="ESelection" value="${question.selectionE}" style="width: 250px; height: 25px;"/>
                </td>
            </tr>
              <tr>
                <td>选项F:</td>
                <td>
                    <input type="text" id="FSelection" name="FSelection" value="${question.selectionF}" style="width: 250px; height: 25px;"/>
                </td>
            </tr>
             <tr>
                <td>选项G:</td>
                <td>
                    <input type="text" id="GSelection" name="GSelection" value="${question.selectionG}" style="width: 250px; height: 25px;"/>
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