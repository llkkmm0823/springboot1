--------------------------------------------------------
--  DDL for Procedure GETALLCOUNT
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "SCOTT"."GETALLCOUNT" (
    p_cnt OUT NUMBER 
)
IS
    v_cnt NUMBER;
BEGIN
    SELECT count(*) INTO v_cnt FROM board;
    p_cnt := v_cnt;
END;

/
--------------------------------------------------------
--  DDL for Procedure GETMEMBER
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "SCOTT"."GETMEMBER" (
    p_userid IN member.userid%TYPE,
    p_cursor OUT SYS_REFCURSOR  )
IS
BEGIN
    OPEN p_cursor FOR
        SELECT*FROM member WHERE userid=p_userid;
END;

/
--------------------------------------------------------
--  DDL for Procedure SELECTBOARD
--------------------------------------------------------
set define off;

  CREATE OR REPLACE PROCEDURE "SCOTT"."SELECTBOARD" (
    p_startNum IN NUMBER,
    p_endNum IN NUMBER,
    p_cursor OUT SYS_REFCURSOR
)
IS
    temp_cur SYS_REFCURSOR; -- �Խù� ��ȣ�� ��ȸ�� ����� ���� Ŀ������
    v_num NUMBER;   -- �׵��� �Խù� ��ȣ���� �����ư��� ���� �� ����
    v_rownum NUMBER; -- �׵��� ���ȣ���� �����ư��� ���� �� ����
    v_cnt NUMBER; -- �� �Խù� ��ȣ�� ��ȸ�� ��۰����� ���� �� ���� 
BEGIN
    -- board ���̺��� startNum �� endNum ������ �Խù��� ��ȸ�ϵ�,
    -- �Խù� ��ȣ(num) ���� ���մϴ� (ROWNUM�� ����)
    -- num ������ reply ���̺��� boardnum �� num �� ���ڵ尡 ����� ������ ���մϴ�.
    -- num ���� ��� ������ �̿��ؼ� board ���̺��� replycnt �ʵ带 update �մϴ�.
    OPEN temp_cur FOR
        SELECT * FROM (
            SELECT * FROM (
                SELECT rownum as rn, b.num FROM( ( SELECT * FROM board ORDER BY num DESC ) b )
            )WHERE rn >= p_startNum
        )WHERE rn <= p_endNum;
    LOOP
        FETCH temp_cur INTO v_rownum, v_num ;
        EXIT WHEN temp_cur%NOTFOUND;
        SELECT count(*) INTO v_cnt FROM reply WHERE boardnum = v_num;
        UPDATE board SET replycnt = v_cnt WHERE num = v_num;
    END LOOP;
    COMMIT;

    -- ��۰����� ä���� ��� �Խù��� ��ȸ�ؼ� p_cursor�� ����ϴ�.
    OPEN p_cursor FOR
         SELECT * FROM (
            SELECT * FROM (
                SELECT rownum as rn, b.* FROM( ( SELECT * FROM board ORDER BY num DESC ) b )
            )WHERE rn >= p_startNum
        )WHERE rn <= p_endNum;

END;

/
