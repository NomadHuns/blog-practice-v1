<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ include file="../layout/header.jsp" %>
        <div class="container my-3">
            <div class="container">
                <form action="/join" method="post" onsubmit="return valid()">
                    <div class="form-group mb-2">
                        <input type="text" name="username" class="form-control" placeholder="Enter username"
                            id="username" required>
                    </div>

                    <div class="form-group mb-2">
                        <input type="password" name="password" class="form-control" placeholder="Enter password"
                            id="password" required onchange="checkSamePassword()">
                    </div>

                    <div class="form-group mb-2">
                        <input type="password" class="form-control" placeholder="Enter passwordCheck" id="passwordCheck"
                            required onchange="checkSamePassword()">
                    </div>

                    <div id="passwordCheckAlert">
                    </div>

                    <div class="form-group mb-2">
                        <input type="email" name="email" class="form-control" placeholder="Enter email" id="email"
                            required>
                    </div>

                    <button type="submit" class="btn btn-primary">회원가입</button>
                </form>

            </div>
        </div>

        <script>
            let checkPassword = false;

            function valid() {
                if (checkPassword == true) {
                    return true;
                }
                return false;
            }

            function checkSamePassword() {
                let password = $("#password").val();
                let passwordCheck = $("#passwordCheck").val();
                if (password == passwordCheck) {
                    checkPassword = true;
                    $("#passwordCheckAlert").empty();
                    let el = `<div class="alert alert-success" id="passwordCheckAlert">
                        <strong>비밀번호 확인 완료!</strong>
                        </div>`;
                    $("#passwordCheckAlert").append(el);
                } else {
                    checkPassword = false;
                    $("#passwordCheckAlert").empty();
                    let el = `<div class="alert alert-danger">
                              <strong>비밀번호가 다릅니다!</strong>
                              </div>`;
                    $("#passwordCheckAlert").append(el);
                }
            }
        </script>
        <%@ include file="../layout/footer.jsp" %>