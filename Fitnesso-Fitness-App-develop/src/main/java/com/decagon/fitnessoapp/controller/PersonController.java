package com.decagon.fitnessoapp.controller;

import com.decagon.fitnessoapp.model.user.Person;
import com.decagon.fitnessoapp.model.user.ROLE_DETAIL;
import com.decagon.fitnessoapp.model.user.TrainerProfile;
import com.decagon.fitnessoapp.service.FavouriteService;
import com.decagon.fitnessoapp.service.PersonService;
import org.springframework.data.domain.Page;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import com.decagon.fitnessoapp.dto.*;
import com.decagon.fitnessoapp.service.VerificationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/person")
@AllArgsConstructor
public class PersonController {

    private final PersonService personService;
    private final FavouriteService favouriteService;
    public final VerificationService verificationTokenService;

    @PutMapping("/profile/edit/personinfo")
    public ResponseEntity<UpdatePersonResponse> editUserDetails(@RequestBody UpdatePersonRequest updatePersonDetails) {
        return ResponseEntity.ok().body( personService.updateUserDetails(updatePersonDetails));
    }

    @GetMapping("/profile")
    public ResponseEntity<PersonInfoResponse> getUserInfo() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok().body(personService.getInfo(authentication));
    }

    @GetMapping("/admin/stats")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AdminStats> getStats() {
        return ResponseEntity.ok().body(personService.getFitnessoDetails());
    }

    @PreAuthorize("hasRole('ROLE_PREMIUM') or hasRole('ROLE_ADMIN')")
    @PutMapping("/profile/edit/password")
    public  ResponseEntity<ChangePasswordResponse> editUserPassword(@RequestBody ChangePasswordRequest changePassword) {
        return ResponseEntity.ok().body(personService.updateCurrentPassword(changePassword));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register (@Valid @RequestBody PersonRequest personRequest) throws IOException {
        personRequest.setRoleDetail(ROLE_DETAIL.PREMIUM);
        return ResponseEntity.ok(personService.register(personRequest));
    }

    @PostMapping("/admin/register")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> registerAdmin (@Valid @RequestBody PersonRequest personRequest) throws IOException {
        personRequest.setRoleDetail(ROLE_DETAIL.ADMIN);
        return ResponseEntity.ok(personService.register(personRequest));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/trainer/register")
    public ResponseEntity<?> addTrainer (@Valid @RequestBody PersonRequest personRequest){
    return ResponseEntity.ok(personService.addTrainer(personRequest));
    }

    @PostMapping("/admin/add_trainer")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<TrainerResponse> registerTrainer(@RequestBody TrainerRequest request) {
        return ResponseEntity.ok().body(personService.addTrainerModel(request));
    }

    @GetMapping("/view_trainers")
    public ResponseEntity<List<TrainerProfile>> getTrainers() {
        return ResponseEntity.ok().body(personService.getTrainers());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/trainer/delete/{id}")
    public ResponseEntity<String> removeTrainer (@Valid @PathVariable ("id") Long id){
    return personService.removeTrainer(id);
}

    @GetMapping("/confirm")
    public ResponseEntity<PersonResponse> confirm (@RequestParam("token") String token){
        return ResponseEntity.ok(verificationTokenService.confirmToken(token));
    }

    @PostMapping("/resend-token")
    public ResponseEntity<PersonResponse> resendingEmailToken (@RequestBody EmailTokenRequest tokenRequest) {
        return ResponseEntity.ok(personService.sendingEmail(tokenRequest.getEmail()));
    }


    @PostMapping(path="/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest req) throws Exception {
        return personService.loginUser(req);
    }

    @PostMapping("/reset_password")
    public ResponseEntity<?> processResetPassword (@RequestBody EmailRequest resetEmail){
        return ResponseEntity.ok().body(personService.resetPasswordToken(resetEmail.getEmail()));
    }

    @PutMapping("/update_password")
    public ResponseEntity<?> updatePassword(@RequestBody ResetPasswordRequest passwordRequest, @RequestParam(value = "token") String token){
        return ResponseEntity.ok().body(personService.updateResetPassword(passwordRequest, token));
    }

    @PostMapping("/admin/reset_password")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PersonResponse> adminProcessResetPassword (@RequestBody EmailRequest resetEmail)  {
        return ResponseEntity.ok().body(personService.resetPasswordToken(resetEmail.getEmail()));
    }

    @PutMapping("/admin/update_password")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> adminUpdatePassword(@RequestBody ResetPasswordRequest passwordRequest, @RequestParam(value = "token") String token){
        return ResponseEntity.ok().body(personService.updateResetPassword(passwordRequest, token));
    }
    @PostMapping("/add_or_delete_favourite/{productId}")
    public ResponseEntity<String> addOrDeleteFavourite(@PathVariable("productId") Long productId, Authentication authentication){
        return favouriteService.addOrDeleteFavourite(productId, authentication);
    }

    @PostMapping("/handle_favourite/{username}/{productId}")
    public ResponseEntity<Boolean> handleFavourite(@PathVariable("username") String username, @PathVariable("productId") Long productId){
        return ResponseEntity.ok().body(favouriteService.handleFavourite(username, productId));
    }

    @PostMapping("/check_fave_default/{username}/{productId}")
    public ResponseEntity<Boolean> checkFaveDefault(@PathVariable("username") String username, @PathVariable("productId") Long productId){
        return ResponseEntity.ok().body(favouriteService.checkFaveDefault(username, productId));
    }

    @GetMapping("/view_favourites")
     public ResponseEntity<List<ProductResponseDto>> viewFavourites(Authentication authentication){
        return ResponseEntity.ok().body(favouriteService.viewFavourites(authentication));
    }

    @GetMapping("/admin/view_users/{pageNumber}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Page<Person>> viewUsers(@PathVariable("pageNumber") int pageNumber) {
        return ResponseEntity.ok().body(personService.getAllUsers(pageNumber));
    }

}

