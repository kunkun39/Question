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
            <spring-form:form commandName="examination" method="POST" cssClass="form">
                <table cellspacing="0" width="100%">
                    <input type="hidden" name="id" value="${examination.id}"/>
                    <input type="hidden" name="filterTitle" value="${filterTitle}"/>

                    <tr>
                        <td width="200px;">
                             问卷标题 <span class="required">*</span>
                        </td>
                        <td>
                            <spring-form:input path="title" maxlength="60" cssStyle="width:300px;"/>&nbsp;
                            <spring-form:errors path="title" cssClass="required"/>
                        </td>
                    </tr>

                    <tr>
                        <td width="200px;">
                             问卷使用范围 <span class="required">*</span>
                        </td>
                        <td>
                            <select name="examinationType" style="width: 310px; height: 30px">
                                <option value="BOTH" <c:if test="${examination.examinationType == 'BOTH'}">selected="true"</c:if>>只应用于手机助手</option>
                                <option value="TVBOX_ONLY" <c:if test="${examination.examinationType == 'TVBOX_ONLY'}">selected="true"</c:if>>应用于手机助手和电视</option>
                            </select>
                            手机助手可以支持单选，多选和主观题，而电视只支持单选和多选题！
                        </td>
                    </tr>

                    <tr>
                        <td width="200px;" valign="top">
                             问卷说明
                        </td>
                        <td>
                            <spring-form:textarea path="description" cols="80" rows="8"/>&nbsp;
                            <button type="button" class="thoughtbotform" onclick="window.location.href='${pageContext.request.contextPath}/backend/examinationoverview.html?filterTitle=${filterTitle}&current=${current}'">
                                返回
                            </button>
                        	<button name="" type="submit" class="thoughtbotform" onclick="javascript: return validate();">保存</button>

                        </td>
                    </tr>
                </table>
            </spring-form:form>

            <c:if test="${examination.id > 0}">
                <br/>
                &nbsp;<button class="thoughtbotform" onclick="previewExamination('${examination.id}')">预览</button>
                &nbsp;<button name="" type="submit" class="thoughtbotform" onclick="openQuestionForm('-1', 'add')">添加问题</button>
                <br/>

                <table width="100%" cellpadding="0" cellspacing="0" class="list">
                    <thead>
                        <td width="4%">&nbsp;排序</td>
                        <td width="1%">&nbsp;&nbsp;</td>
                        <td width="5%">编号</td>
                        <td width="8%">类型</td>
                        <td width="30%">问题</td>
                        <td width="35%">答案</td>
                        <td>操作</td>
                    </thead>
                    <tbody>
                        <c:set var="turns" value="true"/>
                        <c:forEach items="${questions}" var="question" varStatus="status">
                            <c:set var="color" value="${turns ? 'r1' :'r2'}"/>
                            <c:set var="isFirst" value="${status.first}"/>
                            <c:set var="isLast" value="${status.last}"/>

                            <tr class="${color}" onmouseover="this.className='over'" onmouseout="this.className='${color}'">
                            <c:set var="turns" value="${!turns}"/>
                                <td >
                                    <c:if test="${!isFirst}">
                                        <a href="${pageContext.request.contextPath}/backend/questionsequencechange.html?examinationId=${examination.id}&sequence=${question.sequence}&sorting=up">
                                            <img src="${pageContext.request.contextPath}/images/up.gif" align="left"/>
                                        </a>
                                    </c:if>

                                    <c:if test="${!isLast}">
                                        <a href="${pageContext.request.contextPath}/backend/questionsequencechange.html?examinationId=${examination.id}&sequence=${question.sequence}&sorting=down">
                                            <img src="${pageContext.request.contextPath}/images/down.gif" align="right"/>
                                        </a>
                                    </c:if>
                                </td>
                                <td></td>
                                <td>${question.sequence}</td>
                                <td>
                                    <c:if test="${question.questionType == 'MUTI'}">
                                        多选题
                                    </c:if>
                                    <c:if test="${question.questionType == 'SINGLE'}">
                                        单选题
                                    </c:if>
                                    <c:if test="${question.questionType == 'OBJECTIVE'}">
                                        主观题
                                    </c:if>
                                </td>
                                <td>${question.title}</td>
                                <td>${question.answerDescription} </td>
                                <td>
                                    <a href="#"><button class="thoughtbot" onclick="openQuestionForm('${question.id}', 'add')">编辑</button></a>

                                    <a href="#" onclick="return questionDeleteConfirm('${question.id}');"><button class="thoughtbot">删除</button></a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </td>
    </tr>
