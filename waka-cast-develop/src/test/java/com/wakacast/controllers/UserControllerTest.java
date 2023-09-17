package com.wakacast.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.wakacast.configurations.security_configs.JwtAuthenticationEntryPoint;
import com.wakacast.configurations.security_configs.JwtTokenUtil;
import com.wakacast.dto.LogOutRequestDto;
import com.wakacast.dto.EquipmentRequestedDto;
import com.wakacast.dto.ProfileSetupDto;
import com.wakacast.dto.SignUpDto;
import com.wakacast.dto.SpokenLanguageDto;
import com.wakacast.dto.search_criteria.RoleMatchSearchCriteria;
import com.wakacast.enums.LanguageProficiency;
import com.wakacast.services.UserServices;
import com.wakacast.services.service_impl.JwtUserDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserServices userServices;
    @MockBean
    private JwtTokenUtil jwtTokenUtil;
    @MockBean
    private JwtUserDetailsService userDetailsService;
    @MockBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Test
    void registerUser() throws Exception {
        SignUpDto dto = new SignUpDto();
        dto.setEmail("obum@gmail.com");
        dto.setUserPersona("TALENT");
        dto.setPassword("Umeigbo123#");
        dto.setConfirmPassword("Umeigbo123#");

        ObjectMapper objectMapper = new ObjectMapper();

        MvcResult result = mvc.perform(post("/signup")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(dto)))
                .andReturn();

        int status = result.getResponse().getStatus();
        assertEquals(201, status);

        SignUpDto badEmailDto = new SignUpDto();
        badEmailDto.setEmail("testmail.com@");
        badEmailDto.setUserPersona("TALENT");
        badEmailDto.setPassword("Umeigbo123#");
        badEmailDto.setConfirmPassword("Umeigbo123#");

        MvcResult result2 = mvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(badEmailDto)))
                .andReturn();

        int errorResponse = result2.getResponse().getStatus();
        assertEquals(400, errorResponse);

    }

    @Test
    void verifyAccount() throws Exception {
        MvcResult result = mvc.perform(get("/account-verification/token")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = result.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    void setUpProfile() throws Exception {
        ProfileSetupDto profileSetupDto = new ProfileSetupDto();
        profileSetupDto.setFirstName("Innocent");
        profileSetupDto.setSurname("Ogesiano");
        profileSetupDto.setDateOfBirth(LocalDate.of(2000, 10, 31));
        Set<String> roleTitles = new HashSet<>();
        roleTitles.add("Voice-over Artiste");
        profileSetupDto.setRoles(roleTitles);
        Set<String> genreTitles = new HashSet<>();
        genreTitles.add("Movie Production");
        profileSetupDto.setGenres(genreTitles);
        SpokenLanguageDto languageDto = new SpokenLanguageDto();
        languageDto.setLanguage("English");
        languageDto.setProficiency(LanguageProficiency.ADVANCED);
        Set<SpokenLanguageDto> languageDtos = new HashSet<>();
        languageDtos.add(languageDto);
        profileSetupDto.setSpokenLanguages(languageDtos);
        String email = "inno@gmail.com";

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        MvcResult result = mvc.perform(post("/profile-setup")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(profileSetupDto)))
                .andReturn();

        int status = result.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    void uploadProfilePic() throws Exception {
        MockMultipartFile file = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
        mvc.perform(multipart("/profile-setup/upload-profile-pic")
                .file(file))
                .andExpect(status().isOk());
    }

    @Test
    void uploadPortfolio() throws Exception {
        MockMultipartFile file = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
        mvc.perform(multipart("/profile-setup/upload-portfolio")
                        .file(file)
                        .param("portfolioType", "audio")
                        .param("portfolioTitle", "Intro"))
                .andExpect(status().isOk());
    }

    @Test
    void postEquipment() throws Exception {
        EquipmentRequestedDto equipmentRequestedDto = new EquipmentRequestedDto();
        ObjectMapper objectMapper = new ObjectMapper();
        mvc.perform(post("/post-equipment")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(equipmentRequestedDto)))
                .andExpect(status().isOk());
    }

    @Test
    void uploadEquipmentImage() throws Exception {
        MockMultipartFile file = new MockMultipartFile("data", "filename.txt", "text/plain", "some xml".getBytes());
        mvc.perform(multipart("/upload-equipment-image/1")
                        .file(file))
                .andExpect(status().isOk());
    }

    @Test
    void editPostedEquipment() throws Exception {
        mvc.perform(put("/edit-posted-equipment/1")
                .param("equipmentName", "Digital Camera"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteEquipment() throws Exception {
        mvc.perform(delete("/delete-equipment/1"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteUserAccount() throws Exception {
        mvc.perform(delete("/delete-user-account"))
                .andExpect(status().isOk());
    }

    @Test
    void logout() throws Exception {
        LogOutRequestDto logoutRequest = new LogOutRequestDto();
        ObjectMapper mapper = new ObjectMapper();
        mvc.perform(post("/sign-out")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(logoutRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void setupSearchAgent() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        RoleMatchSearchCriteria roleMatchSearchCriteria = new RoleMatchSearchCriteria();
        mvc.perform(post("/setup-search-agent")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(roleMatchSearchCriteria)))
                .andExpect(status().isOk());
    }

    @Test
    void editSearchAgent() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        RoleMatchSearchCriteria roleMatchSearchCriteria = new RoleMatchSearchCriteria();
        mvc.perform(post("/edit-search-agent/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(roleMatchSearchCriteria)))
                .andExpect(status().isOk());
    }

    @Test
    void removeSearchAgent() throws Exception {
        mvc.perform(delete("/remove-search-agent/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getLoggedInUser() throws Exception {
        mvc.perform(get("/user-details"))
                .andExpect(status().isOk());
    }

    @Test
    void getUserById() throws Exception {
        mvc.perform(get("/user-by-id/1"))
                .andExpect(status().isOk());
    }
}