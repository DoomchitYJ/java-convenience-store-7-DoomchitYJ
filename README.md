# java-convenience-store-precourse

# 예상 시나리오

1. 편의점이 가지고 있는 상품 내역을 출력한다.
2. 구매할 상품명과 수량을 입력 받는다.
3. 구매할 상품의 프로모션과 관련하여 추가 고지한다.
   1) 프로모션 상품을 해당 수량보다 적게 가져온 경우에 무료로 상품을 더 받을 것인지 여부를 입력 받는다.
   2) 프로모션 상품의 재고가 부족하여 일부를 혜택 없이 결제해야 하는 경우에 정가로 결제할지 여부를 입력 받는다.
4. 멤버십 할인 적용 여부를 입력 받는다.
5. 추가 구매 여부를 입력 받는다.

### 예외 처리

1. 구매할 상품과 수량 형식이 올바르지 않은 경우 (ex. [상품명-개수],[상품명-개수])
   - [ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.
2. 존재하지 않는 상품을 입력한 경우
   - [ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.
3. 구매 수량이 재고 수량을 초과한 경우
   - [ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.
4. 여부를 묻는 질문에서 Y와 N 외의 값을 입력한 경우
   - [ERROR] Y 혹은 N을 입력해주세요.

# 구현 기능 목록

## 편의점 재고를 생성하는 기능

- [x]  products.md를 읽어온다.
- [X]  필요에 따라 재고를 업데이트할 수 있도록 도메인을 구성한다.
- [x]  최초 재고를 셋업한다.

## 편의점 재고를 출력하는 기능

- [x]  “안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n” 를 출력한다.
- [x]  products.md와 promotions.md 파일을 이용하여 재고 목록을 생성한다.
- [x]  재고 목록을 출력한다.
   - [x]  "상품명 000원 00개 (프로모션)"
   - [x]  가격은 천 원 단위로 ,를 붙인다.
   - [x]  재고가 없을 경우 재고 없음을 출력한다.

## 구매할 상품과 수량을 입력 받는 기능

- [x]  "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])"을 출력한다.
- [x]  구매할 상품명과 수량을 입력 받는다.
- [x]  입력을 검증한다.
   - [x]  구분자로 나누어 파싱한다.
   - [x]  [상품명-개수] 형식에 맞는지 확인한다.
   - [x]  상품명과 수량을 각각 파싱한다.
   - [x]  재고 목록에 있는 상품인지 확인한다.
   - [x]  요청한 개수 이상 재고가 남아있는지 확인한다. 

## 구매 정책을 세우는 기능

- [x]  프로모션 정책을 세운다.
   - [x]  promotions.md를 읽어온다.
   - [x]  오늘 날짜를 확인하여 프로모션 적용 기간인지 확인한다.
   - [x]  해당 프로모션의 buy와 get을 확인한다.
      - [x]  "프로모션 적용 가능 상품을 고객이 해당 수량만큼 가져오지 않았을 경우"의 처리 기능을 동작한다.
      - [x]  "프로모션 재고가 부족하여 일부를 혜택 없이 결제해야 하는 경우"의 처리 기능을 동작한다.
   - [x]  증정품의 개수를 센다.
   - [x]  장바구니에 상품을 넣는다.
- [x]  프로모션이 진행 중이지 않을 경우,
   - [x]  장바구니에 상품을 넣는다.

## 장바구니에 구매할 상품을 넣는 기능

- [x]  장바구니에 구매할 상품 정보를 저장한다.
- [x]  구매할 상품의 재고를 수량만큼 줄인다.

## 상품을 구매하는 기능

- [x]  멤버십 적용 여부에 따라 적용한다.
- [x]  영수증을 출력한다.
- [ ]  장바구니를 비운다.

## 프로모션 적용 가능 상품을 고객이 해당 수량만큼 가져오지 않았을 경우

### 안내 메시지를 출력하는 기능

- [x]  “현재 {상품명}은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)” 를 출력한다.

### 위 경우의 응답을 입력 받는 기능

- [x]  응답을 입력 받는다.
- [x]  입력을 검증한다.
   - [x]  (Y/N) 형식에 맞는지 확인한다. 

### 응답이 Y일 때 동작하는 기능

- [x]  구매할 수량을 1개 늘린다.
- [x]  장바구니에 구매한 상품 정보를 추가한다.

## 프로모션 재고가 부족하여 일부를 혜택 없이 결제해야 하는 경우

### 안내 메시지를 출력하는 기능

- [x]  프로모션 할인이 적용되지 않는 수량을 구한다.
- [x]  "현재 {상품명} {수량}개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)"를 출력한다.

### 위 경우의 응답을 입력 받는 기능

- [x]  응답을 입력 받는다.
- [x]  입력을 검증한다.
   - [x]  (Y/N) 형식에 맞는지 확인한다.

### 응답이 Y일 때 동작하는 기능

- [x]  장바구니에 구매한 상품 정보를 추가한다.

### 응답이 N일 때 동작하는 기능

- [x]  기존 구매하려던 수량에서 프로모션 미적용 수량을 뺀다.
- [x]  장바구니에 구매한 상품 정보를 추가한다.

## 멤버십 할인 적용 여부 안내 메시지를 출력하는 기능

- [x]  "멤버십 할인을 받으시겠습니까? (Y/N)"를 출력한다.

### 위 경우의 응답을 입력 받는 기능

- [x]  응답을 입력 받는다.
- [x]  입력을 검증한다.
   - [x]  (Y/N) 형식에 맞는지 확인한다.

### 응답이 Y일 때 동작하는 기능

- [x]  장바구니에서 프로모션이 적용되지 않은 상품의 총액 * 0.3을 계산한다.
   - [x]  할인액이 8,000원을 넘을 경우, 할인액은 8,000원으로 한다.
- [x]  총액 - 할인액 = 최종 결제값으로 갖는다.

## 영수증을 출력하는 기능

- [x]  장바구니에 있는 상품 목록을 출력한다.
   - [x]  "===========W 편의점============="를 출력한다.
   - [x]  "상품명		수량 	금액"을 출력한다.
   - [x]  "{상품명}   {수량}   {금액}"을 출력한다. (구매한 상품 목록대로)
   - [x]  "===========증	정============="를 출력한다.
   - [x]  "{상품명}   {수량}"을 출력한다. (증정 받은 상품 목록대로)
   - [x]  "=============================="를 출력한다.
   - [x]  "총구매액   {수량}   {금액}"을 출력한다.
   - [x]  "행사할인           {금액}"을 출력한다.
   - [x]  "멤버십할인          {금액}"을 출력한다.
   - [x]  "내실돈             {금액}"을 출력한다.
   - [x]  상품명, 수량은 좌측 정렬, 금액은 우측 정렬로 출력되게 한다.

## 추가 구매 여부 안내 메시지를 출력한다.

- [ ]  "감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)"를 출력한다.

### 위 경우의 응답을 입력 받는 기능

- [ ]  응답을 입력 받는다.
- [ ]  입력을 검증한다.
- [ ]  (Y/N) 형식에 맞는지 확인한다.

### 응답이 Y일 때 동작하는 기능

- [ ]  처음(편의점 재고 출력)으로 돌아가 반복한다.

### 응답이 N일 때 동작하는 기능

- [ ]  프로그램을 종료한다.

# 고려할 점

1. 재입력은 5회로 제한
