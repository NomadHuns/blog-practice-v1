insert into user_tb(role, username, password, email, created_at) values('user', 'ssar', '1234', 'ssar@nate.com', now());
insert into user_tb(role, username, password, email, created_at) values('user', 'cos', '1234', 'cos@nate.com', now());
insert into user_tb(role, username, password, email, created_at) values('admin', 'alss', '1234', 'alss@nate.com', now());

insert into board_tb(title, content, thumbnail, user_id, created_at) values('첫번째 글입니다.', '첫번째 내용입니다','/images/dora.png', 1, now());
insert into board_tb(title, content, thumbnail, user_id, created_at) values('두번째 글입니다.', '두번째 내용입니다','/images/dora.png', 1, now());
insert into board_tb(title, content, thumbnail, user_id, created_at) values('세번째 글입니다.', '세번째 내용입니다','/images/dora.png', 1, now());
insert into board_tb(title, content, thumbnail, user_id, created_at) values('네번째 글입니다.', '네번째 내용입니다','/images/dora.png', 1, now());
insert into board_tb(title, content, thumbnail, user_id, created_at) values('다섯번째 글입니다.', '다섯번째 내용입니다', '/images/dora.png', 1, now());
insert into board_tb(title, content, thumbnail, user_id, created_at) values('여섯번째 글입니다.', '여섯번째 내용입니다', '/images/dora.png', 2, now());
insert into board_tb(title, content, thumbnail, user_id, created_at) values('일곱번째 글입니다.', '일곱번째 내용입니다', '/images/dora.png', 2, now());
insert into board_tb(title, content, thumbnail, user_id, created_at) values('여덟번째 글입니다.', '여덟번째 내용입니다', '/images/dora.png', 2, now());
insert into board_tb(title, content, thumbnail, user_id, created_at) values('아홉번째 글입니다.', '아홉번째 내용입니다', '/images/dora.png', 2, now());
insert into board_tb(title, content, thumbnail, user_id, created_at) values('열번째 글입니다.', '열번째 내용입니다', '/images/dora.png', 2, now());

insert into reply_tb(comment, user_id, board_id, created_at) values('1번째 댓글입니다.', 1, 1, now());
insert into reply_tb(comment, user_id, board_id, created_at) values('2번째 댓글입니다.', 1, 2, now());
insert into reply_tb(comment, user_id, board_id, created_at) values('3번째 댓글입니다.', 2, 1, now());
insert into reply_tb(comment, user_id, board_id, created_at) values('4번째 댓글입니다.', 2, 2, now());

insert into love_tb(user_id, board_id, created_at) values(1, 1, now());
insert into love_tb(user_id, board_id, created_at) values(1, 2, now());
insert into love_tb(user_id, board_id, created_at) values(1, 3, now());
insert into love_tb(user_id, board_id, created_at) values(2, 1, now());
insert into love_tb(user_id, board_id, created_at) values(2, 2, now());
insert into love_tb(user_id, board_id, created_at) values(2, 3, now());

commit; 