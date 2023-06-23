DROP TABLE Member CASCADE CONSTRAINTS;

CREATE TABLE Member
(
	userid varchar2(30),
	pwd varchar2(30) ,
	name varchar2(30) ,
	email varchar2(30),
	phone varchar2(15),
	provider varchar2(30),

	PRIMARY KEY (userid)
);

insert into member values('scott', '1234', '홍길동', 'scott@abc.com', '010-1234-1234' ,null);

select*from member
select*from board;

alter table board add replycnt number(5);