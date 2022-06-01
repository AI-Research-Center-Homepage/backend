package byuntil.backend.research.service;

import byuntil.backend.research.domain.entity.Field;
import byuntil.backend.research.domain.entity.Thesis;
import byuntil.backend.research.domain.repository.FieldRepository;
import byuntil.backend.research.dto.FieldDto;
import byuntil.backend.research.dto.ThesisDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FieldService {

    private final FieldRepository fieldRepository;

    @Transactional
    public Long save(FieldDto fieldDto){
        //dto -> entity
        //repository에 저장
        Field field = fieldDto.toEntity();
        return fieldRepository.save(field).getId();
    }
    //member_thesis는 어떻게?
    @Transactional
    public Long update(Long id, FieldDto fieldDto){
        Field origin = fieldRepository.findById(id).orElseThrow(()->new IllegalArgumentException("찾는 연구분야가 없습니다. id = "+id));
        origin.update(fieldDto.getName(), fieldDto.getDescription());

        return id;
    }
    @Transactional
    public void delete(Long id){
        fieldRepository.deleteById(id);
    }
    public Optional<Field> findById(Long id){
        return fieldRepository.findById(id);
    }
    public List<Field> findAll(){
        return fieldRepository.findAll();
    }
}
