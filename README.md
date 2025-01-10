<div align="center">
	<h1>Spring Auth</h1>
	<p>
		<b>JWT(Json Web Token) 기반 인증 시스템 구축</b>
	</p>
	<br>
</div>

> [!NOTE]
> 프로젝트의 목표는 기존과 다른 방식으로 JWT (Json Web Token) 기반 인증 시스템을 구축하는 것이다.

## 🔐 인증 구현 방식

### 1. 기존 방식

- AT (Access Token) / RT (Refresh Token) 모두 클라이언트에서 저장 및 관리
- AT와 RT 각각 다른 **Secret Key**로 생성

```mermaid
---
title: 기존 방식의 토큰 발급 과정
---

graph LR;
    login("로그인 요청 (ID, PW)") --> check{"ID, PW 검증"};
    check --> |성공| create_access_token("AT 생성 (ID, Role)");
    access_token_key("AT Secret Key") --> create_access_token;
    check --> |실패| error("Error 메시지 반환");
    create_access_token --> create_refresh_token("RT 생성 (ID)");
    refresh_token_key("RT Secret Key") --> create_refresh_token;
    create_refresh_token --> return("AT/RT 반환");
```

```mermaid
---
title: 기존 방식의 토큰 재발급 과정
---

graph LR;
    refresh("재발급 요청 (RT)") --> check_refresh_token{"RT 디코딩"};
    check_refresh_token --> |"성공 (ID)"| search_user_info{"ID 검색"};
    search_user_info --> |성공| create_access_token("AT 생성 (ID, Role)");
    search_user_info --> |실패| error("Error 메시지 반환");
    access_token_key("AT Secret Key") --> create_access_token;
    create_access_token --> create_refresh_token("RT 생성 (ID)");
    refresh_token_key("RT Secret Key") --> create_refresh_token;
    create_refresh_token --> return("AT/RT 반환");
```

### 2. 새로운 방식

- AT는 클라이언트에서 저장 및 관리
- RT는 클라이언트와 서버에서 저장 및 관리
- AT와 RT 같은 **Secret Key**로 생성

```mermaid
---
title: 새로운 방식의 토큰 발급 과정
---

graph LR;
    login("로그인 요청 (ID, PW)") --> check{"ID, PW 검증"};
    
    check --> |성공| create_access_token("AT 생성 (ID, Role)");
    check --> |실패| error("Error 메시지 반환");
    
    key("Secret Key") --> create_access_token;
    create_access_token --> create_refresh_token("RT 생성");
    key --> create_refresh_token;
    create_refresh_token --> |"RT 저장 {RT: ID}"| redis[(Redis)];
    create_refresh_token --> return("AT/RT 반환");
```

```mermaid
---
title: 새로운 방식의 토큰 재발급 과정
---

graph LR;
    refresh("재발급 요청 (AT, RT)") --> parse_access_token{"만료된 AT 디코딩"};
    
    parse_access_token --> |"성공 (ID)"| search_from_redis{"Redis RT 검색"};
    parse_access_token --> |"실패"| error("Error 메시지 반환");
    
    search_from_redis --> |"성공 {RT: ID}"| check_id{"ID 검증"};
    search_from_redis --> |"실패"| error;
    
    check_id --> |"성공"| create_access_token("AT 생성 (ID, Role)");
    check_id --> |"실패"| error;
    
    key("Secret Key") --> create_access_token;
    create_access_token --> create_refresh_token("RT 생성 (ID)");
    key --> create_refresh_token;
    create_refresh_token --> |"RT 저장 {RT: ID}"| redis[(Redis)];
    create_refresh_token --> return("AT/RT 반환");
```

