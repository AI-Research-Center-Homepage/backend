package byuntil.backend.research.service;

import byuntil.backend.research.domain.entity.Demo;
import byuntil.backend.research.domain.repository.DemoRepository;
import byuntil.backend.research.dto.DemoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DemoService {
    private final DemoRepository demoRepository;

    public Long save(DemoDto demoDto){
        return demoRepository.save(demoDto.toEntity()).getId();
    }
    public Optional<Demo> findById(Long id){
        return demoRepository.findById(id);
    }
    public void deleteById(Long id){
        demoRepository.deleteById(id);
    }
    public void update(DemoDto demoDto){

    }
}

