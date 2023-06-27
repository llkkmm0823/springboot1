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