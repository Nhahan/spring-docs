# Git

<br>

## Git이란?

- 형상 관리 도구
  - 버전 관리 시스템이라고도 한다.
- 분산형 관리 시스템
  - 중앙 서버에서 소스코드와 히스토리를 저장하지 않고 여러 로컬 저장소들을 이용하여 버전을 관리한다. 따라서 중앙서버에 장애가 발생하거나 인터넷이 끊겼거나(혹은 인터넷이 되지 않는 환경에 있더라도) 하더라도 로컬에서 계속 버전관리를 해나아갈 수 있다.

<br>
  
![image](https://user-images.githubusercontent.com/81916648/217448434-f5da7676-46dd-4f10-afd0-5a499e61c644.png)

(깃을 쓰지 않던 시절의 누군가)

<br>

![image](https://user-images.githubusercontent.com/81916648/217448530-ebadcd22-9492-4d96-8585-70bb22846dbf.png)

(깃을 쓰고 난 후의 누군가. 대한민국 헌법 개정내역 https://github.com/ohahohah/constitution-of-republic-of-korea/commits/main)

<br>
<hr>
<br>
  
![image](https://user-images.githubusercontent.com/81916648/217444143-08a130d5-6974-44bb-9df1-0eb12003467f.png)

git에서 관리하는 영역은 크게 3가지가 있다. 

- 현재 작업중인 **Working Directory**
- commit 할 파일의 예비 저장소, 혹은 추적 대상 파일의 공간인 **Staging Area**
- 각 유저의 컴퓨터에서 관리되고 있는 **Remote Repository**

현재 눈으로 확인하며 작업중인 곳을 Working Directory라고 하고, Working Directory에서 작업한 내용을 Add 명령어로 Local Repository에 추가할 대상들을 모아놓는 곳이 Staging Area이다. 그리고 Commit으로 Staging Area를 결과적으로 Local Repository에 저장하게 되고, 마지막으로 Remote Repository에 Push를 하게 된다.

<br><br>

## GitHub이란?

위에서의 Remote Repository의 한 종류가 GitHub이다. (GitHub 외에도 GitLab, BitBucket 등이 있다)
깃헙은 깃으로 된 프로젝트 저장 공간을 제공하고, 편하게 사용하기 위한 여러가지 부가기능을 가지고 있다. 깃헙은 협업하기 위한 여러가지 기능들을 가지고 있다.
로컬 컴퓨터에 있는 깃 프로젝트를 업로드하거나, 타인이나 협업을 하기 위한 깃헙에 올라와있는 프로젝트를 다운로드(Clone)할 수도 있다.
부가 기능으로 Issue, Pull Request, Actions, Wiki, Security 등을 제공하고 있어 협업과 개발을 함에 있어 많은 도움을 받을 수 있다.

- 커밋 히스토리 보기 예: https://github.com/Nhahan/mvc/commits/main
- 변경사항 보기 예: https://github.com/Nhahan/mvc/commit/40d8f5648591e993e179db3511ed1a43a3ef9507

<br><br>

## 해보기

1. 로컬 저장소 초기화(시작)

```
git init
```

작업하고 있는 프로젝트를 git이 관리하게 하기 위해 초기화를 해주어야 한다.

<br>

2. 사용자 등록

```
git config --global user.name  "Hello"
git config --global user.email "hello@github.com"

git config user.name // name 확인
git config user.email // email 확인
```

<br>

3. `.gitignore` 파일을 통한 commit 대상 제외

git으로 관리하는 파일도 있지만 git으로 관리하고 싶지 않은 파일도 있을텐데, 이 때 `.gitignore`에 등록하면 commit에서 제외시킬 수 있다. 보통 저장소의 최상위 디렉토리에 위치하고 있다.
SSH pem key, .env 같은 환경변수나 키 등을 `.gitignore`에 등록시켜 Public Repository에 등록되지 않게 해야한다. 
만약 Private Repository의 경우는 그 안에서도 특정 인원만 접근하게 할 게 아니라면 등록시켜도 된다. Public Repository일 경우에는 외부 프로그램 혹은 서비스(AWS, Doppler 등)들을 이용하거나 GitHub에서 제공하는 Security를 이용하는 방법도 있다.

<br>

4. 저장소 상태 확인

```
git status
```

<br>

초기화를 하자마자 `git status`를 입력하면 커밋할 사항이 없다고 나올 것이다.

5. 추적하기

```
git add <file name> // 특정 파일만 추적
git add . // 전체 추적
```

<br>

6. 추적 해제하기

```
git rm --cached <file name>
```

<br>

7. Local Repository에 소스 반영하기

```
git commit -m "message"
// 커밋할 때는 메시지를 적어주어야하는데, 따로할 수도 있지만 대부분은 같이 하기 때문에 `-m`을 붙여준다

git commit -am "message"
// `git add .` 과정도 생략하고 싶을 때 `-a`도 함께 붙여준다.
// 주의할 점은 한 번도 add 되지 않았던 파일은 add, commit 되지 않는다.
```

<br>

8. 커밋 로그 확인하기(작업 히스토리)

```
git log
git log --oneline // 한줄 표현
```

<br>

9. 소스 변동 사항 확인

```
gif diff
```

터미널로는 변경 사항이 일정 수준 이상으로 많아지면 확인이 힘들기 때문에 GUI 툴로 확인하는 편이 좋다.

<br>

10. 브랜치 설정하기

```
git branch hello // hello branch를 만든다
git branch -D hello // hello branch를 삭제한다
git checkout hello // hello branch로 이동한다
git checkout -b hello // hello branch를 만듦과 동시에 이동한다
```

<br>

11. 브랜치 병합하기

```
// main branch에 위치하고 있고, hello branch가 존재한다고 가정
git merge hello // main branch에 hello의 작업사항을 합친다.
```

<br>

12. 원격 저장소 설정

```
git remote add origin https://github.com/ggingmin/test.git
// git remote add [원격 저장소명] [원격 저장소 주소]
```

<br>

13. 원격 저장소에 프로젝트 업로드

```
git push -u origin main
// -u는 --set-upstream의 shortcut
```

upstream으로 설정한 브랜치에는 간단하게 `git push`만으로 푸시가 가능하다.
그렇지 않은 브랜치에는 `git push origin <branch명>`처럼 푸시를 해주어야한다.

<br>

14. 원격 저장소 프로젝트를 로컬로 가져오기

```
git fetch // 원격 저장소의 모든 업데이트 사항을 가져온다
git fetch origin main // origin main의 업데이트 사항만 가져온다
git pull // fetch를 함과 동시에 merge까지 한다
```

보통은 git pull을 가장 많이 쓴다. 
새로운 작업을 하기 위해 세 branch를 생성할거라면 반드시 git pull을 먼저 실행한 뒤에 branch를 만드는 습관을 들여야 나중에 conflict이 나지 않는다.

<br>

15. Working Directory에 변경 사항이 있고, 커밋을 할 수 없는 상황에서 다른 브랜치로 가야할 때
```
git stash // 변경 내용을 임시 저장. 이제 다른 브랜치로 이동이 가능해진다
git stash pop // 임시 저장 내용을 삭제하면서 내용을 가져온다
git stash clear // 임시 저장 내역을 모두 삭제한다 (너무 많은 stash 내역이 쌓였을 때)
```

`git stash`를 더 딥하게 쓸 수도 있지만, 간단하게 이 정도만 쓰는 걸 추천한다. stash를 한 후 곧바로 stash pop을 할 상황이 아니라면 쓰지 않는 게 좋다. 오래 임시 저장 내용을 방치해야하는 상황이라면 commit을 이용하는게 낫다.
