package byuntil.backend.admin.controlller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/sample/")
public class SampleController {
    @GetMapping("/all")
    public String exAll(){
        log.info("exAll..");
        return "akk";
    }
    @GetMapping("/member")
    public String exMember(){
        log.info("exMember");
        return "member";
    }
    @GetMapping("/admin")
    public String exAdmin(){
        log.info("exAdmin");
        return "exadmin";
    }
}