</table>

<script type="text/javascript">
    var settings_question = {
        align : 'center',									//Valid values, left, right, center
        top : 150, 											//Use an integer (in pixels)
        width : 600, 										//Use an integer (in pixels)
        padding : 10,										//Use an integer (in pixels)
        backgroundColor : 'white', 						    //Use any hex code
        source : '', 				    					//Refer to any page on your server, external pages are not valid e.g. http://www.google.co.uk
        borderColor : '#333333', 							//Use any hex code
        borderWeight : 4,									//Use an integer (in pixels)
        borderRadius : 5, 									//Use an integer (in pixels)
        fadeOutTime : 300, 									//Use any integer, 0 : no fade
        disableColor : '#666666', 							//Use any hex code
        disableOpacity : 40, 								//Valid range 0-100
        loadingImage : '${pageContext.request.contextPath}/js/popup/loading.gif'
    };

    jQuery.ready(function() {
    });

    function openQuestionForm(questionId, method) {
		settings_question.source = '${pageContext.request.contextPath}/backend/questionform.html?method=' + method + '&questionId=' + questionId + '&examinationId=${examination.id}&filterTitle=${filterTitle}&current=${current}';
		openModalPopup(settings_question);
	}

	function openModalPopup(obj) {
		modalPopup(obj.align, obj.top, obj.width, obj.padding, obj.disableColor, obj.disableOpacity, obj.backgroundColor, obj.borderColor, obj.borderWeight, obj.borderRadius, obj.fadeOutTime, obj.source, obj.loadingImage);
	}

    function saveQuestionForm() {
        var questionType = jQuery("#questionType").val();
        var questionTitle = jQuery("#questionTitle").val();
        if(questionType != 'OBJECTIVE') {
            var ASelection = jQuery("#ASelection").val();
            var BSelection = jQuery("#BSelection").val();
            if(questionTitle == null || questionTitle == '' || ASelection == null || ASelection == '' || BSelection == null || BSelection == '') {
                alert("请填入必要的信息!");
            } else {
                jQuery("#questionForm").submit();
            }
        } else {
            if(questionTitle == null || questionTitle == '') {
                alert("请填入必要的信息!");
            } else {
                jQuery("#questionForm").submit();
            }
        }
    }

    function questionDeleteConfirm(questionId) {
        if(confirm('确定要删除该问题吗?')) {
            window.location.href = '${pageContext.request.contextPath}/backend/questiondelete.html?questionId=' + questionId + '&examinationId=${examination.id}&filterTitle=${filterTitle}&current=${current}';
        }
    }

    var settings_preview = {
        align : 'center',									//Valid values, left, right, center
        top : 50, 											//Use an integer (in pixels)
        width : 800, 										//Use an integer (in pixels)
        padding : 10,										//Use an integer (in pixels)
        backgroundColor : 'white', 						    //Use any hex code
        source : '', 				    					//Refer to any page on your server, external pages are not valid e.g. http://www.google.co.uk
        borderColor : '#333333', 							//Use any hex code
        borderWeight : 4,									//Use an integer (in pixels)
        borderRadius : 5, 									//Use an integer (in pixels)
        fadeOutTime : 300, 									//Use any integer, 0 : no fade
        disableColor : '#666666', 							//Use any hex code
        disableOpacity : 40, 								//Valid range 0-100
        loadingImage : '${pageContext.request.contextPath}/js/popup/loading.gif'
    };

    function previewExamination(examinationId) {
        settings_preview.source = '${pageContext.request.contextPath}/backend/previewexamination.html?examinationId=${examination.id}';
		openModalPopup(settings_preview);
    }
</script>

</body>
</html>