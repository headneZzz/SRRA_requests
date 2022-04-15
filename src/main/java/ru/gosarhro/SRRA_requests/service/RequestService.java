package ru.gosarhro.SRRA_requests.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.gosarhro.SRRA_requests.dto.RequestDto;
import ru.gosarhro.SRRA_requests.entity.personal_data.PersonalData;
import ru.gosarhro.SRRA_requests.entity.requests.Request;
import ru.gosarhro.SRRA_requests.mapper.RequestMapper;
import ru.gosarhro.SRRA_requests.repository.requests.RequestRepository;
import ru.gosarhro.SRRA_requests.util.Pager;
import ru.gosarhro.SRRA_requests.util.RequestFilter;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class RequestService {

    private static final int BUTTONS_TO_SHOW = 5;
    private static final int INITIAL_PAGE = 0;
    private static final int INITIAL_PAGE_SIZE = 15;
    private static final int[] PAGE_SIZES = {5, 10, 20};
    private final RequestRepository repository;
    private final RubricService rubricService;
    private final ThemeService themeService;
    private final SourceService sourceService;
    private final ExecutorService executorService;
    private final PaymentService paymentService;
    private final PersonalDataService personalDataService;
    private final RequestWithPersonalService requestWithPersonalService;
    private final RequestMapper requestMapper;
    private final ComputerWithAccessService computerWithAccessService;

    public ModelAndView request(
            Optional<Integer> page,
            Integer id,
            Integer outNumber,
            Integer smav,
            String subject,
            String answer,
            String executor,
            String executeDateFrom,
            String executeDateTo,
            String inNumFromOrg,
            Boolean caseIns,
            String initiator,
            String shipment,
            HttpServletRequest servletRequest
    ) {
        int evalPageSize = 1;
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
        Pageable pageable = PageRequest.of(evalPage, evalPageSize, Sort.by("id").descending());
        RequestFilter filter = new RequestFilter(id, outNumber, smav, subject, answer, executor, executeDateFrom, executeDateTo, inNumFromOrg, caseIns, initiator, shipment);
        Page<Request> requests;
        if (computerWithAccessService.getWhitelistOfIps().contains(servletRequest.getRemoteAddr()) && initiator != null && shipment != null) {
            if (!initiator.equals("") || !shipment.equals("")) {
                requests = requestWithPersonalService.getByFilterAndPersonal(filter, pageable);
            } else {
                requests = getByFilter(filter, pageable);
            }
        } else {
            requests = getByFilter(filter, pageable);
        }
        Page<RequestDto> requestsDto = new PageImpl<>(requests.get().map(requestMapper::convertToDto).collect(Collectors.toList()), pageable, requests.getTotalElements());
        Pager pager = new Pager(requests.getTotalPages(), requests.getNumber(), BUTTONS_TO_SHOW);
        ModelAndView modelAndView = new ModelAndView("request");
        modelAndView.addObject("filter", filter);
        modelAndView.addObject("requests", requestsDto);
        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        modelAndView.addObject("pager", pager);
        modelAndView.addObject("rubrics", rubricService.getAll());
        modelAndView.addObject("themes", themeService.getAll());
        modelAndView.addObject("sources", sourceService.getAll());
        modelAndView.addObject("executors", executorService.getAll());
        modelAndView.addObject("payments", paymentService.getAll());
        log.info(servletRequest.getRemoteAddr());
        if (computerWithAccessService.getWhitelistOfIps().contains(servletRequest.getRemoteAddr())) {
            int requestId = requestsDto.getContent().get(0).getId();
            PersonalData personalData = personalDataService.getById(requestId) == null ?
                    new PersonalData()
                    :
                    personalDataService.getById(requestId);
            modelAndView.addObject("personalData", personalData);
        }
        return modelAndView;
    }

    public ModelAndView requests(Optional<Integer> page, Integer id, Integer outNumber, Integer smav, String subject, String answer, String executor, String executeDateFrom, String executeDateTo, String inNumFromOrg, Boolean caseIns, String initiator, String shipment, HttpServletRequest servletRequest) {
        int evalPageSize = INITIAL_PAGE_SIZE;
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
        Pageable pageable = PageRequest.of(evalPage, evalPageSize, Sort.by("id").descending());
        RequestFilter filter = new RequestFilter(id, outNumber, smav, subject, answer, executor, executeDateFrom, executeDateTo, inNumFromOrg, caseIns, initiator, shipment);
        Page<Request> requests;
        if (computerWithAccessService.getWhitelistOfIps().contains(servletRequest.getRemoteAddr()) && initiator != null && shipment != null) {
            if (!initiator.equals("") || !shipment.equals("")) {
                requests = requestWithPersonalService.getByFilterAndPersonal(filter, pageable);
            } else {
                requests = getByFilter(filter, pageable);
            }
        } else {
            requests = getByFilter(filter, pageable);
        }
        Page<RequestDto> requestsDto = new PageImpl<>(requests.get().map(requestMapper::convertToDto).collect(Collectors.toList()), pageable, requests.getTotalElements());
        Pager pager = new Pager(requests.getTotalPages(), requests.getNumber(), BUTTONS_TO_SHOW);
        ModelAndView modelAndView = new ModelAndView(requests.getTotalElements() == 1 ? "forward:/request" : "requests");
        modelAndView.addObject("filter", filter);
        modelAndView.addObject("requests", requestsDto);
        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        modelAndView.addObject("pager", pager);
        modelAndView.addObject("themes", themeService.getAll());
        modelAndView.addObject("executors", executorService.getAll());
        return modelAndView;
    }

    public String saveRequest(Request request, PersonalData personalData, HttpServletRequest servletRequest, RedirectAttributes redirectAttributes) {
        save(request);
        if (computerWithAccessService.getWhitelistOfIps().contains(servletRequest.getRemoteAddr())) {
            personalDataService.save(new PersonalData(request.getId(), personalData.getRequestInitiator(), personalData.getShipment()));
        }
        redirectAttributes.addFlashAttribute("action", "Изменения сохранены");
        return "redirect:" + servletRequest.getHeader("Referer");
    }

    public Request getById(int id) {
        return repository.findById(id).orElse(null);
    }

    public List<Request> getAll() {
        return repository.findAll();
    }

    public Page<Request> getByFilter(RequestFilter filter, Pageable pageable) {
        return repository.findAll(filter.getSpecification(), pageable);
    }

    public Request save(Request request) {
        return repository.save(request);
    }

    public String printLetter(
            Request request,
            PersonalData personalData,
            Model model,
            HttpServletRequest servletRequest,
            RedirectAttributes redirectAttributes
    ) {
        if (getEndDate(request, personalData, servletRequest, redirectAttributes)) {
            return "redirect:" + servletRequest.getHeader("Referer");
        }
        if (personalData.getShipment().equals("")) {
            redirectAttributes.addFlashAttribute("action", "Не задана пересылка");
            return "redirect:" + servletRequest.getHeader("Referer");
        }
        model.addAttribute("request", request);
        model.addAttribute("personalData", personalDataService.getById(request.getId()).getShipment());
        return "print_page";
    }

    public String printInquiry(
            Request request,
            PersonalData personalData,
            Model model,
            HttpServletRequest servletRequest,
            RedirectAttributes redirectAttributes
    ) {
        if (getEndDate(request, personalData, servletRequest, redirectAttributes)) {
            return "redirect:" + servletRequest.getHeader("Referer");
        }
        if (personalData.getRequestInitiator().equals("")) {
            redirectAttributes.addFlashAttribute("action", "Не задан инициатор");
            return "redirect:" + servletRequest.getHeader("Referer");

        }
        model.addAttribute("request", request);
        model.addAttribute("personalData", personalDataService.getById(request.getId()).getRequestInitiator());
        model.addAttribute("isInquiry", true);
        return "print_page";
    }

    private boolean getEndDate(Request request, PersonalData personalData, HttpServletRequest servletRequest, RedirectAttributes redirectAttributes) {
        LocalDate endDate = getById(request.getId()).getEndDate();
        if (endDate == null || !LocalDate.now().isAfter(endDate.plusDays(10))) {
            save(request);
            if (computerWithAccessService.getWhitelistOfIps().contains(servletRequest.getRemoteAddr())) {
                personalDataService.save(new PersonalData(request.getId(), personalData.getRequestInitiator(), personalData.getShipment()));
            }
        }
        if (request.getEndDate() == null) {
            redirectAttributes.addFlashAttribute("action", "Не задана дата исполнения");
            return true;
        }
        return false;
    }
}
