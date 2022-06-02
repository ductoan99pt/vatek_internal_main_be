package asia.vatek.project.controller;

import asia.vatek.project.readable.request.CreateFormReq;
import asia.vatek.project.readable.response.employee.CreateFormRes;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor
@Log4j2
@RequestMapping("/api/form")
public class FormController {
    @PostMapping("/contact/create")
    public CreateFormRes createContactUsForm(@ModelAttribute CreateFormReq createFormReq){
        return null;
    }

    @PostMapping("/quote/create")
    public CreateFormRes createQuoteForm(@ModelAttribute CreateFormReq createFormReq){
        return null;
    }
}
