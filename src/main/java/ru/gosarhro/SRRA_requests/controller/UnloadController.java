package ru.gosarhro.SRRA_requests.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gosarhro.SRRA_requests.service.UnloadService;
import ru.gosarhro.SRRA_requests.util.UnloadFilter;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/unload")
@RequiredArgsConstructor
public class UnloadController {

    private final UnloadService unloadService;

    @GetMapping()
    public String unloadFromDb(Model model, HttpServletRequest servletRequest) {
        return unloadService.unloadFromDb(model, servletRequest);
    }


    @PostMapping()
    public String unloadFromDbWithFilter(@ModelAttribute("unloadFilter") UnloadFilter unloadFilter, Model model, HttpServletRequest servletRequest) {
        return unloadService.unloadFromDbWithFilter(unloadFilter, model, servletRequest);
    }
}
