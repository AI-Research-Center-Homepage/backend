package byuntil.backend.admin.service;

import byuntil.backend.admin.domain.Admin;
import byuntil.backend.admin.domain.UserRole;
import byuntil.backend.admin.domain.dto.AdminDto;
import byuntil.backend.admin.repository.AdminRepository;
import byuntil.backend.common.exception.LoginIdDuplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminService {
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
}
