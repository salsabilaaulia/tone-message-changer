package salsabilaaulia.tonemessagechanger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import salsabilaaulia.tonemessagechanger.dto.MessageRequest;
import salsabilaaulia.tonemessagechanger.service.ClientService;

@Controller
public class ClientController {

    @Autowired
    ClientService clientService;

    @GetMapping("/")
    public String formMessage(Model model) {
        MessageRequest messageDTO = new MessageRequest();
        model.addAttribute("messageDTO", messageDTO);
        return "form-message";
    }

    @PostMapping("/change-tone")
    public String changeTone(@ModelAttribute("messageDTO") MessageRequest messageDTO, Model model) {
        String result = clientService.getResponse(messageDTO);

        model.addAttribute("messageDTO", messageDTO);
        model.addAttribute("result", result);
        return "form-message";
    }

}
