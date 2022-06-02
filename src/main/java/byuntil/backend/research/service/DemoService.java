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
        field.addDemo(demo);
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
        Demo demo = demoRepository.findById(demoDto.getId()).orElseThrow(()->
                new IllegalArgumentException("찾는 연구분야가 없습니다. id = "+ demoDto.getId()));

        demo.update(demoDto);
        //근데 demo가 가지고 있는 field도 새로 넣어주어야함
        if(!demo.getField().getId().equals(demoDto.getFieldDto().getId())){
            //내용만 변경되는건 안됨 field는 field혼자만 변경되어야함
            Field field = fieldRepository.findById(demoDto.getFieldDto().getId()).get();
            field.addDemo(demo);
        }
    }
}

