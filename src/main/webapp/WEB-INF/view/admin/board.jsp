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
                <tbody id="tableBody">
                    <c:forEach items="${boardList}" var="board">
                        <tr id="board-${board.id}">
                            <td style="text-align: center;" class="my-text-ellipsis">${board.id}</td>
                            <td class="my-text-ellipsis">${board.title}</td>
                            <td class="my-text-ellipsis">${board.content}</td>
                            <td class="my-text-ellipsis">${board.createdAt}</td>
                            <td><button onclick="deleteById(${board.id})" class="badge bg-secondary">삭제</span></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div class="d-flex justify-content-center">
                <div style="width: 50%;">
                    <form class="d-flex" id="search">
                        <input id="searchString" class="form-control me-2" type="text" placeholder="검색">
                        <button id="searchButton" class="btn btn-primary" type="button"
                            onclick="search()">Search</button>
                    </form>
                </div>
            </div>
        </div>

        <script>
            function deleteById(id) {
                $.ajax({
                    type: "delete",
                    url: "/admin/board/" + id,
                    dataType: "json",
                })
                    .done((res) => { // 20X일 때
                        alert(res.msg);
                        $("#board-" + id).remove();
                    })
                    .fail((err) => { // 40X, 50X일 때
                        alert(err.responseJSON.msg);
                    })
            }
            function search() {
                let searchString = $("#searchString").val()
                $.ajax({
                    type: "post",
                    url: "/admin/board/search",
                    headers: {
                        'Content-type': 'text/plain; charset=UTF-8',
                    },
                    data: searchString,
                    dataType: "json",
                })
                    .done((res) => { // 20X일 때
                        $("#tableBody").empty();
                        for (let i = 0; i < res.data.length; i++) {
                            let el = `<tr id="board-` + res.data[i].id + `,">` +
                                `<td style="text-align: center;" class="my-text-ellipsis">` + res.data[i].id + `</td>` +
                                `<td class="my-text-ellipsis">` + res.data[i].title + `</td>` +
                                `<td class="my-text-ellipsis">` + res.data[i].content + `</td>` +
                                `<td class="my-text-ellipsis">` + res.data[i].createdAt + `</td>` +
                                `<td><button onclick="deleteById(` + res.data[i].id + `)" class="badge bg-secondary">삭제</span></td>
                                </tr>`;
                            $("#tableBody").append(el);
                        }
                    })
                    .fail((err) => { // 40X, 50X일 때
                        alert(err.responseJSON.msg);
                    })
            }
        </script>
        <%@ include file="../layout/footer.jsp" %>