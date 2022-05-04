package byuntil.backend.admin.service;

import byuntil.backend.admin.domain.Admin;
import byuntil.backend.admin.domain.dto.AdminDto;
import byuntil.backend.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {
    private final AdminRepository adminRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("username : " + username);

        Optional<Admin> result = adminRepository.findByLoginId(username);
        if(!result.isPresent()){
            throw new UsernameNotFoundException("Check Id");
        }
        Admin admin = result.get();
        AdminDto dto = new AdminDto(admin.getLoginId(), admin.getLoginPw(), admin.getRoleSet().stream()
                .map(userRole -> new SimpleGrantedAuthority("ROLE_" + userRole.name())).collect(Collectors.toSet()));
        return dto;
    }
}
