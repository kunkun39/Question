<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="ch" uri="http://www.chanhong.com" %>
<html>
<head>
    <title>川网用户反馈平台</title>
    <script type="text/javascript">
        function examinationDeleteConfirm() {
            return confirm('确定要删除该问卷调查吗?');
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
                <a href="${pageContext.request.contextPath}/backend/examinationform.html?filterTitle=${filterTitle}&current=${current}"><button class="thoughtbot">添加问卷</button></a>
            </div>
            <form action="${pageContext.request.contextPath}/backend/examinationoverview.html" class="search_form" method="POST">
                <div class="search">
                    <span><label>标题:</label><input type="text" name="filterTitle" class="text" value="${paging.filterTitle}"/></span>
                    <input type="button" value="查询" onclick="this.form.submit();"/>
                </div>
            </form>

            <table width="100%" cellpadding="0" cellspacing="0" class="list">
                <thead>
                <td width="5%">&nbsp;&nbsp;编号</td>
                <td width="10%">创建时间</td>
                <td width="10%">应用范围</td>
                <td width="50%">标题</td>
                <td width="10%">状态</td>
                <td>操作</td>
                </thead>
                <tbody>
                <c:set var="turns" value="true"/>
                <c:forEach items="${examinations}" var="examination">
                    <c:set var="color" value="${turns ? 'r1' :'r2'}"/>
                    <tr class="${color}" onmouseover="this.className='over'" onmouseout="this.className='${color}'">
                    <c:set var="turns" value="${!turns}"/>
                        <td>&nbsp;&nbsp;${examination.id}</td>
                        <td>${examination.createTime}</td>
                        <td>
                            <c:if test="${examination.examinationType == 'BOTH'}">
                                应用于手机和电视
                            </c:if>
                            <c:if test="${examination.examinationType == 'TVBOX_ONLY'}">
                                只应用于电视
                            </c:if>
                        </td>
                        <td>${examination.title} </td>
                        <td>
                            <c:if test="${examination.published}">
                                已发布
                            </c:if>
                            <c:if test="${!examination.published}">
                                未发布
                            </c:if>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/backend/examinationform.html?examinationId=${examination.id}&filterTitle=${filterTitle}&current=${current}"><button class="thoughtbot">编辑</button></a>
                            <%--<a href="${pageContext.request.contextPath}/backend/examinationdelete.html?examinationId=${examination.id}&filterTitle=${filterTitle}&current=${current}" onclick="return examinationDeleteConfirm();"><button class="thoughtbot">删除</button></a>--%>
                            <c:if test="${examination.published}">
                                <a href="${pageContext.request.contextPath}/backend/examinationpublished.html?examinationId=${examination.id}&filterTitle=${filterTitle}&current=${current}"><button class="thoughtbot">停止</button></a>
                            </c:if>
                            <c:if test="${!examination.published}">
                                <a href="${pageContext.request.contextPath}/backend/examinationpublished.html?examinationId=${examination.id}&filterTitle=${filterTitle}&current=${current}"><button class="thoughtbot">发布</button></a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <div class="paging">
                <ch:paging urlMapping="${pageContext.request.contextPath}/backend/examinationoverview.html" showGoTo="false" paging="${paging}"/>
            </div>
        </td>
    </tr>
</table>
</body>
</html>