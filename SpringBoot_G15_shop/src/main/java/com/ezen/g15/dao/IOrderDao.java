package com.ezen.g15.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ezen.g15.dto.CartVO;
import com.ezen.g15.dto.OrderVO;

@Mapper
public interface IOrderDao {

	void insertOrders(String id);

	int LookupMaxOseq();

	void insertOrderDetail(CartVO cvo, int oseq);

	void deleteCart(Integer cseq);

	List<OrderVO> listOrderByOseq(int oseq);

	void insertOrderDetailOne(int pseq, int quantity, int oseq);

	List<Integer> selectSeqOrderIng(String id);

	List<Integer> selectSeqOrderAll(String id);

}
