# Git branch 전략

- **develop branch에서** 새로운 branch를 만들어 작업을 한다.
  - 이때 branch 명은 feature/[기능이름]
- commit 메세지는 [링크](https://jae04099.tistory.com/entry/GIT-%EC%BB%A4%EB%B0%8B%EC%97%90-%EA%B4%80%ED%95%98%EC%97%AC%EC%A2%8B%EC%9D%80-%EC%BB%A4%EB%B0%8B-%EB%A9%94%EC%8B%9C%EC%A7%80-%EC%BB%A4%EB%B0%8B-%ED%83%80%EC%9D%B4%EB%B0%8D-%EB%93%B1) 활용
  - 여기서 `git commit -m "blabla"` -m 옵션 사용하지 말고 직접 추가 하자
  - type들 중에 여러가지를 동시에 했다면 그냥 주제 부분에 어떤어떤거 했다 쓰고 바디 부분에 type: blabla
  ![image](https://user-images.githubusercontent.com/74540758/161551726-c774548e-f998-4088-ae98-0420dccdc816.png)
  이런식으로 작성하자

- merge하기 전에 pull request 올리자
```
git add .
git commit (ex: Feat: 추가 로그인 API 로직 구현)
git push origin 브랜치 이름 (ex: feature/#1)

```
