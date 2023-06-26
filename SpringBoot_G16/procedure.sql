CREATE OR REPLACE PROCEDURE getMember(
    p_userid IN member.userid%TYPE,
    p_cursor OUT SYS_REFCURSOR  )
IS
BEGIN
    OPEN p_cursor FOR
        SELECT * FROM member WHERE userid=p_userid;
END;



CREATE OR REPLACE PROCEDURE getAllCount(
    p_cnt  OUT NUMBER
)
IS
    v_cnt NUMBER;
BEGIN
    SELECT count(*) INTO v_cnt FROM board;
    p_cnt := v_cnt;
END;





CREATE OR REPLACE PROCEDURE selectBoard(
    p_startNum IN NUMBER,
    p_endNum IN NUMBER,
    p_cursor OUT SYS_REFCURSOR   )
IS
    temp_cur SYS_REFCURSOR;   -- �Խù� ��ȣ�� ��ȸ�� ����� ���� Ŀ������
    v_num NUMBER;    -- �׵��� �Խù� ��ȣ���� �����ư��� ������ ����
    v_rownum NUMBER;   -- �׵��� ���ȣ���� �����ư��� ������ ����
    v_cnt NUMBER;    -- �� �Խù� ��ȣ�� ��ȸ�� ��۰����� ������ ����
BEGIN
    -- board ���̺��� startNum �� endNum ������ �Խù��� ��ȸ�ϵ�,  �Խù� ��ȣ(num) ���� ���մϴ�(ROWNUM �� ����)
    -- num ������ reply ���̺��� boardnum �� num �� ���ڵ尡 ����� ������ ���մϴ�
    -- num ���� ��� ������ �̿��ؼ� board ���̺��� replycnt �ʵ带  update  �մϴ�
    OPEN temp_cur FOR
            SELECT * FROM (
                SELECT * FROM (
                    SELECT rownum as rn, b.num FROM( ( SELECT * FROM board ORDER BY num DESC ) b )
                )WHERE rn>= p_startNum
            )WHERE rn<=p_endNum;
    LOOP
        FETCH temp_cur INTO v_rownum, v_num;
        EXIT WHEN temp_cur%NOTFOUND;
        SELECT count(*) INTO v_cnt FROM reply WHERE boardnum=v_num;
        UPDATE board SET replycnt = v_cnt WHERE num=v_num;
    END LOOP;
    COMMIT;
    
    -- ��۰����� ä���� ��� �Խù��� ��ȸ�ؼ� p_cursor�� ����ϴ�.
    OPEN p_cursor FOR
            SELECT * FROM (
                SELECT * FROM (
                    SELECT rownum as rn, b.* FROM( ( SELECT * FROM board ORDER BY num DESC ) b )
                )WHERE rn>= p_startNum
            )WHERE rn<=p_endNum;
    EXCEPTION
        WHEN OTHERS THEN
            ROLLBACK;
END;

SELECT * FROM BOARD;
ALTER TABLE BOARD ADD REPLYCNT NUMBER(5);


CREATE OR REPLACE PROCEDURE joinKakao(
    p_userid member.userid%type,
    p_email member.email%type,
    p_name member.name%type,
    p_provider member.provider%type

)
IS
BEGIN
    INSERT INTO member (userid, name, email, provider)
    VALUES(p_userid,p_email,p_name,p_provider);
    COMMIT;
END;


CREATE OR REPLACE PROCEDURE insertMember(
    p_userid member.userid%type,
    p_pwd member.pwd%type,
    p_email member.email%type,
    p_name member.name%type,
    p_phone member.phone%type

)
IS
BEGIN
    INSERT INTO member (userid,pwd, name, email, phone)
    VALUES(p_userid,p_pwd,p_name,p_email,p_phone);
    COMMIT;
END;

create or replace procedure updateMember(
    p_userid member.userid%type,
    p_pwd member.pwd%type,
    p_name member.name%type,
    p_email member.email%type,
    p_phone member.phone%type
)
is
begin
    update member set pwd = p_pwd, name = p_name, email = p_email, phone = p_phone
    where userid=p_userid;
    commit;
end;




CREATE OR REPLACE PROCEDURE getBoard(
    p_num  IN board.num%type,
    p_cur1 OUT SYS_REFCURSOR,
    p_cur2 OUT SYS_REFCURSOR
    )
IS
BEGIN
    OPEN p_cur1 FOR
            SELECT * FROM board WHERE num=p_num;
    OPEN p_cur2 FOR
            SELECT * FROM reply WHERE boardnum=p_num ORDER BY replynum DESC;      
    END;
    
CREATE OR REPLACE PROCEDURE plusOneReadCount(
    p_num  IN board.num%type
    )
IS
BEGIN
    UPDATE board SET readcount = readcount +1 WHERE num=p_num;
    COMMIT;     
END;

CREATE OR REPLACE PROCEDURE insertReply(
    p_boardnum  IN reply.boardnum%type,
    p_userid  IN reply.userid%type,
    p_content  IN reply.content%type
    )
IS
BEGIN
   INSERT INTO reply (replynum, boardnum,userid, content)
    VALUES(reply_seq.nextVal,p_boardnum, p_userid,p_content);
    COMMIT;   
END;


CREATE OR REPLACE PROCEDURE deleteReply(
    p_replynum  IN reply.replynum%type
    )
IS
BEGIN
   delete from reply where replynum=p_replynum;
    COMMIT;   
END;

CREATE OR REPLACE PROCEDURE insertBoard(
    p_userid  IN board.userid%type,
    p_pass  IN board.pass%type,
    p_email  IN board.email%type,
    p_content  IN board.content%type,
    p_title  IN board.title%type,
    p_imgfilename IN board.imgfilename%type
    )
IS
BEGIN
   INSERT INTO board (num,userid,pass,email, content,title,imgfilename)
    VALUES(board_seq.nextVal, p_userid,p_pass,p_email, p_content,p_title,p_imgfilename);
    COMMIT;   
END;


CREATE OR REPLACE PROCEDURE updateBoard(
    p_num  IN board.num%type,
    p_userid  IN board.userid%type,
    p_pass  IN board.pass%type,
    p_email  IN board.email%type,
    p_content  IN board.content%type,
    p_title  IN board.title%type,
    p_imgfilename IN board.imgfilename%type
    
    )
IS
BEGIN
    UPDATE board SET userid=p_userid,pass=p_pass,email=p_email, content=p_content,title=p_title,imgfilename=p_imgfilename
    WHERE num=p_num;
    COMMIT;     
END;

CREATE OR REPLACE PROCEDURE deleteBoard(
    p_num  IN board.num%type
    )
IS
BEGIN
   delete from board where num=p_num;
    COMMIT;   
END;









