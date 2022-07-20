package byuntil.backend.research.service;

import byuntil.backend.research.domain.entity.Demo;
import byuntil.backend.research.domain.repository.DemoRepository;
import byuntil.backend.research.dto.request.DemoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DemoService {
    private final DemoRepository demoRepository;

    @Transactional
    public Long save(final DemoDto demoDto) {
        Demo demo = demoDto.toEntity();
        return demoRepository.save(demo).getId();
    }

    public Optional<Demo> findById(final Long id) {
        return demoRepository.findById(id);
    }

    public List<Demo> findAll() {
        return demoRepository.findAll();
    }

    @Transactional
    public void deleteById(final Long id) {
        demoRepository.deleteById(id);
    }

    @Transactional
    public void update(final DemoDto demoDto) {
        /*Demo demo = demoRepository.findById(demoDto.getId()).orElseThrow(() ->
                new IllegalArgumentException("찾는 연구분야가 없습니다. id = " + demoDto.getId()));*/
        demoRepository.findById(demoDto.getId()).ifPresentOrElse(demo -> {demo.update(demoDto);},
                () ->  new IllegalArgumentException("찾는 연구분야가 없습니다. id = " + demoDto.getId()));


    }
}

