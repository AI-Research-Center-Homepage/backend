package byuntil.backend.admin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class PasswordTests {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testEncode(){
        //given
        String password = "111";
        String enPw = passwordEncoder.encode(password);

        //when
        System.out.println("암호화된 비밀번호 : " + enPw);
        boolean matchResult = passwordEncoder.matches(password, enPw);

        //then
        Assertions.assertTrue(matchResult);
    }
}
