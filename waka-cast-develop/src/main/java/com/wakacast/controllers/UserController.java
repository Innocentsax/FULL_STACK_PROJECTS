package com.wakacast.controllers;

import com.wakacast.dto.*;
import com.wakacast.dto.search_criteria.RoleMatchSearchCriteria;
import com.wakacast.enums.PortfolioType;
import com.wakacast.responses.HttpResponse;
import com.wakacast.responses.PersonaResponse;
import com.wakacast.responses.ResponseData;
import com.wakacast.services.UserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserServices userServices;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignUpDto signUpDto) throws IOException {
        String message = userServices.signup(signUpDto);
        return new ResponseEntity<>(message, CREATED);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = "/account-verification/{token}")
    public ResponseEntity<ResponseData> verifyAccount(@PathVariable String token) {
        userServices.verifyAccount(token);
        return response(OK,"Account Activated Successfully");
    }

    @GetMapping("/user-details")
    public ResponseEntity<UserDto> getLoggedInUser() {
        return new ResponseEntity<>(userServices.getLoggedInUser(), OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/profile-setup")
    public ResponseEntity<ResponseData> setUpProfile(@Valid @RequestBody ProfileSetupDto setupDto) {
        return response(OK, userServices.setUpProfile(setupDto));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/profile-setup/upload-profile-pic")
    public ResponseEntity<ResponseData> uploadProfilePic(@RequestPart MultipartFile profilePic) throws IOException {
        return response(OK, userServices.uploadProfilePic(profilePic));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/profile-setup/upload-portfolio", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseData> uploadPortfolio(@RequestPart MultipartFile portfolio, @RequestParam String portfolioTitle,
                                                        @RequestParam PortfolioType portfolioType) throws IOException {
        PortfolioDto portfolioDto = new PortfolioDto(portfolioTitle, portfolioType);
        return response(OK, userServices.uploadPortfolio(portfolio, portfolioDto));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/post-equipment")
    public ResponseEntity<ResponseData> postEquipment(@RequestBody EquipmentRequestedDto equipmentRequestedDto) {
        return response(OK, userServices.postEquipmentForLease(equipmentRequestedDto));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping(value = "/upload-equipment-image/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResponseData> uploadEquipmentImage(@PathVariable long id, @RequestPart MultipartFile image) throws IOException {
        return response(OK, userServices.uploadEquipmentImage(id, image));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping(value = "/edit-posted-equipment/{id}")
    public ResponseEntity<ResponseData> editPostedEquipment(@PathVariable long id, @RequestParam String equipmentName) {
        return response(OK, userServices.editPostedEquipment(id, equipmentName));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping(value = "/delete-equipment/{id}")
    public ResponseEntity<ResponseData> deleteEquipment(@PathVariable long id) {
        return response(OK, userServices.deletePostedEquipment(id));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping(value = "/delete-user-account")
    public ResponseEntity<ResponseData> deleteUserAccount() {
        return response(OK, userServices.deleteUserAccount());
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping( "/sign-out")
    public ResponseEntity<ResponseData> logout(@Valid @RequestBody LogOutRequestDto logOutRequest) {
        return response(OK, userServices.logout(logOutRequest));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/setup-search-agent")
    public ResponseEntity<ResponseData> setupSearchAgent(@RequestBody RoleMatchSearchCriteria roleMatchSearchCriteria) {
        return response(OK, userServices.setUpSearchAgent(roleMatchSearchCriteria));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/edit-search-agent/{id}")
    public ResponseEntity<ResponseData> editSearchAgent(@PathVariable long id, @RequestBody RoleMatchSearchCriteria roleMatchSearchCriteria) {
        return response(OK, userServices.editSearchAgent(id, roleMatchSearchCriteria));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/remove-search-agent/{id}")
    public ResponseEntity<ResponseData> removeSearchAgent(@PathVariable long id) {
        return response(OK, userServices.removeSearchAgent(id));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/user-personas")
    public ResponseEntity<PersonaResponse> userPersonas () {
        List<String> personas = List.of("Talent", "Producer", "Crew_member");
        return new ResponseEntity<>(new PersonaResponse(personas), OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/user-by-id/{id}")
    public ResponseEntity<UserDto> getUserById (@PathVariable Long id) {
        return new ResponseEntity<>(userServices.getUserById(id), OK);
    }

    public static ResponseEntity<ResponseData> response(HttpStatus httpStatus, String message) {
        return new  ResponseEntity<>(new ResponseData(new HttpResponse(httpStatus.value(),
                httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message.toUpperCase())), httpStatus );
    }
}
