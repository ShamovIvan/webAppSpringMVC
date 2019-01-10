<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Parts Page</title>

    <style type="text/css">
        .tg {
            border-collapse: collapse;
            border-spacing: 0;
            border-color: #20a088;
            margin-left: 75px;
        }

        .tg td {
            font-family: "Times New Roman", sans-serif;
            font-size: 15px;
            padding: 10px 5px;
            border-style: solid;
            border-width: 1px;
            overflow: hidden;
            word-break: normal;
            border-color: #b2d1a6;
            color: #155654;
            background-color: #e9ffcf;

        }

        .tg th {
            font-family: "Times New Roman", sans-serif;
            font-size: 15px;
            font-weight: normal;
            padding: 10px 5px;
            border-style: solid;
            border-width: 1px;
            overflow: hidden;
            word-break: normal;
            border-color: #19cc95;
            color: #35484e;
            background-color: #bcddaf;

        }
        .yy{
            margin-left: 74px;
        }
        .uu{
            padding: 5px
           <%-- margin-right:450px ;
            text-align: right; --%>
        }
        .ii{
            font-size: 20px;
            color: #155654;
        }

        h1{
            color: #035447;
        }
        span{
            color: #035447;
        }

        h2{
            color: #085a4b;
        }
        a{
            color: #328072;
        }
        a:hover{
            color: #b3bf2f;
        }
        a:active{

            color: #07bf7c
        }
        a:visited{
            color: #418598;
        }


    </style>
    <style type="text/css">
        .mycss{
            background-color: #e9ffcf;
            color: #35484e;
        }
    </style>
    <style type="text/css">
        .mycss2{
            color: #35484e;
        }
    </style>
</head>
<body>
<div class="yy">
    <a href="../../index.jsp"  class="mycss2" >   Back to main menu </a>
    <br/> <br/> <br/>
    <form action="/parts">
       <span> Show </span>
        <button name="param" type="submit" value="all">All details</button>
        <button name="param" type="submit" value="needOnly">Need</button>
        <button name="param" type="submit" value="optionOnly">Option</button>
    </form>
    <br/> <br/>

    <span>Search a Part by Name</span>
    <c:url value="/search" var="searchAction" />
    <form:form action="${searchAction}"  method="POST" >
        <label >
            <input type="text" name="partname"/>
        </label>
        <input type="submit" value="search" /> </form:form>

    <h1 >Part List</h1>
    <c:if test="${!empty listParts}">
        <table class="tg"  >
            <tr>
                <th width="40">ID</th>
                <th width="160">Name</th>
                <th width="60">Number</th>
                <th width="80">Is Need</th>
                <th width="60">Edit</th>
                <th width="60">Delete</th>
            </tr>
            <tbody id="target">
            <c:forEach items="${listParts}" var="part">
                <tr>
                    <td align="center">${part.id}</td>
                    <td><a href="/partdata/${part.id}" target="_blank">${part.name}</a></td>
                    <td align="center">${part.number}</td>
                    <td align="center">${part.isNeed}</td>
                    <td align="center"><a href="<c:url value='/edit/${part.id}'/>">Edit</a></td>
                    <td align="center"><a href="<c:url value='/remove/${part.id}'/>">Delete</a></td>
                </tr>
            </c:forEach>
            <tr>
                <td colspan="6" align="center">Data part list</td>
            </tr>
            <tr>
                <td colspan="2" align="center">Can make computers</td>
                <td colspan="2" align="center">${availableComputers}</td>
                <td colspan="2" align="center">computers</td>
            </tr>
            </tbody>
        </table>
    </c:if>

    <div align="center" id="pagination" class="uu">
        <p>Pagination: </p>

        <c:url value="/parts" var="prev">
            <c:param name="page" value="${page-1}"/>
        </c:url>
        <c:if test="${page > 1}">
            <a href="<c:out value="${prev}" />" class="pn prev">Prev</a>
        </c:if>

        <c:forEach begin="1" end="${maxPages}" step="1" varStatus="i">
            <c:choose>
                <c:when test="${page == i.index}">
                    <span>${i.index}</span>
                </c:when>
                <c:otherwise>
                    <c:url value="/parts" var="url">
                        <c:param name="page" value="${i.index}"/>
                    </c:url>
                    <a href='<c:out value="${url}" />'>${i.index}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <c:url value="/parts" var="next">
            <c:param name="page" value="${page + 1}"/>
        </c:url>
        <c:if test="${page + 1 <= maxPages}">
            <a href='<c:out value="${next}" />' class="pn next">Next</a>
        </c:if>
    </div>

    <c:if test="${empty listParts}">
        <p class="ii">The Parts list is empty!  Please add part</p>
    </c:if>

    <table>
        <tr>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <td width="50%">

                <h1>Add a Part</h1>
                <c:url var="addAction" value="/parts/add"/>
                <form:form action="${addAction}" modelAttribute="part">
                    <table>
                        <c:if test="${!empty part.name}">
                            <tr>
                                <td>
                                    <form:label path="id"  cssClass="mycss2">
                                        <spring:message text="ID"/>
                                    </form:label>
                                </td>
                                <td>
                                    <form:input path="id" readonly="true" size="8" disabled="true" cssClass="mycss"/>
                                    <form:hidden path="id"/>
                                </td>
                            </tr>
                        </c:if>
                        <tr>
                            <td>
                                <form:label path="name" cssClass="mycss2">
                                    <spring:message text="Name"/>
                                </form:label>
                            </td>
                            <td>
                                <form:input path="name" cssClass="mycss"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <form:label path="number" cssClass="mycss2">
                                    <spring:message text="Number"/>
                                </form:label>
                            </td>
                            <td>
                                <form:input path="number" cssClass="mycss"/>
                            </td>
                        </tr>
                        <tr>
                            <td><form:label path="isNeed" cssClass="mycss2">
                                <spring:message text="IsNeed" />
                            </form:label>
                            </td>
                            <td>
                                <form:input path="isNeed" cssClass="mycss"/>
                            </td>
                        </tr>
                        <tr>

                        </tr>
                        <tr>
                            <td colspan="2">
                                <c:if test="${!empty part.name}">
                                    <input type="submit"
                                           value="<spring:message text="Edit Part" />" class="mycss"/>
                                </c:if>
                                <c:if test="${empty part.name}">
                                    <input type="submit"
                                           value="<spring:message text="Add Part"/>" class="mycss"/>
                                </c:if>
                            </td>
                        </tr>
                    </table>

                </form:form>
            </td>

            <td></td>
        </tr>
    </table>

</div>

</body>
</html>