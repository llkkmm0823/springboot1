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



CREATE OR REPLACE PROCEDURE insertOrder(
     p_id IN orders.id%TYPE,
     p_oseq OUT orders.oseq%TYPE
 )
IS
    v_oseq orders.oseq%TYPE;
    cart_cur SYS_REFCURSOR;
    v_pseq cart.pseq%TYPE;
    v_quantity cart.quantity%TYPE;
    v_cseq cart.cseq%TYPE;
BEGIN
   -- orders ���̺� ���ڵ带 �߰���
    INSERT INTO orders(oseq, id)
    VALUES (orders_seq.nextVal, p_id);
    
    -- orders ���̺� ��� �߰��� ���ڵ��� oseq�� ��ȸ�ؼ� ������ ����.
    SELECT MAX(oseq) INTO v_oseq FROM orders;
    
    -- cart ���̺��� �ֹ��� ��ǰ�� ��ǰ��ȣ, ������ ��ȸ�ؼ� Ŀ�������� ����
    OPEN cart_cur FOR SELECT cseq, pseq, quantity FROM cart WHERE id = p_id; 
    
    LOOP
        FETCH cart_cur INTO v_cseq, v_pseq, v_quantity;    -- Ŀ������ ���ڵ� (pseq, quantity �ϳ� ����)
        EXIT WHEN cart_cur%NOTFOUND;
        INSERT INTO order_detail(odseq, oseq, pseq, quantity)
        VALUES(order_detail_seq.nextVal, v_oseq, v_pseq, v_quantity);   -- order_detail�� ���ڵ� �߰� 
        DELETE FROM cart WHERE cseq = v_cseq;
    END LOOP;
    
    COMMIT;
    p_oseq := v_oseq;
    
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        
END;

create or replace PROCEDURE listOrderByOseq(
     
     p_oseq IN orders.oseq%TYPE,
     p_cur  OUT SYS_REFCURSOR 
      
 )
IS
BEGIN
    OPEN p_cur FOR SELECT * FROM order_view where oseq=p_oseq;
    
end;
















