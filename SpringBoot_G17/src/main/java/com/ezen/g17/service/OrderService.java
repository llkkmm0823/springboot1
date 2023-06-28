package com.ezen.g17.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.g17.dao.ICartDao;
import com.ezen.g17.dao.IOrderDao;

@Service
public class OrderService {

	@Autowired
	IOrderDao odao;

	public void insertOrder(HashMap<String, Object> paramMap) {
		odao.insertOrder(paramMap);
	}

	public void listOrderByOseq(HashMap<String, Object> paramMap) {
		odao.listOrderByOseq(paramMap);

		ArrayList<HashMap<String, Object>> list = (ArrayList<HashMap<String, Object>>) paramMap.get("ref_cursor");
		int totalPrice = 0;
		for (HashMap<String, Object> ovo : list) {
			totalPrice += Integer.parseInt(ovo.get("QUANTITY").toString())
					* Integer.parseInt(ovo.get("PRICE2").toString());
		}
		paramMap.put("totalPrice", totalPrice);
	}

	public void insertOrderOne(HashMap<String, Object> paramMap) {
		odao.insertOrderOne(paramMap);
	}

	public void myPageList(HashMap<String, Object> paramMap) {

		// 최종 mypage.jsp에 전달될 리스트
		// 이전 프로젝트와 다른점
		// Controller에서 하던 일 ->Service로 이동
		// ArrayList에 들어가는 OrderVO -> HashMap<HashMap<String, Object>>
		ArrayList<HashMap<String, Object>> finalList = new ArrayList<HashMap<String, Object>>();

		paramMap.put("ref_cursor", null);

		// 전달된 아이디로 진행중인 주문번호들을 조회(order_view에서 result가 '1'& 아이디로 검색)
		odao.listOrderByIdIng(paramMap);
		ArrayList<HashMap<String, Object>> oseqList = (ArrayList<HashMap<String, Object>>) paramMap.get("ref_cursor");
		// oseqList에 담긴 HashMap들의 하나하나는 모두 키: "oseq" 밸류 : 주문번호로 구성이 되어있을것

		// 주문번호별 주문내역을 조회
		for (HashMap<String, Object> result : oseqList) {
			// 해시맵에서 주문번호를 추출
			int oseq = Integer.parseInt(result.get("OSEQ").toString());

			// 주문번호 주문들을 조회
			paramMap.put("oseq", oseq);
			paramMap.put("ref_cursor", null);

			odao.listOrderByOseq(paramMap);
			ArrayList<HashMap<String, Object>> orderListByOseq = (ArrayList<HashMap<String, Object>>) paramMap
					.get("ref_cursor");

			// 조회한 주문목록에서 첫 번째 주문을 별도 저장
			HashMap<String, Object> orderFirst = orderListByOseq.get(0);
			// 상품의 이름변경
			orderFirst.put("PNAME", (String) orderFirst.get("PNAME") + " 포함" + orderListByOseq.size() + "건");

			// 상품의 가격도 총 금액으로 변경
			int totalPrice = 0;
			for (HashMap<String, Object> ovo : orderListByOseq) {
				totalPrice += Integer.parseInt(ovo.get("QUANTITY").toString())
						* Integer.parseInt(ovo.get("PRICE2").toString());
			}
			orderFirst.put("PRICE2", totalPrice);

			finalList.add(orderFirst);// 최종리스트에 추가
		}
		paramMap.put("finalList", finalList);// 최종리스트를 paramMap에 추가

	}

	public void orderAllList(HashMap<String, Object> paramMap) {
		ArrayList<HashMap<String, Object>> finalList = new ArrayList<HashMap<String, Object>>();
		paramMap.put("ref_cursor", null);
		odao.listOrderByIdAll(paramMap);
		ArrayList<HashMap<String, Object>> oseqList = (ArrayList<HashMap<String, Object>>) paramMap.get("ref_cursor");
		for (HashMap<String, Object> result : oseqList) {
			int oseq = Integer.parseInt(result.get("OSEQ").toString());
			paramMap.put("oseq", oseq);
			paramMap.put("ref_cursor", null);
			odao.listOrderByOseq(paramMap);
			ArrayList<HashMap<String, Object>> orderListByOseq = (ArrayList<HashMap<String, Object>>) paramMap
					.get("ref_cursor");
			HashMap<String, Object> orderFirst = orderListByOseq.get(0);
			orderFirst.put("PNAME", (String) orderFirst.get("PNAME") + " 포함" + orderListByOseq.size() + "건");
			int totalPrice = 0;
			for (HashMap<String, Object> ovo : orderListByOseq) {
				totalPrice += Integer.parseInt(ovo.get("QUANTITY").toString())
						* Integer.parseInt(ovo.get("PRICE2").toString());
			}
			orderFirst.put("PRICE2", totalPrice);

			finalList.add(orderFirst);
		}
		paramMap.put("finalList", finalList);
	}
}
