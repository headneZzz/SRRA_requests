package ru.gosarhro.SRRA_requests.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.gosarhro.SRRA_requests.entity.personal_data.PersonalData;
import ru.gosarhro.SRRA_requests.entity.requests.Request;
import ru.gosarhro.SRRA_requests.service.RequestService;
import ru.gosarhro.SRRA_requests.util.RequestFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
public class RequestController {

    private static final int INITIAL_PAGE = 0;
    private static final int INITIAL_PAGE_SIZE = 15;

    private final RequestService requestService;

    @GetMapping({"/requests", "/"})
    public ModelAndView requests(
            @RequestParam("page") Optional<Integer> page,
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) Integer outNumber,
            @RequestParam(required = false) Integer smav,
            @RequestParam(required = false) String subject,
            @RequestParam(required = false) String answer,
            @RequestParam(required = false) String executor,
            @RequestParam(required = false) String executeDateFrom,
            @RequestParam(required = false) String executeDateTo,
            @RequestParam(required = false) String inNumFromOrg,
            @RequestParam(required = false) Boolean caseIns,
            @RequestParam(required = false) String initiator,
            @RequestParam(required = false) String shipment,
            HttpServletRequest servletRequest
    ) {
        int evalPage = page.orElse(0) < 1 ? INITIAL_PAGE : page.get() - 1;
        Pageable pageable = PageRequest.of(evalPage, INITIAL_PAGE_SIZE, Sort.by("id").descending());
        RequestFilter filter = new RequestFilter(id, outNumber, smav, subject, answer, executor, executeDateFrom, executeDateTo, inNumFromOrg, caseIns, initiator, shipment);
        String remoteAddr = servletRequest.getRemoteAddr();
        return requestService.requests(filter, initiator, shipment, remoteAddr, pageable);
    }

    @GetMapping("/request")
    public ModelAndView request(
            @RequestParam("page") Optional<Integer> page,
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) Integer outNumber,
            @RequestParam(required = false) Integer smav,
            @RequestParam(required = false) String subject,
            @RequestParam(required = false) String answer,
            @RequestParam(required = false) String executor,
            @RequestParam(required = false) String executeDateFrom,
            @RequestParam(required = false) String executeDateTo,
            @RequestParam(required = false) String inNumFromOrg,
            @RequestParam(required = false) Boolean caseIns,
            @RequestParam(required = false) String initiator,
            @RequestParam(required = false) String shipment,
            HttpServletRequest servletRequest
    ) {
        int evalPageSize = 1;
        int evalPage = page.orElse(0) < 1 ? INITIAL_PAGE : page.get() - 1;
        Pageable pageable = PageRequest.of(evalPage, evalPageSize, Sort.by("id").descending());
        RequestFilter filter = new RequestFilter(id, outNumber, smav, subject, answer, executor, executeDateFrom, executeDateTo, inNumFromOrg, caseIns, initiator, shipment);
        String remoteAddr = servletRequest.getRemoteAddr();
        return requestService.request(filter, initiator, shipment, remoteAddr, pageable);
    }

    @GetMapping("/new")
    public String addNew() {
        requestService.save(new Request());
        return "redirect:/request";
    }

    @PostMapping(value = "/request/update", params = "save")
    public String saveRequest(
            @ModelAttribute("request") Request request,
            @ModelAttribute("personalData") PersonalData personalData,
            HttpServletRequest servletRequest,
            RedirectAttributes redirectAttributes
    ) {
        return requestService.saveRequest(request, personalData, servletRequest, redirectAttributes);
    }

    @PostMapping(value = "/request/update", params = "printInquiry")
    public String printInquiry(
            @ModelAttribute("request") Request request,
            @ModelAttribute("personalData") PersonalData personalData,
            Model model,
            HttpServletRequest servletRequest,
            RedirectAttributes redirectAttributes
    ) {
        return requestService.printInquiry(request, personalData, model, servletRequest, redirectAttributes);
    }

    @PostMapping(value = "/request/update", params = "printLetter")
    public String printLetter(
            @ModelAttribute("request") Request request,
            @ModelAttribute("personalData") PersonalData personalData,
            Model model,
            HttpServletRequest servletRequest,
            RedirectAttributes redirectAttributes
    ) {
        return requestService.printLetter(request, personalData, model, servletRequest, redirectAttributes);
    }
}
