
/* Drop Tables */

DROP TABLE ban_table CASCADE CONSTRAINTS;
DROP TABLE boardimage_table CASCADE CONSTRAINTS;
DROP TABLE commentLike_table CASCADE CONSTRAINTS;
DROP TABLE board_comment CASCADE CONSTRAINTS;
DROP TABLE board_location CASCADE CONSTRAINTS;
DROP TABLE like_table CASCADE CONSTRAINTS;
DROP TABLE board_table CASCADE CONSTRAINTS;
DROP TABLE chatbot_table CASCADE CONSTRAINTS;
DROP TABLE dm_message CASCADE CONSTRAINTS;
DROP TABLE dm_table CASCADE CONSTRAINTS;
DROP TABLE flightreservation_table CASCADE CONSTRAINTS;
DROP TABLE follow_table CASCADE CONSTRAINTS;
DROP TABLE member_info CASCADE CONSTRAINTS;
DROP TABLE notice_table CASCADE CONSTRAINTS;
DROP TABLE profileimage_table CASCADE CONSTRAINTS;
DROP TABLE push_table CASCADE CONSTRAINTS;
DROP TABLE reservation_table CASCADE CONSTRAINTS;
DROP TABLE plan_table CASCADE CONSTRAINTS;
DROP TABLE schedule_table CASCADE CONSTRAINTS;
DROP TABLE member CASCADE CONSTRAINTS;




/* Create Tables */

CREATE TABLE ban_table
(
	-- 벤 여부 고유번호
	ban_no number NOT NULL,
	-- 벤 날짜
	ban_createtime date DEFAULT SYSDATE,
	-- 고유값:아이디
	id varchar2(100) NOT NULL,
	PRIMARY KEY (ban_no)
);


CREATE TABLE boardimage_table
(
	-- 게시판이미지테이블 고유번호
	boardimage_no number NOT NULL,
	-- 파일명
	boardimage_filename varchar2(100),
	-- 게시판 이미지 사이즈
	boardimage_size number,
	-- 게시판 이미지 경로
	boardimage_route varchar2(100),
	-- 게시판 고유번호
	board_no number NOT NULL,
	PRIMARY KEY (boardimage_no)
);


CREATE TABLE board_comment
(
	-- 댓글 테이블 고유번호
	comment_no number NOT NULL,
	-- 댓글 작성자
	comment_writer nvarchar2(100),
	-- 댓글 내용
	comment_content varchar2(100),
	-- 댓글 작성시간
	comment_createtime date DEFAULT SYSDATE,
	-- 게시판 고유번호
	board_no number NOT NULL,
	-- 고유값:아이디
	id varchar2(100) NOT NULL,
	PRIMARY KEY (comment_no)
);


CREATE TABLE board_location
(
	-- 게시판 지역테이블 고유번호
	location_no number NOT NULL,
	-- 지역이름
	location_name varchar2(150),
	-- 지역 주소
	location_address varchar2(200),
	-- 이미지사진 url
	location_imageurl varchar2(200),
	-- 지역 별점
	location_rating float,
	-- 플레이스 아이디
	location_placeid varchar2(150),
	-- 게시판 고유번호
	board_no number NOT NULL,
	PRIMARY KEY (location_no)
);


CREATE TABLE board_table
(
	-- 게시판 고유번호
	board_no number NOT NULL,
	-- 게시판 제목
	board_title varchar2(100),
	-- 게시판 내용
	board_content varchar2(500),
	-- 게시판 작성시간
	board_createtime date DEFAULT SYSDATE NOT NULL,
	-- 게시판 수정시간
	-- defulat null
	board_updatetime date DEFAULT null,
	-- 게시판 이미지 파일명
	board_imagefilename varchar2(100),
	-- 게시판 작성자
	board_writer varchar2(50) NOT NULL,
	-- 게시판 태그들
	board_tags varchar2(200),
	-- 고유값:아이디
	id varchar2(100) NOT NULL,
	PRIMARY KEY (board_no)
);


