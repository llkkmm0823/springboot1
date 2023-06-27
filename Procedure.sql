CREATE OR REPLACE PROCEDURE getBestNewBannerList(
    p_cur1 OUT SYS_REFCURSOR ,
    p_cur2 OUT SYS_REFCURSOR ,
    p_cur3 OUT SYS_REFCURSOR
    )
IS
BEGIN
    OPEN p_cur1 FOR
            SELECT * FROM new_pro_view;
    OPEN p_cur2 FOR
            SELECT * FROM best_pro_view;
    OPEN p_cur3 FOR
            SELECT * FROM banner WHERE order_seq<=5 ORDER BY order_seq ;
END;




create or replace PROCEDURE getMember (
    p_id IN member.id%TYPE,
    p_curvar OUT SYS_REFCURSOR  )
IS
BEGIN
    OPEN p_curvar FOR SELECT*FROM member WHERE id=p_id;
END;



create or replace PROCEDURE joinKakao(
    p_id in member.id%type,
    p_name in member.name%type,
    p_email in member.email%type,
    p_provider in member.provider%type
)
IS
BEGIN
    INSERT INTO member (id, name, email, provider)
    VALUES(p_id,p_name,p_email,p_provider);
    COMMIT;
END;

create or replace PROCEDURE insertMember(
    p_id in member.id%type,
    p_name in member.name%type,
    p_pwd in member.pwd%type,
    p_email in member.email%type,
    p_phone in member.phone%type,
    p_zip_num in member.zip_num%type,
    p_address1 in member.address1%type,
    p_address2 in member.address2%type,
    p_address3 in member.address3%type
)
IS
BEGIN
    INSERT INTO member (id, name, pwd, email,phone, zip_num, address1, address2,address3)
    VALUES(p_id,p_name,p_pwd,p_email,p_phone,p_zip_num, p_address1,p_address2,p_address3);
    COMMIT;
END;




















