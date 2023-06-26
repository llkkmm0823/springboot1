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
    temp_cur SYS_REFCURSOR;   -- 게시물 번호만 조회한 결과를 담을 커서변수
    v_num NUMBER;    -- 그들의 게시물 번호들을 번갈아가며 저장할 변수
    v_rownum NUMBER;   -- 그들의 행번호들을 번갈아가며 저장할 변수
    v_cnt NUMBER;    -- 각 게시물 번호로 조회한 댓글갯수를 저장할 변수
BEGIN
    -- board 테이블에서 startNum 과 endNum 사이의 게시물을 조회하되,  게시물 번호(num) 값만 취합니다(ROWNUM 도 같이)
    -- num 값으로 reply 테이블에서 boardnum 이 num 인 레코드가 몇개인지 갯수를 구합니다
    -- num 값과 댓글 갯수를 이용해서 board 테이블의 replycnt 필드를  update  합니다
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
    
    -- 댓글갯수가 채워진 대상 게시물을 조회해서 p_cursor에 담습니다.
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
















