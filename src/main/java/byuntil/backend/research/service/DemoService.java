package byuntil.backend.research.service;

import byuntil.backend.common.exception.IdNotExistException;
import byuntil.backend.research.domain.entity.Demo;
import byuntil.backend.research.domain.repository.DemoRepository;
import byuntil.backend.research.dto.request.DemoDto;
import byuntil.backend.research.dto.response.AllDemoResponseDto;
import byuntil.backend.research.dto.response.IndividualDemoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    public IndividualDemoDto findById(final Long id) {
        Demo demo = demoRepository.findById(id).orElseThrow(()-> {
            throw new IdNotExistException();
        });
        return IndividualDemoDto.builder().content(demo.getContent()).name(demo.getTitle()).participants(demo.getParticipants())
                .url(demo.getUrl()).description(demo.getDescription()).build();
    }

    public List<AllDemoResponseDto> findAll() {
        List<AllDemoResponseDto> demos = new ArrayList<>();
        for (Demo demo: demoRepository.findAll()) {
            demos.add(AllDemoResponseDto.builder().title(demo.getTitle()).description(demo.getTitle()).url(demo.getUrl())
                    .id(demo.getId()).build());
        }
        return demos;
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

