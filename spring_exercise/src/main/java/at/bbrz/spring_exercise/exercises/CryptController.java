package at.bbrz.spring_exercise.exercises;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/crypt")
public class CryptController {

    @Autowired
    private CryptService cryptService;

    @PostMapping(path = "encrypt")
    private CryptResponse encrypt(@RequestBody CryptRequest request) {
        return new CryptResponse(cryptService.xor(request.getKey(), request.getTxt()), request.getKey());
    }
}
