package byuntil.backend.admin.service;

import byuntil.backend.admin.domain.Admin;
import byuntil.backend.admin.domain.dto.AdminDto;
import byuntil.backend.admin.repository.AdminRepository;
import byuntil.backend.common.exception.LoginIdDuplicationException;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long signUp(AdminDto dto){
        //중복회원이 있을 시 예외
        adminRepository.findByLoginId(dto.getLoginId()).ifPresent((m -> {
            throw new LoginIdDuplicationException("이미 존재하는 회원입니다.");
        }));

        String encodedPw = passwordEncoder.encode(dto.getPassword());

        Admin admin = dto.toEntity();
        admin.changePw(encodedPw);


        return adminRepository.save(admin).getId();
    }

    public Optional<Admin> findById(Long id){
        return adminRepository.findById(id);
    }
    public Optional<Admin> findByLoginId(String loginId){
        return adminRepository.findByLoginId(loginId);
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
        AdminDto dto = new AdminDto(admin.getLoginId(), admin.getLoginPw(), auth);

        return dto;
    }
}
