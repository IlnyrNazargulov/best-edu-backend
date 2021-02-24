package ru.ilnyrdiplom.bestedu.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.ilnyrdiplom.bestedu.facade.exceptions.DisciplineAlreadyExistsException;
import ru.ilnyrdiplom.bestedu.facade.exceptions.EntityNotFoundException;
import ru.ilnyrdiplom.bestedu.facade.exceptions.ImpossibleAccessDisciplineException;
import ru.ilnyrdiplom.bestedu.facade.exceptions.WrongAccountTypeException;
import ru.ilnyrdiplom.bestedu.facade.model.DisciplineFacade;
import ru.ilnyrdiplom.bestedu.facade.model.enums.Role;
import ru.ilnyrdiplom.bestedu.facade.services.DisciplineServiceFacade;
import ru.ilnyrdiplom.bestedu.web.contracts.requests.DisciplineRequest;
import ru.ilnyrdiplom.bestedu.web.contracts.responses.ApiResponse;
import ru.ilnyrdiplom.bestedu.web.model.TokenPrincipal;

import java.util.List;

@RestController
@RequestMapping(value = "/disciplines/", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class DisciplineController {

    private final DisciplineServiceFacade disciplineService;

    @Secured(Role.TEACHER)
    @PostMapping
    public ResponseEntity<ApiResponse<DisciplineFacade>> addDiscipline(@AuthenticationPrincipal TokenPrincipal tokenPrincipal,
                                                                       @RequestBody DisciplineRequest disciplineRequest
    ) throws DisciplineAlreadyExistsException, EntityNotFoundException {
        DisciplineFacade discipline = disciplineService
                .createDiscipline(tokenPrincipal.getAccountIdentity(), disciplineRequest.getName(), disciplineRequest.isPublic());
        return ApiResponse.success(discipline);
    }

    @Secured(Role.TEACHER)
    @PutMapping("/{disciplineId}/")
    public ResponseEntity<ApiResponse<DisciplineFacade>> updateDiscipline(@AuthenticationPrincipal TokenPrincipal tokenPrincipal,
                                                                          @PathVariable int disciplineId,
                                                                          @RequestBody DisciplineRequest disciplineRequest
    ) throws EntityNotFoundException {
        DisciplineFacade discipline = disciplineService
                .updateDiscipline(tokenPrincipal.getAccountIdentity(), () -> disciplineId, disciplineRequest.getName());
        return ApiResponse.success(discipline);
    }

    @Secured({Role.TEACHER, Role.STUDENT})
    @GetMapping
    public ResponseEntity<ApiResponse<List<? extends DisciplineFacade>>> getDisciplines(
            @AuthenticationPrincipal TokenPrincipal tokenPrincipal,
            @RequestParam(required = false) Integer teacherId,
            @RequestParam(required = false) String teacherFullName,
            @RequestParam(required = false) String nameDiscipline

    ) throws EntityNotFoundException {
        List<? extends DisciplineFacade> disciplines = disciplineService
                .getDisciplines(tokenPrincipal.getAccountIdentity(), () -> teacherId, teacherFullName, nameDiscipline);
        return ApiResponse.success(disciplines);
    }

    @Secured({Role.TEACHER, Role.STUDENT})
    @GetMapping("/{disciplineId}/")
    public ResponseEntity<ApiResponse<DisciplineFacade>> getDiscipline(
            @AuthenticationPrincipal TokenPrincipal tokenPrincipal,
            @PathVariable int disciplineId
    ) throws EntityNotFoundException, WrongAccountTypeException, ImpossibleAccessDisciplineException {
        DisciplineFacade discipline = disciplineService
                .getDiscipline(tokenPrincipal.getAccountIdentity(), () -> disciplineId);
        return ApiResponse.success(discipline);
    }

    @Secured(Role.TEACHER)
    @DeleteMapping("/{disciplineId}/")
    public ResponseEntity<ApiResponse<DisciplineFacade>> deleteDiscipline(@AuthenticationPrincipal TokenPrincipal tokenPrincipal,
                                                                          @PathVariable int disciplineId
    ) throws EntityNotFoundException {
        DisciplineFacade discipline = disciplineService
                .deleteDiscipline(tokenPrincipal.getAccountIdentity(), () -> disciplineId);
        return ApiResponse.success(discipline);
    }
}