CREATE TABLE chatbot_table
(
	-- 챗봇테이블 고유번호
	chatbot_no number NOT NULL,
	-- 봇 설명
	chatbot_system varchar2(1000) NOT NULL,
	-- 지원(컴퓨터)
	chatbot_assistance nvarchar2(1000) NOT NULL,
	-- 사용자(역할)
	chatbot_role  NOT NULL,
	-- 사용자가 보낸 시간
	chatbot_sendtime date DEFAULT SYSDATE NOT NULL,
	-- 컴퓨터가 보낸 시간
	chatbot_recipienttime date DEFAULT SYSDATE NOT NULL,
	-- 고유값:아이디
	id varchar2(100) NOT NULL,
	PRIMARY KEY (chatbot_no)
);


CREATE TABLE commentLike_table
(
	-- 댓글 좋아요 고유번호
	commentLike_no number NOT NULL,
	-- 댓글 좋아요 선택한 아이디
	commentLike_id varchar2(100) NOT NULL,
	-- 댓글 좋아요 누른시간
	commentLike_createtime date DEFAULT SYSDATE,
	-- 댓글 테이블 고유번호
	comment_no number NOT NULL,
	PRIMARY KEY (commentLike_no)
);


CREATE TABLE dm_message
(
	-- dm 채널테이블 고유번호
	dm_no number NOT NULL,
	-- 보낸사람 아이디
	dm_sender_id varchar2(100) NOT NULL,
	-- DM내용
	dm_content varchar2(500),
	-- 보낸시간
	dm_sendtime date DEFAULT SYSDATE
);


CREATE TABLE dm_table
(
	-- dm 채널테이블 고유번호
	dm_no number NOT NULL,
	-- 보낸사람 아이디
	dm_sender_id varchar2(100) NOT NULL,
	-- 받은사람 아이디
	dm_recipient_id varchar2(100) NOT NULL,
	PRIMARY KEY (dm_no)
);


CREATE TABLE flightreservation_table
(
	-- 항공권 예약 테이블 고유번호
	flight_no number NOT NULL,
	-- 상품명
	flight_productname varchar2(100) NOT NULL,
	-- 출발 공항코드
	flight_depcode varchar2(30) NOT NULL,
	-- 도착 공항코드
	flight_arrivalairportcode varchar2(30) NOT NULL,
	-- 출발일자
	flight_departuredate date NOT NULL,
	-- 운행시간
	flight_duration varchar2(100) NOT NULL,
	-- 좌석종류
	flight_seattype varchar2(50) NOT NULL,
	-- 좌석번호
	flight_seatnumber varchar2(50) NOT NULL,
	-- 상품가격
	flight_price number NOT NULL,
	-- 결제일
	flight_paymentdate date DEFAULT SYSDATE NOT NULL,
	-- 항공권 영수증 url
	flight_receipt varchar2(100) NOT NULL,
	-- 고유값:아이디
	id varchar2(100) NOT NULL,
	PRIMARY KEY (flight_no)
);


CREATE TABLE follow_table
(
	-- 팔로우 테이블 고유번호
	follow_no number NOT NULL,
	-- 보낸사람 아이디
	follow_senderid varchar2(100) NOT NULL,
	-- 받은사람 아이디
	follow_recipientid varchar2(100) NOT NULL,
	-- 팔로우 요청날짜
	follow_requesttime date DEFAULT SYSDATE NOT NULL,
	-- 팔로우 여부
	follow_is char(1) DEFAULT 'F' NOT NULL,
	-- 팔로우 수락시간
	follow_responsetime date,
	-- 고유값:아이디
	id varchar2(100) NOT NULL,
	PRIMARY KEY (follow_no)
);


CREATE TABLE like_table
(
	-- 댓글 좋아요 고유번호
	commentLike_no number NOT NULL,
	-- 댓글 좋아요 선택한 아이디
	commentLike_id varchar2(100) NOT NULL,
	-- 댓글 좋아요 누른시간
	commentLike_createtime date DEFAULT SYSDATE,
	-- 게시판 고유번호
	board_no number NOT NULL,
	PRIMARY KEY (commentLike_no)
);


