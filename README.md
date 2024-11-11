# java-convenience-store-precourse

## 기능 구현 목록 
-[x] 입력 클래스 (InputView)
  - [x] 상품의 가격과 수량
  - [x] 파일에서 읽어온다
    - 프로모션 적용이 가능한 상품에 대해 고객이 증정상품

- [x] 상품 클래스
  - [x] 재고 수량
  - [x] 고객이 상품을 구매한 수만큼 재고에서 차감

- [x] 프로모션 할인
  - N개 구매시 1개 무료증정 형태
  - 동일 상품에 여러 프로모션이 적용되지 않는다
  - [x] 오늘 날짜가 프로모션 기간 내에 포함되는 지 확인
  - 프로모션 재고 내에서만 적용가능 
    - [x] 구매한 수만큼 재고에서 차람
    - [x] 재고가 부족한 경우에는 일반 재고를 사용
  - [x] 알림
    - 고객이 프로모션 적용 가능한 상품을 해당 수량보다 적게 가져온 경우,
        필요한 수량을 추가로 가져오면 해택을 받을 수 있음을 안내한다.
    - 프로모션 제고가 부족하여 일부 수량을 프로모션 없이 결제하는 경우, 
    일부 수량에 대해 정가로 결제하게 됨을 안내한다.
  
- [x] 맴버쉽 할인
  - 프로모션 적용 후 남은 금액의 30%를 할인
  - 최대 한도는 8000원 
  
- [x] 영수증 출력 (OutputView)
  - [x] 구매 상품 내역: 상품명, 가격, 수량
  - [x] 증정 삼품 내역: 프로모션에 따라 무료로 제공된 상품 목록 
  - [x] 금액 정보
    - [x] 총구매액
    - [x] 행사할인: 프로모션에 의해 할인된 금액
    - [x] 맴버쉽 할인 
    - [x] 최종 결제 금액

## 프로그래밍 요구 사항
- [ ] else 예약어를 사용하지 않는다
- [x] Enum 적용
- [ ] 함수 길이는 10라인 이하로, 한가지 일만 하도록 구현
- [x] 입출력을 담당하는 클래스를 별도로 구현한다 
- [ ] indent depth 2까지 허용