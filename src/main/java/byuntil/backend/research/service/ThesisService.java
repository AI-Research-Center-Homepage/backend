package byuntil.backend.research.service;

import byuntil.backend.research.domain.entity.Field;
import byuntil.backend.research.domain.entity.Thesis;
import byuntil.backend.research.domain.repository.FieldRepository;
import byuntil.backend.research.domain.repository.ThesisRepository;
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
public class ThesisService {
    private final FieldRepository fieldRepository;
    private final ThesisRepository thesisRepository;
    @Transactional
    public Long save(ThesisDto thesisDto){
        if(thesisDto.getFieldDto()==null){
            //그냥 thesisDto만 저장한다
            Thesis thesis = thesisDto.toEntity();
            return thesisRepository.save(thesis).getId();
        }
        else{
            Field field = thesisDto.getFieldDto().toEntity();
            fieldRepository.save(field);
            Thesis thesis = thesisDto.toEntity();
            thesis.setField(field);//연관관계설정
            return thesisRepository.save(thesis).getId();
        }
    }
    @Transactional
    public Long update(Long id, ThesisDto thesisDto){
        Thesis origin = thesisRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 논문이 없습니다. id = "+id));
        origin.update(thesisDto);
        return id;
    }
    @Transactional
    public void delete(Long id){
        thesisRepository.deleteById(id);
    }
    public Optional<Thesis> findById(Long id){
        return thesisRepository.findById(id);
    }
    public List<Thesis> findAll(){
        return thesisRepository.findAll();
    }
}
