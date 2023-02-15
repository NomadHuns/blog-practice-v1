<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ include file="../layout/header.jsp" %>
        <div class="container my-3">
            <ul class="nav nav-tabs">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="/admin/user">유저관리</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/admin/board">게시물관리</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/admin/reply">댓글관리</a>
                </li>
            </ul>
            <table class="table table-hover my-table">
                <thead>
                    <tr>
                        <th style="text-align: center;">번호</th>
                        <th>유저명</th>
                        <th>이메일</th>
                        <th>가입일자</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${userList}" var="user">
                    <tr>
                        <td style="text-align: center;" class="my-text-ellipsis">${user.id}</td>
                        <td class="my-text-ellipsis">${user.username}</td>
                        <td class="my-text-ellipsis">${user.email}</td>
                        <td class="my-text-ellipsis">${user.createdAt}</td>
                        <td><span class="badge bg-secondary">삭제</span></td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <%@ include file="../layout/footer.jsp" %>