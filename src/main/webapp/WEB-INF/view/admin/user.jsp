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
                    <tr>
                        <td style="text-align: center;" class="my-text-ellipsis">1</td>
                        <td class="my-text-ellipsis">Doe</td>
                        <td class="my-text-ellipsis">john@example.com</td>
                        <td class="my-text-ellipsis">2022.05.13</td>
                        <td><span class="badge bg-secondary">삭제</span></td>
                    </tr>
                    <tr>
                        <td style="text-align: center;" class="my-text-ellipsis">2</td>
                        <td class="my-text-ellipsis">Moe</td>
                        <td class="my-text-ellipsis">mary@example.com</td>
                        <td class="my-text-ellipsis">2022.05.13</td>
                        <td><span class="badge bg-secondary">삭제</span></td>
                    </tr>
                    <tr>
                        <td style="text-align: center;" class="my-text-ellipsis">3</td>
                        <td class="my-text-ellipsis">Dooley</td>
                        <td class="my-text-ellipsis">july@example.com</td>
                        <td class="my-text-ellipsis">2022.05.13</td>
                        <td><span class="badge bg-secondary">삭제</span></td>
                    </tr>
                </tbody>
            </table>
        </div>
        <%@ include file="../layout/footer.jsp" %>