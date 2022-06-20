package byuntil.backend.research.service;

import byuntil.backend.common.exception.research.NullFieldException;
import byuntil.backend.research.domain.entity.Demo;
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
        //thesisDto.name이 null이면 예외가 터져야함
        Field field = fieldRepository.findByName(thesisDto.getFieldName()).orElseThrow(()-> new NullFieldException());
        Thesis thesis = thesisDto.toEntity();
        field.addThesis(thesis);
        return thesisRepository.save(thesis).getId();
    }
    @Transactional
    public void update(ThesisDto thesisDto){
        Thesis thesis = thesisRepository.findById(thesisDto.getId()).orElseThrow(()
                ->new IllegalArgumentException("해당 논문이 없습니다. id = "+thesisDto.getId()));
        thesis.update(thesisDto);
        if(!thesis.getField().getName().equals(thesisDto.getFieldName())){
            //내용만 변경되는건 안됨 field는 field혼자만 변경되어야함
            Field field = fieldRepository.findByName(thesisDto.getFieldName()).get();
            field.addThesis(thesis);
        }
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
