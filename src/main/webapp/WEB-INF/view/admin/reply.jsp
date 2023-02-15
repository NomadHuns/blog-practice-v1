<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ include file="../layout/header.jsp" %>
        <div class="container my-3">
            <ul class="nav nav-tabs">
                <li class="nav-item">
                    <a class="nav-link" href="/admin/user">유저관리</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/admin/board">게시물관리</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="/admin/reply">댓글관리</a>
                </li>
            </ul>
            <table class="table table-hover my-table">
                <thead>
                    <tr>
                        <th style="text-align: center;">번호</th>
                        <th>작성자</th>
                        <th>댓글내용</th>
                        <th>작성일자</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${replyList}" var="reply">
                        <tr id="reply-${reply.id}">
                            <td style="text-align: center;" class="my-text-ellipsis">${reply.id}</td>
                            <td class="my-text-ellipsis">${reply.username}</td>
                            <td class="my-text-ellipsis">${reply.comment}</td>
                            <td class="my-text-ellipsis">${reply.createdAt}</td>
                            <td><button onclick="deleteById(${reply.id})" class="badge bg-secondary">삭제</span></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <script>
            function deleteById(id) {
                $.ajax({
                    type: "delete",
                    url: "/admin/reply/" + id,
                    dataType: "json",
                })
                    .done((res) => { // 20X일 때
                        alert(res.msg);
                        $("#reply-" + id).remove();
                    })
                    .fail((err) => { // 40X, 50X일 때
                        alert(err.responseJSON.msg);
                    })
            }
        </script>
        <%@ include file="../layout/footer.jsp" %>