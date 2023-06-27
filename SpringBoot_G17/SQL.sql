SELECT*FROM BANNER;
DELETE FROM BANNER WHERE BSEQ=25
update banner set image='w7.jpg' where bseq=24


drop table member cascade constraints;


create table member(
	id varchar2(20) not null,
	pwd varchar2(20),
	name varchar2(20),
	email varchar2(40) not null,
	zip_num varchar2(10),
	address1 varchar2(50),
	address2 varchar2(50),
	address3 varchar2(50),
	phone varchar2(20),
	indate date default sysdate,
	useyn char(1) default 'Y',
	provider varchar2(30),
	primary key(id)
);

insert into member(id, pwd, name, zip_num, address1, address2, phone, email) values
('one', '1111', '김나리', '133-110', '서울시 성동구 성수동1가' , '1번지21호', '017-777-7777','acc@abc.com');
insert into member(id, pwd, name, zip_num, address1, address2, phone, email)values
('two', '2222', '김길동', '130-120', '서울시 송파구 잠실2동' , '리센츠 아파트 201동 505호', '011-123-4567','acc@abc.com');
