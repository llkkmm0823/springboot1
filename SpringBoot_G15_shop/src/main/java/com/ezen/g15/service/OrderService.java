package com.ezen.g15.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezen.g15.dao.ICartDao;
import com.ezen.g15.dao.IOrderDao;
import com.ezen.g15.dto.CartVO;
import com.ezen.g15.dto.OrderVO;

@Service
public class OrderService {

	@Autowired
	IOrderDao odao;

	@Autowired
	ICartDao cdao;
	// 따로 메서드 생성하지 않고 있는 것 쓸 것

	@Transactional
	public HashMap<String, Object> insertOrder(String id) {
		HashMap<String, Object> result = new HashMap<String, Object>();

		int oseq = 0;
		// 여러데이터베이스 작업
		// 1. Orders 테이블에 현재 아이디로 레코드를 추가
		odao.insertOrders(id);
		// 2. 방금 추가된 Orders 테이블의 레코드의 주문번호(oseq)를 조회 (가장 큰 주문번호)
		oseq = odao.LookupMaxOseq();
		// 3. 주문번호와 함께 카트리스트에 있는 상품들을 하나하나 order_detail테이블에 추가
		// - CartList를 id로 한번 더 조회
		// 4. Cart테이블에서 방금 주문한 상품을 삭제
		List<CartVO> list = cdao.getCartList(id);
		for (CartVO cvo : list) {
			odao.insertOrderDetail(cvo, oseq);
			odao.deleteCart(cvo.getCseq());
		}
		// 5. 주문리스트와 주문번호를 해시맵에 담음
		result.put("oseq", oseq);

		return result;
	}

	public HashMap<String, Object> listOrderByOseq(int oseq) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		List<OrderVO> list = odao.listOrderByOseq(oseq);
		result.put("orderList", list);
		int totalPrice = 0;
		for (OrderVO ovo : list) {
			totalPrice += ovo.getPrice2() * ovo.getQuantity();
		}
		result.put("totalPrice", totalPrice);

		return result;
	}

	public int insertOrderOne(int pseq, int quantity, String id) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		int oseq = 0;
		odao.insertOrders(id);
		oseq = odao.LookupMaxOseq();
		odao.insertOrderDetailOne(pseq, quantity, oseq);
		return oseq;
	}

	public ArrayList<OrderVO> getFinalListIng(String id) {
		// 최종 리턴될 오더리스트
		ArrayList<OrderVO> orderList = new ArrayList<OrderVO>();

		// 1. id로 진행중인 주문의 주문번호 조회
		List<Integer> oseqList = odao.selectSeqOrderIng(id);

		// 2. 주문 번호들로 반복실행을 실행
		for (int oseq : oseqList) {

		// 3. 하나의 주문번호로 주문내역을 조회
		List<OrderVO> orderListIng = odao.listOrderByOseq(oseq);

		// 4. orderListIng에서 첫 번째 주문을 따로 저장
		OrderVO ovo = orderListIng.get(0);

		// 5. orderListIng의 주문들의 요약정보를 ovo에 저장
		ovo.setPname(ovo.getPname() + " 포함" + orderListIng.size() + " 건");
		int totalPrice = 0;
		for (OrderVO ovo1 : orderListIng)
			totalPrice += ovo1.getPrice2() * ovo1.getQuantity();
		ovo.setPrice2(totalPrice);

		// 6. return될 리스트에 ovo를 add
		orderList.add(ovo);
	}
	return orderList;
}

	public ArrayList<OrderVO> getFinalListAll(String id) {
		ArrayList<OrderVO> orderList = new ArrayList<OrderVO>();

		List<Integer> oseqList = odao.selectSeqOrderAll(id);

		for (int oseq : oseqList) {
			List<OrderVO> orderListIng = odao.listOrderByOseq(oseq);
			OrderVO ovo = orderListIng.get(0);

			ovo.setPname(ovo.getPname() + " 포함" + orderListIng.size() + " 건");
			int totalPrice = 0;
			for (OrderVO ovo1 : orderListIng)
				totalPrice += ovo1.getPrice2() * ovo1.getQuantity();
			ovo.setPrice2(totalPrice);

			orderList.add(ovo);
		}
		return orderList;
	}
}
