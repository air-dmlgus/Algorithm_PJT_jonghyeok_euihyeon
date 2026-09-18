# 냠냠코치

Java 17+ 콘솔에서 실행되는 식단 분석 기반 개인 건강 코칭 프로젝트입니다. 명세서의 UI(Menu) → Manager → Repository → Domain/DTO → Util 계층을 반영했고, 외부 API 키가 없어도 모든 메뉴를 사용할 수 있습니다.

## 바로 실행

필수 환경은 JDK 17 이상과 Maven 3.9 이상입니다.

```bash
mvn clean test package
java -jar target/yumyum-coach.jar
```

Windows에서는 `run.bat`, macOS/Linux에서는 `./run.sh`로도 실행할 수 있습니다. 최초 실행 시 JAR 내부 샘플을 현재 폴더의 `data/foods.csv`, `data/users.json`, `data/app-data.json`으로 복사합니다. 저장 위치를 바꾸려면 다음과 같이 지정합니다.

```bash
java -Dyumyum.data.dir=./my-data -jar target/yumyum-coach.jar
```

샘플 계정은 `demo / demo1234`, `coach / coach1234`입니다.

## 기능

- 필수: F201 식단 작성, F202 목록·ID 상세 조회, F203 수정, F204 삭제, F205 영양 합계·점수 분석
- 필수: F206 회원 가입, F207 본인 조회, F208 수정, F209 비활성화, F210 로그인·로그아웃과 접근 제어
- 추가: F211 팔로우/취소/목록, F212 챌린지 CRD, F213 참여·달성도, F214 게시판 CRUD·검색, F215 댓글 CRUD
- 심화: F216 식단 코칭, F217 운동 코칭. 기본값은 규칙 기반 오프라인 코치입니다.

## 자료구조와 알고리즘

- `LinkedHashMap<String, User/Food>`: ID 조회 평균 O(1), 입력 순서 보존
- `ArrayList`: 식단·챌린지·게시글·댓글의 순차 조회와 JSON 직렬화
- `LinkedHashSet<String>`: 팔로우 중복 방지와 순서 보존
- KMP: 음식명과 게시글 검색 O(N+M), 실제 비교 횟수·나노초 출력
- 병합 정렬: 식단 열량 정렬 O(n log n), 비교 횟수·나노초 출력
- `PriorityQueue`: 개인 조건을 점수화한 Top-K 음식 추천 O(n log k)
- 영양 점수: 목표 열량, 단백질, 나트륨, 지방 비율을 기준으로 0~100점 산정

로그인 후 `7.성능 데모`를 선택하면 동일 실행 환경에서 알고리즘명, 실행시간, 비교/평가 횟수를 확인할 수 있습니다. JVM 워밍업, OS 스케줄링, 데이터 크기에 따라 시간은 달라지므로 비교 횟수도 함께 해석해야 합니다.

## 생성형 AI 연동 구조

`GenerativeAiProvider`가 교체 지점입니다. 기본 `RuleBasedAiProvider`는 네트워크나 키 없이 실행됩니다. 실제 연동 시 `HttpAiProvider`를 상속해 `complete()`와 `name()`을 구현한 뒤 `App`에서 주입하면 됩니다. API 키는 코드나 JSON에 저장하지 말고 환경 변수에서 읽으세요. 외부 모델의 출력도 의료 진단이 아닌 일반 건강 조언으로 제한해야 합니다.

## 프로젝트 구조

```text
src/main/java/com/ssafy/yumyumcoach/
├─ ui/          콘솔 메뉴, 입력 검증
├─ manager/     업무 규칙, 분석, 추천, 성능 측정
├─ repository/  Map/List 메모리 저장과 JSON 영속화
├─ domain/      회원·음식·식단·챌린지·커뮤니티 모델
├─ util/        CSV/JSON, KMP, 병합 정렬, 측정 도구
└─ ai/          생성형 AI 인터페이스와 규칙 기반 fallback
```

## 데이터와 주의사항

샘플 음식 CSV는 교육·동작 확인용 축약 데이터이며 의료 판단에 사용할 수 없습니다. 비밀번호는 명세 시연을 위해 평문 샘플로 저장되므로 실제 서비스에서는 Argon2/bcrypt 해시, 접근 권한, 백업 정책이 필요합니다. JSON 저장은 임시 파일 후 교체 방식으로 손상 가능성을 줄였습니다.
