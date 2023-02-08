<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ include file="../layout/header.jsp" %>
        <div class="container my-3">
            <form>
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="Enter title" name="title" id="title"
                        value="${board.title}">
                </div>

                <div class="form-group">
                    <textarea class="form-control summernote" rows="5" id="content" name="content">
                    ${board.content}
                </textarea>
                </div>
                <button type="button" class="btn btn-primary" onclick="update(${board.id})">글수정완료</button>
            </form>

        </div>

        <script>
            $('.summernote').summernote({
                tabsize: 2,
                height: 400
            });



            function update(id) {
                
                let post = {
                    title: $("#title").val(),
                    content: $("#content").val(),
                }
                $.ajax({
                    type: "put",
                    url: "/board/" + id,
                    headers: {
                        'Content-type': 'application/json; charset=UTF-8',
                    },
                    data: JSON.stringify(post),
                    dataType : "json" // default : 응답의 mime 타입으로 유추함
                })
                    .done((res) => {
                        alert(res.msg);
                        location.href = "/board/" + id;
                    })
                    .fail((err) => {
                        alert(err.responseJSON.msg);
                    })
            }
        </script>

        <%@ include file="../layout/footer.jsp" %>