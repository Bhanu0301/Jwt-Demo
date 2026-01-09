# 🔐 Spring Security with JWT – Authentication & Authorization Flow

This project demonstrates **stateless authentication and authorization** using **Spring Boot + Spring Security + JWT**.

It focuses on **how Spring Security actually works internally**, not just “making JWT work”.

---

👉 **Visual Flow (Excalidraw Diagram):**  
https://excalidraw.com/#json=A7qHTe9qg0P5vaZen0BWt,9KoaYYhOL1J50e2CkyUjSw

This diagram explains:
- Where `JwtAuthFilter` sits in the filter chain
- How login and JWT flows are separated
- When `SecurityContext` is populated
- How requests reach the controller securely

---

## 🔑 Authentication Flow (Login)

1. Client sends **username + password**
2. Request enters **Spring Security Filter Chain**
3. `UsernamePasswordAuthenticationFilter` intercepts login request
4. Delegates authentication to `AuthenticationManager`
5. `AuthenticationProvider`:
   - Loads user via `UserDetailsService`
   - Validates password using `PasswordEncoder`
6. On success:
   - Authentication is stored in `SecurityContext`
   - JWT is generated and returned to the client

---

## 🔐 Authorization Flow (JWT)

1. Client sends request with `Authorization: Bearer <JWT>`
2. Request enters **Filter Chain**
3. `JwtAuthFilter`:
   - Extracts JWT from header
   - Validates token
   - Loads user details
   - Sets authenticated user in `SecurityContext`
4. Request proceeds to:
   - Controller → Service → Database
5. Response is returned securely

🚫 No session  
🚫 No server-side state  
✅ Fully stateless

---

LinkedIn share: 
https://www.linkedin.com/posts/vanipenta-bhanuprakash-reddy-726a38204_springboot-springsecurity-jwt-activity-7415462121347403776-IGJ9?utm_source=share&utm_medium=member_android&rcm=ACoAADQfZQ4BFlIIEXfWTty4ph9iPdeu07kCJE0
