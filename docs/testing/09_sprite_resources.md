# 이미지 리소스 검증

## 테스트 목적

캐릭터 상태별 PNG와 스프라이트 메타데이터가 존재하며, Gradle 빌드 산출물의 클래스패스에서 로드되고 프레임 규격과 맞는지 확인한다.

## 검증 환경 및 절차

- 기준 커밋: [`2517f9b`](https://github.com/CHOIJIHWANcheese/Moni/commit/2517f9bcf3756ec489c6be28cbad03e8af94fe84)
- 환경: Windows 11 x64, Microsoft OpenJDK 25.0.5.1, Gradle 9.1.0, JavaFX 25
- 절차: `gradlew clean test` 후 빌드 클래스패스 `build/resources/main`에서 Java `ImageIO`로 리소스를 열고 크기와 알파 채널을 확인했다. `moni_sprites.json`은 PowerShell JSON 파서로 읽어 프레임 크기, 아틀라스 크기, 잡기 애니메이션 반복 시작 프레임을 확인했다.
- 예상 결과: 대기/이동/드래그/수면/대화 이미지와 메타데이터를 찾을 수 있고, 개별 스프라이트는 128×160 프레임 4개로 구성된다.

## 테스트 결과

| ID | 리소스 | 실제 결과 |
|---|---|---|
| T09-01 | `moni_idle.png` | PASS, 클래스패스 로드, 512×160, 알파 있음, 4프레임 |
| T09-02 | `moni_walk_left.png`, `moni_walk_right.png` | PASS, 각 512×160, 알파 있음, 각 4프레임 |
| T09-03 | `moni_grab.png` | PASS, 512×160, 알파 있음, 4프레임 |
| T09-04 | `moni_sleep.png` | PASS, 512×160, 알파 있음, 4프레임 |
| T09-05 | `moni_talk.png` | PASS, 512×160, 알파 있음, 4프레임 |
| T09-06 | `moni_all_sprites.png` | PASS, 클래스패스 로드, 512×960, 알파 있음 |
| T09-07 | `ui/moni_speech_bubble.png` | PASS, 클래스패스 로드, 1774×887, 알파 있음 |
| T09-08 | `moni_sprites.json` | PASS, 프레임 128×160, 아틀라스 512×960, `grab.loopFromFrame=1` |

## 발견된 문제 및 결론

발견된 누락 또는 디코딩 오류 없음. 이미지 및 메타데이터가 빌드 리소스 경로에서 로드됐다. 화면 배율별 렌더링 품질은 확인하지 않았다.