CREATE TABLE member
(
	-- 고유값:아이디
	id varchar2(100) NOT NULL,
	-- 비밀번호 : default 1 (소셜로그인)
	pwd varchar2(100) DEFAULT '1' NOT NULL,
	-- 닉네임 (이름)
	name varchar2(20) NOT NULL,
	-- 권한
	authority varchar2(20) NOT NULL,
	-- 카카오,구글,wit
	provider varchar2(255) NOT NULL,
	-- 아이디 생성날짜
	createtime date DEFAULT SYSDATE NOT NULL,
	-- 최근 로그인 시간
	recentlogintime date DEFAULT SYSDATE NOT NULL,
	-- 프로필 이미지 URL
	-- 기본값 : 0 (미지정)
	profileimage varchar2(300) DEFAULT '0' NOT NULL,
	PRIMARY KEY (id)
);


CREATE TABLE member_info
(
	-- 회원정보 테이블 고유번호
	memberinfo_no number NOT NULL,
	-- MBTI 저장 컬럼
	mbti varchar2(10),
	-- 전화번호
	phonenumber varchar2(35) NOT NULL,
	-- F 는 여자 M은 남자 
	gender nchar(1),
	-- 주소
	address varchar2(100),
	-- 입력한 주소 위도
	lat number,
	-- 입력한 주소 경도
	lng number,
	-- 고유값:아이디
	id varchar2(100) NOT NULL,
	PRIMARY KEY (memberinfo_no)
);


CREATE TABLE notice_table
(
	-- 공지사항 고유번호
	notice_no number NOT NULL,
	-- 공지사항 내용
	notice_content varchar2(500),
	-- 공지사항 등록시간
	notice_createtime date DEFAULT SYSDATE,
	-- 고유값:아이디
	id varchar2(100) NOT NULL,
	PRIMARY KEY (notice_no)
);


CREATE TABLE plan_table
(
	-- 일정 고유번호
	plan_no number NOT NULL,
	-- 일정 이름
	plan_name varchar2(100) NOT NULL,
	-- 일정 이미지
	plan_img varchar2(499) NOT NULL,
	-- 일정 플레이스아이디
	plan_placeid varchar2(100) NOT NULL,
	-- 일정 등록일자
	plan_registrationdate date DEFAULT SYSDATE NOT NULL,
	-- 스케줄 테이블 고유번호
	schedule_no number NOT NULL,
	PRIMARY KEY (plan_no)
);


CREATE TABLE profileimage_table
(
	-- 게시판이미지테이블 고유번호
	boardimage_no number NOT NULL,
	-- 파일명
	boardimage_filename varchar2(100),
	-- 게시판 이미지 사이즈
	boardimage_size number,
	-- 게시판 이미지 경로
	boardimage_route varchar2(100),
	-- 고유값:아이디
	id varchar2(100) NOT NULL,
	PRIMARY KEY (boardimage_no)
);


CREATE TABLE push_table
(
	-- 고유값:아이디
	id varchar2(100) NOT NULL,
	-- 계정 토큰(알림)
	push_token varchar2(100) NOT NULL,
	PRIMARY KEY (id)
);


CREATE TABLE reservation_table
(
	-- 항공권 예약 테이블 고유번호
	flight_no number NOT NULL,
	-- 예약테이블 위도
	reservation_lat number NOT NULL,
	-- 예약 테이블 경도
	reservation_lng number NOT NULL,
	-- 상품명
	flight_productname varchar2(100) NOT NULL,
	-- 상품가격
	flight_price number NOT NULL,
	-- 결제일
	flight_paymentdate date DEFAULT SYSDATE NOT NULL,
	-- 항공권 영수증 url
	flight_receipt varchar2(100) NOT NULL,
	-- 고유값:아이디
	id varchar2(100) NOT NULL,
	PRIMARY KEY (flight_no)
);


CREATE TABLE schedule_table
(
	-- 스케줄 테이블 고유번호
	schedule_no number NOT NULL,
	-- 스케줄 제목
	schedule_title varchar2(150) NOT NULL,
	-- 스케줄러 시작일자
	schedule_startdate date DEFAULT SYSDATE NOT NULL,
	-- 스케줄러 끝난일자
	schedule_enddate date DEFAULT SYSDATE NOT NULL,
	-- 고유값:아이디
	id varchar2(100) NOT NULL,
	PRIMARY KEY (schedule_no)
);



/* Create Foreign Keys */

ALTER TABLE commentLike_table
	ADD FOREIGN KEY (comment_no)
	REFERENCES board_comment (comment_no)
