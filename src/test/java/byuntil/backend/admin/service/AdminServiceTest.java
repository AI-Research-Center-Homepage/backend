package byuntil.backend.admin.service;

import byuntil.backend.admin.domain.Admin;
import byuntil.backend.admin.domain.UserRole;
import byuntil.backend.admin.domain.dto.AdminDto;
import byuntil.backend.admin.repository.AdminRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminServiceTest {
    @Autowired
    private AdminService adminService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Test
    public void 회원가입(){
        //given
        Collection<SimpleGrantedAuthority> auths = new ArrayList<>();
        auths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        AdminDto adminDto = new AdminDto("loId1","비번1", auths);

        //when
        Long id = adminService.signUp(adminDto);
        Admin admin = adminService.findById(id).get();
        //then

        //dto와 entity가 잘 변환됐는가?
        Assertions.assertEquals(admin.getLoginId(), adminDto.getLoginId());
        //pw가 잘 변환되었고 dto와 entity가 잘변환되었는가?
        Assertions.assertTrue(passwordEncoder.matches(adminDto.getPassword(), admin.getLoginPw()));
        //뒤에 toString까지 있어야 제대로 test가 성공함
        Assertions.assertTrue(("ROLE_" + admin.getRole()).equals(auths.toArray()[0].toString()));

    }

}