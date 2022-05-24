package byuntil.backend.admin.service;

import byuntil.backend.admin.domain.Admin;
import byuntil.backend.admin.domain.dto.AdminDto;
import byuntil.backend.admin.repository.AdminRepository;
import byuntil.backend.common.exception.LoginIdDuplicationException;
import byuntil.backend.member.domain.repository.MemberRepository;
import byuntil.backend.member.dto.request.MemberUpdateRequestDto;
import byuntil.backend.member.service.MemberService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public Long signUp(Admin admin){
        //중복회원이 있을 시 예외
        String encodedPw = passwordEncoder.encode(admin.getLoginPw());

        admin.changePw(encodedPw);

        return adminRepository.save(admin).getId();
    }
    @Transactional
    public void encodedPw(Admin admin){
        //암호화하고 update
        String encodedPw = passwordEncoder.encode(admin.getLoginPw());
        admin.changePw(encodedPw);
    }

    public Optional<Admin> findById(Long id){
        return adminRepository.findById(id);
    }
    public Optional<Admin> findByLoginId(String loginId){
        return adminRepository.findByLoginId(loginId);
    }

    public void deleteById(Long id) {
        adminRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Admin> result = adminRepository.findByLoginId(username);
        if(!result.isPresent()){
            throw new UsernameNotFoundException("Check Id");
        }
        Admin admin = result.get();

        Collection<GrantedAuthority> auth = new ArrayList<>();
        auth.add(new SimpleGrantedAuthority(admin.getRole().toString()));
        AdminDto dto = new AdminDto(admin.getLoginId(), admin.getLoginPw(), auth, admin.getDeleted());

        return dto;
    }
}
