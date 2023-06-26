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
    p_provider member.provider%type,

)
IS
BEGIN
    INSERT INTO member (userid, name, email, provider)
    VALUES(p_userid,p_email,p_name,p_provider);
    COMMIT;
END;
