;


ALTER TABLE boardimage_table
	ADD FOREIGN KEY (board_no)
	REFERENCES board_table (board_no)
;


ALTER TABLE board_comment
	ADD FOREIGN KEY (board_no)
	REFERENCES board_table (board_no)
;


ALTER TABLE board_location
	ADD FOREIGN KEY (board_no)
	REFERENCES board_table (board_no)
;


ALTER TABLE like_table
	ADD FOREIGN KEY (board_no)
	REFERENCES board_table (board_no)
;


ALTER TABLE dm_message
	ADD FOREIGN KEY (dm_no)
	REFERENCES dm_table (dm_no)
;


ALTER TABLE ban_table
	ADD FOREIGN KEY (id)
	REFERENCES member (id)
;


ALTER TABLE board_comment
	ADD FOREIGN KEY (id)
	REFERENCES member (id)
;


ALTER TABLE board_table
	ADD FOREIGN KEY (id)
	REFERENCES member (id)
;


ALTER TABLE chatbot_table
	ADD FOREIGN KEY (id)
	REFERENCES member (id)
;


ALTER TABLE flightreservation_table
	ADD FOREIGN KEY (id)
	REFERENCES member (id)
;


ALTER TABLE follow_table
	ADD FOREIGN KEY (id)
	REFERENCES member (id)
;


ALTER TABLE member_info
	ADD FOREIGN KEY (id)
	REFERENCES member (id)
;


ALTER TABLE notice_table
	ADD FOREIGN KEY (id)
	REFERENCES member (id)
;


ALTER TABLE profileimage_table
	ADD FOREIGN KEY (id)
	REFERENCES member (id)
;


ALTER TABLE push_table
	ADD FOREIGN KEY (id)
	REFERENCES member (id)
;


ALTER TABLE reservation_table
	ADD FOREIGN KEY (id)
	REFERENCES member (id)
;


ALTER TABLE schedule_table
	ADD FOREIGN KEY (id)
	REFERENCES member (id)
;


ALTER TABLE plan_table
	ADD FOREIGN KEY (schedule_no)
	REFERENCES schedule_table (schedule_no)
;



/* Comments */

