<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ include file="../layout/header.jsp" %>
        <div class="container my-3">
            <div class="d-flex justify-content-center">
                <h1>회원 정보</h1>
            </div>
            <hr>
            <div class="d-flex">
                <img src="${user.profile == null ? '/images/profile.jfif' : user.profile}"
                    class="img-thumbnail" alt="Cinque Terre" style="width: 30%; height: 200px;">
                <table class="table table-bordered my-0">
                    <tbody>
                        <tr>
                            <th style="width: 20%;">등급</th>
                            <td style="width: 70%;" id="role">${user.role == "user" ? '일반회원' : '관리자'}</td>
                            <td class="d-flex justify-content-center"><button onclick="updateRoleBtn()" type="button"
                                    class="btn btn-primary btn-sm">수정</button></td>
                        </tr>
                        <tr>
                            <th style="width: 20%;">회원아이디</th>
                            <td style="width: 70%;" id="username">${user.username}</td>
                            <td class="d-flex justify-content-center"><button onclick="updateUsernameBtn()"
                                    type="button" class="btn btn-primary btn-sm">수정</button></td>
                        </tr>
                        <tr>
                            <th style="width: 20%;">이메일</th>
                            <td style="width: 70%;" id="email">${user.email}</td>
                            <td class="d-flex justify-content-center"><button onclick="updateEmailBtn()" type="button"
                                    class="btn btn-primary btn-sm">수정</button></td>
                        </tr>
                        <tr>
                            <th style="width: 20%;">가입날자</th>
                            <td style="width: 70%;">${user.createdAt}</td>
                            <td class="d-flex justify-content-center"><button onclick="deleteUserBtn(${user.id})"
                                    type="button" class="btn btn-primary btn-sm">탈퇴</button></td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <script>
            function deleteUserBtn(id) {
                $.ajax({
                    type: "delete",
                    url: "/admin/user/" + id,
                    dataType: "json",
                })
                    .done((res) => { // 20X일 때
                        alert(res.msg);
                        location.href("/admin/user");
                    })
                    .fail((err) => { // 40X, 50X일 때
                        alert(err.responseJSON.msg);
                    })
            }

            function updateRoleBtn() {
                if ($("#role").text() == '일반회원') {
                    $("#role").empty();
                    let el =
                        `<form> 
                        <select id="roleSelect" onchange="updateRoleSelect()">
                            <option selected>일반회원</option>
                            <option>관리자</option>
                        </select>
                    </form>`
                    $("#role").append(el);
                }
                if ($("#role").text() == "관리자") {
                    $("#role").empty();
                    let el =
                        `<form> 
                        <select id="roleSelect" onchange="updateRoleSelect()">
                            <option>일반회원</option>
                            <option selected>관리자</option>
                        </select>
                    </form>`
                    $("#role").append(el);
                }
            }

            function updateRoleSelect() {
                let role = $("#roleSelect").val();
                $("#role").empty();
                $("#role").prepend(role);
            }

            function updateUsernameBtn() {
                let username = $("#username").text();
                $("#username").empty();
                let el =
                    `<form onsubmit="updateUsernameInput()">
                    <input type="text" id="usernameInput" value="`+ username + `">
                </form>`
                $("#username").append(el);
            }

            function updateUsernameInput() {
                let username = $("#usernameInput").val();
                $("#username").empty();
                $("#username").prepend(username);
            }

            function updateEmailBtn() {
                let email = $("#email").text();
                $("#email").empty();
                let el =
                    `<form onsubmit="updateEmailInput()">
                    <input type="text" id="emailInput" value="`+ email + `">
                </form>`
                $("#email").append(el);
            }

            function updateEmailInput() {
                let username = $("#emailInput").val();
                $("#email").empty();
                $("#email").prepend(username);
            }  
        </script>
        <%@ include file="../layout/footer.jsp" %>