package byuntil.backend.research.service;

import byuntil.backend.research.domain.entity.Demo;
import byuntil.backend.research.domain.entity.Field;
import byuntil.backend.research.domain.repository.DemoRepository;
import byuntil.backend.research.domain.repository.FieldRepository;
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
    private final FieldRepository fieldRepository;

    public Long save(DemoDto demoDto){
        Demo demo = demoDto.toEntity();
        Field field = demoDto.getFieldDto().toEntity();
        demo.addField(field);
        fieldRepository.save(field);
        return demoRepository.save(demo).getId();
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

