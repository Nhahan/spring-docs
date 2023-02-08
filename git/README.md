# Git

## Git이란?

- 형상 관리 도구
  - 버전 관리 시스템이라고도 한다.
- 분산형 관리 시스템
  - 중앙 서버에서 소스코드와 히스토리를 저장하지 않고 여러 로컬 저장소들을 이용하여 버전을 관리한다. 따라서 중앙서버에 장애가 발생하거나 인터넷이 끊겼거나(혹은 인터넷이 되지 않는 환경에 있더라도) 하더라도 로컬에서 계속 버전관리를 해나아갈 수 있다.
  
![image](https://user-images.githubusercontent.com/81916648/217448434-f5da7676-46dd-4f10-afd0-5a499e61c644.png)

(깃을 쓰지 않던 시절의 누군가)

![image](https://user-images.githubusercontent.com/81916648/217448530-ebadcd22-9492-4d96-8585-70bb22846dbf.png)

(깃을 쓰고 난 후의 누군가)

<br>
<hr>
  
![image](https://user-images.githubusercontent.com/81916648/217444143-08a130d5-6974-44bb-9df1-0eb12003467f.png)

git에서 관리하는 영역은 크게 3가지가 있습니다. 

- 현재 작업중인 **Working Directory**
- commit 할 파일의 예비 저장소, 혹은 추적 대상 파일의 공간인 **Staging Area**
- 각 유저의 컴퓨터에서 관리되고 있는 **Remote Repository**

현재 눈으로 확인하며 작업중인 곳을 Working Directory라고 하고, Working Directory에서 작업한 내용을 Add 명령어로 Local Repository에 추가할 대상들을 모아놓는 곳이 Staging Area이다. 그리고 Commit으로 Staging Area를 결과적으로 Local Repository에 저장하게 되고, 마지막으로 Remote Repository에 Push를 하게 된다.

