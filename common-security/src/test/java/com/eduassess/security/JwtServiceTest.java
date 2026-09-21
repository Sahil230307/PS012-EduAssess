package com.eduassess.security;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {
 @Test void generatesAndParsesToken(){
   JwtService service=new JwtService("EduAssessSuperSecretKeyForDevelopmentOnly2026",3600000);
   String token=service.generateToken("1","student@eduassess.com","STUDENT");
   assertTrue(service.isValid(token));
   assertEquals("student@eduassess.com",service.parse(token).getSubject());
   assertEquals("STUDENT",service.parse(token).get("role",String.class));
 }
}