COMMENT ON COLUMN ban_table.ban_no IS '벤 여부 고유번호';
COMMENT ON COLUMN ban_table.ban_createtime IS '벤 날짜';
COMMENT ON COLUMN ban_table.id IS '고유값:아이디';
COMMENT ON COLUMN boardimage_table.boardimage_no IS '게시판이미지테이블 고유번호';
COMMENT ON COLUMN boardimage_table.boardimage_filename IS '파일명';
COMMENT ON COLUMN boardimage_table.boardimage_size IS '게시판 이미지 사이즈';
COMMENT ON COLUMN boardimage_table.boardimage_route IS '게시판 이미지 경로';
COMMENT ON COLUMN boardimage_table.board_no IS '게시판 고유번호';
COMMENT ON COLUMN board_comment.comment_no IS '댓글 테이블 고유번호';
COMMENT ON COLUMN board_comment.comment_writer IS '댓글 작성자';
COMMENT ON COLUMN board_comment.comment_content IS '댓글 내용';
COMMENT ON COLUMN board_comment.comment_createtime IS '댓글 작성시간';
COMMENT ON COLUMN board_comment.board_no IS '게시판 고유번호';
COMMENT ON COLUMN board_comment.id IS '고유값:아이디';
COMMENT ON COLUMN board_location.location_no IS '게시판 지역테이블 고유번호';
COMMENT ON COLUMN board_location.location_name IS '지역이름';
COMMENT ON COLUMN board_location.location_address IS '지역 주소';
COMMENT ON COLUMN board_location.location_imageurl IS '이미지사진 url';
COMMENT ON COLUMN board_location.location_rating IS '지역 별점';
COMMENT ON COLUMN board_location.location_placeid IS '플레이스 아이디';
COMMENT ON COLUMN board_location.board_no IS '게시판 고유번호';
COMMENT ON COLUMN board_table.board_no IS '게시판 고유번호';
COMMENT ON COLUMN board_table.board_title IS '게시판 제목';
COMMENT ON COLUMN board_table.board_content IS '게시판 내용';
COMMENT ON COLUMN board_table.board_createtime IS '게시판 작성시간';
COMMENT ON COLUMN board_table.board_updatetime IS '게시판 수정시간
defulat null';
COMMENT ON COLUMN board_table.board_imagefilename IS '게시판 이미지 파일명';
COMMENT ON COLUMN board_table.board_writer IS '게시판 작성자';
COMMENT ON COLUMN board_table.board_tags IS '게시판 태그들';
COMMENT ON COLUMN board_table.id IS '고유값:아이디';
COMMENT ON COLUMN chatbot_table.chatbot_no IS '챗봇테이블 고유번호';
COMMENT ON COLUMN chatbot_table.chatbot_system IS '봇 설명';
COMMENT ON COLUMN chatbot_table.chatbot_assistance IS '지원(컴퓨터)';
COMMENT ON COLUMN chatbot_table.chatbot_role IS '사용자(역할)';
COMMENT ON COLUMN chatbot_table.chatbot_sendtime IS '사용자가 보낸 시간';
COMMENT ON COLUMN chatbot_table.chatbot_recipienttime IS '컴퓨터가 보낸 시간';
COMMENT ON COLUMN chatbot_table.id IS '고유값:아이디';
COMMENT ON COLUMN commentLike_table.commentLike_no IS '댓글 좋아요 고유번호';
COMMENT ON COLUMN commentLike_table.commentLike_id IS '댓글 좋아요 선택한 아이디';
COMMENT ON COLUMN commentLike_table.commentLike_createtime IS '댓글 좋아요 누른시간';
COMMENT ON COLUMN commentLike_table.comment_no IS '댓글 테이블 고유번호';
COMMENT ON COLUMN dm_message.dm_no IS 'dm 채널테이블 고유번호';
COMMENT ON COLUMN dm_message.dm_sender_id IS '보낸사람 아이디';
COMMENT ON COLUMN dm_message.dm_content IS 'DM내용';
COMMENT ON COLUMN dm_message.dm_sendtime IS '보낸시간';
COMMENT ON COLUMN dm_table.dm_no IS 'dm 채널테이블 고유번호';
COMMENT ON COLUMN dm_table.dm_sender_id IS '보낸사람 아이디';
COMMENT ON COLUMN dm_table.dm_recipient_id IS '받은사람 아이디';
COMMENT ON COLUMN flightreservation_table.flight_no IS '항공권 예약 테이블 고유번호';
COMMENT ON COLUMN flightreservation_table.flight_productname IS '상품명';
COMMENT ON COLUMN flightreservation_table.flight_depcode IS '출발 공항코드';
COMMENT ON COLUMN flightreservation_table.flight_arrivalairportcode IS '도착 공항코드';
COMMENT ON COLUMN flightreservation_table.flight_departuredate IS '출발일자';
COMMENT ON COLUMN flightreservation_table.flight_duration IS '운행시간';
COMMENT ON COLUMN flightreservation_table.flight_seattype IS '좌석종류';
COMMENT ON COLUMN flightreservation_table.flight_seatnumber IS '좌석번호';
COMMENT ON COLUMN flightreservation_table.flight_price IS '상품가격';
COMMENT ON COLUMN flightreservation_table.flight_paymentdate IS '결제일';
COMMENT ON COLUMN flightreservation_table.flight_receipt IS '항공권 영수증 url';
COMMENT ON COLUMN flightreservation_table.id IS '고유값:아이디';
COMMENT ON COLUMN follow_table.follow_no IS '팔로우 테이블 고유번호';
COMMENT ON COLUMN follow_table.follow_senderid IS '보낸사람 아이디';
COMMENT ON COLUMN follow_table.follow_recipientid IS '받은사람 아이디';
COMMENT ON COLUMN follow_table.follow_requesttime IS '팔로우 요청날짜';
COMMENT ON COLUMN follow_table.follow_is IS '팔로우 여부';
COMMENT ON COLUMN follow_table.follow_responsetime IS '팔로우 수락시간';
COMMENT ON COLUMN follow_table.id IS '고유값:아이디';
COMMENT ON COLUMN like_table.commentLike_no IS '댓글 좋아요 고유번호';
COMMENT ON COLUMN like_table.commentLike_id IS '댓글 좋아요 선택한 아이디';
COMMENT ON COLUMN like_table.commentLike_createtime IS '댓글 좋아요 누른시간';
COMMENT ON COLUMN like_table.board_no IS '게시판 고유번호';
COMMENT ON COLUMN member.id IS '고유값:아이디';
COMMENT ON COLUMN member.pwd IS '비밀번호 : default 1 (소셜로그인)';
COMMENT ON COLUMN member.name IS '닉네임 (이름)';
COMMENT ON COLUMN member.authority IS '권한';
COMMENT ON COLUMN member.provider IS '카카오,구글,wit';
COMMENT ON COLUMN member.createtime IS '아이디 생성날짜';
COMMENT ON COLUMN member.recentlogintime IS '최근 로그인 시간';
COMMENT ON COLUMN member.profileimage IS '프로필 이미지 URL
기본값 : 0 (미지정)';
COMMENT ON COLUMN member_info.memberinfo_no IS '회원정보 테이블 고유번호';
COMMENT ON COLUMN member_info.mbti IS 'MBTI 저장 컬럼';
COMMENT ON COLUMN member_info.phonenumber IS '전화번호';
COMMENT ON COLUMN member_info.gender IS 'F 는 여자 M은 남자 ';
COMMENT ON COLUMN member_info.address IS '주소';
COMMENT ON COLUMN member_info.lat IS '입력한 주소 위도';
COMMENT ON COLUMN member_info.lng IS '입력한 주소 경도';
COMMENT ON COLUMN member_info.id IS '고유값:아이디';
COMMENT ON COLUMN notice_table.notice_no IS '공지사항 고유번호';
COMMENT ON COLUMN notice_table.notice_content IS '공지사항 내용';
COMMENT ON COLUMN notice_table.notice_createtime IS '공지사항 등록시간';
COMMENT ON COLUMN notice_table.id IS '고유값:아이디';
COMMENT ON COLUMN plan_table.plan_no IS '일정 고유번호';
COMMENT ON COLUMN plan_table.plan_name IS '일정 이름';
COMMENT ON COLUMN plan_table.plan_img IS '일정 이미지';
COMMENT ON COLUMN plan_table.plan_placeid IS '일정 플레이스아이디';
COMMENT ON COLUMN plan_table.plan_registrationdate IS '일정 등록일자';
COMMENT ON COLUMN plan_table.schedule_no IS '스케줄 테이블 고유번호';
COMMENT ON COLUMN profileimage_table.boardimage_no IS '게시판이미지테이블 고유번호';
COMMENT ON COLUMN profileimage_table.boardimage_filename IS '파일명';
COMMENT ON COLUMN profileimage_table.boardimage_size IS '게시판 이미지 사이즈';
COMMENT ON COLUMN profileimage_table.boardimage_route IS '게시판 이미지 경로';
COMMENT ON COLUMN profileimage_table.id IS '고유값:아이디';
COMMENT ON COLUMN push_table.id IS '고유값:아이디';
COMMENT ON COLUMN push_table.push_token IS '계정 토큰(알림)';
COMMENT ON COLUMN reservation_table.flight_no IS '항공권 예약 테이블 고유번호';
COMMENT ON COLUMN reservation_table.reservation_lat IS '예약테이블 위도';
COMMENT ON COLUMN reservation_table.reservation_lng IS '예약 테이블 경도';
COMMENT ON COLUMN reservation_table.flight_productname IS '상품명';
COMMENT ON COLUMN reservation_table.flight_price IS '상품가격';
COMMENT ON COLUMN reservation_table.flight_paymentdate IS '결제일';
COMMENT ON COLUMN reservation_table.flight_receipt IS '항공권 영수증 url';
COMMENT ON COLUMN reservation_table.id IS '고유값:아이디';
COMMENT ON COLUMN schedule_table.schedule_no IS '스케줄 테이블 고유번호';
COMMENT ON COLUMN schedule_table.schedule_title IS '스케줄 제목';
COMMENT ON COLUMN schedule_table.schedule_startdate IS '스케줄러 시작일자';
COMMENT ON COLUMN schedule_table.schedule_enddate IS '스케줄러 끝난일자';
COMMENT ON COLUMN schedule_table.id IS '고유값:아이디';



