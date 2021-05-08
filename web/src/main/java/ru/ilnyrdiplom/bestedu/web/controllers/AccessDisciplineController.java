package ru.ilnyrdiplom.bestedu.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.ilnyrdiplom.bestedu.facade.exceptions.EntityNotFoundException;
import ru.ilnyrdiplom.bestedu.facade.exceptions.WrongAccessDisciplineStatusException;
import ru.ilnyrdiplom.bestedu.facade.model.AccessDisciplineFacade;
import ru.ilnyrdiplom.bestedu.facade.model.enums.AccessDisciplineStatus;
import ru.ilnyrdiplom.bestedu.facade.model.enums.Role;
import ru.ilnyrdiplom.bestedu.facade.services.AccessDisciplineServiceFacade;
import ru.ilnyrdiplom.bestedu.web.contracts.responses.ApiResponse;
import ru.ilnyrdiplom.bestedu.web.model.TokenPrincipal;

import java.util.List;

@RestController
@RequestMapping(value = "/disciplines/", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AccessDisciplineController {

    private final AccessDisciplineServiceFacade accessDisciplineService;

    @Secured({Role.TEACHER, Role.STUDENT})
    @PostMapping("/{disciplineId}/access-discipline/")
    public ResponseEntity<ApiResponse<AccessDisciplineFacade>> createRequestAccessDiscipline(
            @AuthenticationPrincipal TokenPrincipal tokenPrincipal,
            @PathVariable int disciplineId
    ) throws WrongAccessDisciplineStatusException, EntityNotFoundException {
        AccessDisciplineFacade accessDiscipline = accessDisciplineService
                .createRequestAccessDiscipline(tokenPrincipal.getAccountIdentity(), () -> disciplineId);
        return ApiResponse.success(accessDiscipline);
    }

    @Secured(Role.TEACHER)
    @PutMapping("/{disciplineId}/access-discipline/{accessDisciplineId}/accept/")
    public ResponseEntity<ApiResponse<AccessDisciplineFacade>> acceptAccessDiscipline(@AuthenticationPrincipal TokenPrincipal tokenPrincipal,
                                                                                      @PathVariable int disciplineId,
                                                                                      @PathVariable int accessDisciplineId
    ) throws WrongAccessDisciplineStatusException, EntityNotFoundException {
        AccessDisciplineFacade accessDiscipline = accessDisciplineService
                .acceptAccessDiscipline(tokenPrincipal.getAccountIdentity(), () -> disciplineId, () -> accessDisciplineId);
        return ApiResponse.success(accessDiscipline);
    }

    @Secured(Role.TEACHER)
    @PutMapping("/{disciplineId}/access-discipline/{accessDisciplineId}/reject/")
    public ResponseEntity<ApiResponse<AccessDisciplineFacade>> rejectAccessDiscipline(@AuthenticationPrincipal TokenPrincipal tokenPrincipal,
                                                                                      @PathVariable int disciplineId,
                                                                                      @PathVariable int accessDisciplineId
    ) throws EntityNotFoundException {
        AccessDisciplineFacade accessDiscipline = accessDisciplineService
                .rejectAccessDiscipline(tokenPrincipal.getAccountIdentity(), () -> disciplineId, () -> accessDisciplineId);
        return ApiResponse.success(accessDiscipline);
    }

    @Secured({Role.TEACHER})
    @GetMapping("/{disciplineId}/access-discipline/")
    public ResponseEntity<ApiResponse<List<? extends AccessDisciplineFacade>>> getDisciplines(
            @AuthenticationPrincipal TokenPrincipal tokenPrincipal,
            @PathVariable int disciplineId,
            @RequestParam(required = false) AccessDisciplineStatus status
    ) throws EntityNotFoundException {
        List<? extends AccessDisciplineFacade> accessDisciplines = accessDisciplineService
                .getAccessDisciplines(tokenPrincipal.getAccountIdentity(), () -> disciplineId, status);
        return ApiResponse.success(accessDisciplines);
    }

}
