# 자율 이동 계획 검증

## 검증 환경

- 기준 커밋: [`2517f9b`](https://github.com/CHOIJIHWANcheese/Moni/commit/2517f9bcf3756ec489c6be28cbad03e8af94fe84)
- 대상: `src/main/java/com/moni/app/` 내 walking/WalkPlanner.java 외 관련 클래스 3개
- 환경: Linux, OpenJDK 21.0.12.1
- 방법: 기준 커밋의 Java 소스 9개를 별도 작업 공간에 복사하고 Git blob 해시가 원본과 동일함을 확인한 뒤, 독립 검증 하네스로 컴파일 및 실행. AI 도구를 활용한 검증이며 사용자 PC에서 직접 실행한 결과는 아님.
- 명령: `javac -d out */*.java` 및 `java -cp out com.moni.app.interaction.QaHarness`
- 기존 프로그램 코드 및 테스트 코드 변경 없음.

## 테스트 결과

| ID | 입력 및 조건 | 기대 결과 | 실제 결과 |
|---|---|---|---|
| W01 | 왼쪽 끝 x=0, 창 너비 128, 화면 0~1000, 최소 난수 | RIGHT, 목표 x=80 | PASS |
| W02 | 오른쪽 끝 x=872, 최대 난수 | LEFT, 목표 x=632 | PASS |
| W03 | x=40, 최소 난수 | RIGHT | PASS |
| W04 | 화면 너비 200, 창 너비 128 | 이동 계획 없음 | PASS |
| W05 | 창 너비 0 | 이동 계획 없음 | PASS |

실행 출력: W01 PASS, W02 PASS, W03 PASS, W04 PASS, W05 PASS.

## 결론 및 제한 사항

5개 검증 통과, 실패 0개. 이동 방향과 경계 조건을 검증했다. 실제 화면 이동 및 다중 모니터 동작은 미검증.

전체 Gradle/JUnit 테스트 및 Windows/JDK 25/JavaFX 실행은 수행하지 않았다.
