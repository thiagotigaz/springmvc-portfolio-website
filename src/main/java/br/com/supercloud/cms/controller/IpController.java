package br.com.supercloud.cms.controller;


import br.com.supercloud.cms.service.IpHolderService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ip")
public class IpController {

    @Autowired
    private IpHolderService ipHolderService;

    @GetMapping
    public ResponseEntity<String> getIp() {
        return new ResponseEntity<String>(ipHolderService.getIp(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> setIp(HttpServletRequest request) {
        String header = request.getHeader("X-FORWARDED-FOR");
        if (!StringUtils.isEmpty(header)) {
            ipHolderService.setIp(header);
        } else {
            ipHolderService.setIp(request.getRemoteAddr());
        }
        return ResponseEntity.ok().build();
    }
}
