# 캐릭터 상태 전환 검증

## 검증 환경

- 기준 커밋: [`2517f9b`](https://github.com/CHOIJIHWANcheese/Moni/commit/2517f9bcf3756ec489c6be28cbad03e8af94fe84)
- 대상: `src/main/java/com/moni/app/` 내 interaction/MoniStateMachine.java
- 환경: Linux, OpenJDK 21.0.12.1
- 방법: 기준 커밋의 Java 소스 9개를 별도 작업 공간에 복사하고 Git blob 해시가 원본과 동일함을 확인한 뒤, 독립 검증 하네스로 컴파일 및 실행. AI 도구를 활용한 검증이며 사용자 PC에서 직접 실행한 결과는 아님.
- 명령: `javac -d out */*.java` 및 `java -cp out com.moni.app.interaction.QaHarness`
- 기존 프로그램 코드 및 테스트 코드 변경 없음.

## 테스트 결과

| ID | 입력 및 조건 | 기대 결과 | 실제 결과 |
|---|---|---|---|
| M01 | 초기 상태에서 왼쪽 이동 시작 | IDLE → WALKING_LEFT | PASS |
| M02 | 이동 중 재이동 요청 후 이동 종료 | 재이동 거절, IDLE 복귀 | PASS |
| M03 | 잡기 중 대화 및 수면 요청 | 두 요청 거절, GRABBING 유지 | PASS |
| M04 | 잡기 해제 후 수면 요청 | IDLE → SLEEPING | PASS |
| M05 | 수면 중 대화 시작 후 종료 | TALKING → IDLE | PASS |

실행 출력: M01 PASS, M02 PASS, M03 PASS, M04 PASS, M05 PASS.

## 결론 및 제한 사항

5개 검증 통과, 실패 0개. 상태 전환 및 드래그 우선순위를 검증했다. 실제 마우스 이벤트와 애니메이션은 미검증.

전체 Gradle/JUnit 테스트 및 Windows/JDK 25/JavaFX 실행은 수행하지 않았다.
