<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ include file="../layout/header.jsp" %>
        <div class="container my-3">
            <ul class="nav nav-tabs">
                <li class="nav-item">
                    <a class="nav-link" href="/admin/user">유저관리</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="/admin/board">게시물관리</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/admin/reply">댓글관리</a>
                </li>
            </ul>
            <table class="table table-hover my-table">
                <thead>
                    <tr>
                        <th style="text-align: center;">번호</th>
                        <th>제목</th>
                        <th>내용</th>
                        <th>작성일자</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${boardList}" var="board">
                        <tr>
                            <td style="text-align: center;" class="my-text-ellipsis">${board.id}</td>
                            <td class="my-text-ellipsis">${board.title}</td>
                            <td class="my-text-ellipsis">${board.content}</td>
                            <td class="my-text-ellipsis">${board.createdAt}</td>
                            <td><span class="badge bg-secondary">삭제</span></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <%@ include file="../layout/footer.jsp" %>