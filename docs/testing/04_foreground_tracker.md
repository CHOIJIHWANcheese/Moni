# 활성 앱 변경 추적 검증

## 검증 환경

- 기준 커밋: [`2517f9b`](https://github.com/CHOIJIHWANcheese/Moni/commit/2517f9bcf3756ec489c6be28cbad03e8af94fe84)
- 대상: `src/main/java/com/moni/app/` 내 foreground/ForegroundApplicationTracker.java, ForegroundApplication.java
- 환경: Linux, OpenJDK 21.0.12.1
- 방법: 기준 커밋의 Java 소스 9개를 별도 작업 공간에 복사하고 Git blob 해시가 원본과 동일함을 확인한 뒤, 독립 검증 하네스로 컴파일 및 실행. AI 도구를 활용한 검증이며 사용자 PC에서 직접 실행한 결과는 아님.
- 명령: `javac -d out */*.java` 및 `java -cp out com.moni.app.interaction.QaHarness`
- 기존 프로그램 코드 및 테스트 코드 변경 없음.

## 테스트 결과

| ID | 입력 및 조건 | 기대 결과 | 실제 결과 |
|---|---|---|---|
| F01 | a.exe 첫 입력 | a.exe 반환 | PASS |
| F02 | 동일 앱 반복 및 동일 경로의 다른 PID | 둘 다 알림 없음 | PASS |
| F03 | b.exe로 변경 후 반복 | 최초만 b.exe 반환 | PASS |
| F04 | 빈 결과 후 b.exe 재입력 | 빈 결과 이후 다시 감지 | PASS |

실행 출력: F01 PASS, F02 PASS, F03 PASS, F04 PASS.

## 결론 및 제한 사항

4개 검증 통과, 실패 0개. 가상 프로세스 데이터를 이용해 중복 감지 방지와 초기화를 검증했다. Windows 네이티브 API 및 실제 활성 창 감지는 미검증.

전체 Gradle/JUnit 테스트 및 Windows/JDK 25/JavaFX 실행은 수행하지 않았다.
