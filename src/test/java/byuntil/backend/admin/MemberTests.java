package byuntil.backend.admin;

import byuntil.backend.admin.domain.Admin;
import byuntil.backend.admin.domain.UserRole;
import byuntil.backend.admin.repository.AdminRepository;
import byuntil.backend.member.domain.entity.member.Member;
import byuntil.backend.member.domain.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;

@SpringBootTest
public class MemberTests {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void 권한확인(){
        //given
        Optional<Admin> result = adminRepository.findByLoginId("user2");
        //when
        Set<UserRole> roles = result.get().getRoleSet();
        //then
        Assertions.assertThat(roles.contains(UserRole.ADMIN));
    }
    @Test
    public void testRead(){
        //given
        Optional<Admin> result = adminRepository.findByLoginId("user2");
        //when
        Admin admin = result.get();
        //then
        Assertions.assertThat(admin.getLoginId()).isEqualTo("user2");
    }
    @Test
    public void insertDummies(){
        //10개의 user생성
        //given
        IntStream.rangeClosed(1,10).forEach(i -> {
            Admin admin = Admin.builder().loginId("user" + i).loginPw(passwordEncoder.encode("111")).build();
            admin.addUserRole(UserRole.ADMIN);
            adminRepository.save(admin);
        });
        //when
        //then
        Assertions.assertThat(adminRepository.findAll().size()).isEqualTo(10);
    }
}
