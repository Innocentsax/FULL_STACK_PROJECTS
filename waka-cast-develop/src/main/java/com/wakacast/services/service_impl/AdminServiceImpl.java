package com.wakacast.services.service_impl;

import com.wakacast.dto.*;
import com.wakacast.dto.pages_criteria.CastCallPage;
import com.wakacast.dto.pages_criteria.CastCallReportPage;
import com.wakacast.dto.pages_criteria.UserPage;
import com.wakacast.enums.UserType;
import com.wakacast.exceptions.BadRequestException;
import com.wakacast.exceptions.ResourceNotFound;
import com.wakacast.exceptions.UnauthorizedException;
import com.wakacast.exceptions.UserWithEmailNotFound;
import com.wakacast.global_constants.Constants;
import com.wakacast.models.*;
import com.wakacast.repositories.*;
import com.wakacast.repositories.criteri_class.UserSearchCriteriaRepository;
import com.wakacast.requests.EmailRequest;
import com.wakacast.responses.JwtResponse;
import com.wakacast.services.AdminService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static java.security.SecureRandom.getInstanceStrong;

@Service
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final CastCallRepository castCallRepository;
    private final EmailService emailService;
    private final GenreRepository genreRepository;
    private final ModelMapper mapper;
    private final UserSearchCriteriaRepository userSearchCriteriaRepository;
    private final LanguageRepository languageRepository;
    private final ReasonForReportingCastCallRepository reasonForReportingCastCallRepository;
    private final ReportCastCallRepository reportCastCallRepository;
    private final RoleRepository roleRepository;
    private final UserServicesImpl userServices;
    private final PasswordEncoder passwordEncoder;
    private final Random rand;
    private final EquipmentTypeRepository equipmentTypeRepository;

    @Autowired
    public AdminServiceImpl(UserRepository userRepository, CastCallRepository castCallRepository,
                            EmailService emailService, GenreRepository genreRepository,
                            ModelMapper mapper, UserSearchCriteriaRepository userSearchCriteriaRepository,
                            LanguageRepository languageRepository, ReasonForReportingCastCallRepository reasonForReportingCastCallRepository,
                            ReportCastCallRepository reportCastCallRepository, RoleRepository roleRepository,
                            UserServicesImpl userServices, PasswordEncoder passwordEncoder,
                            EquipmentTypeRepository equipmentTypeRepository) throws NoSuchAlgorithmException {
        this.userRepository = userRepository;
        this.castCallRepository = castCallRepository;
        this.emailService = emailService;
        this.genreRepository = genreRepository;
        this.mapper = mapper;
        this.userSearchCriteriaRepository = userSearchCriteriaRepository;
        this.languageRepository = languageRepository;
        this.reasonForReportingCastCallRepository = reasonForReportingCastCallRepository;
        this.reportCastCallRepository = reportCastCallRepository;
        this.roleRepository = roleRepository;
        this.userServices = userServices;
        this.passwordEncoder = passwordEncoder;
        this.equipmentTypeRepository = equipmentTypeRepository;
        rand = getInstanceStrong();
    }

    @Override
    @Cacheable(cacheNames = "users")
    public Page<UserDto> getAllUsers(UserPage userPage) {
        Sort sort = Sort.by(userPage.getSortDirection(), userPage.getSortBy());
        Pageable pageable = PageRequest.of(userPage.getPageNumber(), userPage.getPageSize(), sort);
        Page <User> users = userRepository.findAll(pageable);
        List<UserDto> usersDtoList = new ArrayList<>();
        for(User user: users){
            UserDto userDto = mapper.map(user, UserDto.class);
            usersDtoList.add(userDto);
        }
        return new PageImpl<>(usersDtoList);
    }

    @Override
    public ResponseEntity<JwtResponse> generateLoginToken(String email) throws IOException{
        User user = userRepository.findUserByEmail(email).orElseThrow(()->
                new UserWithEmailNotFound("User not found"));
        if (user.getUserType().equals(UserType.ADMIN) || user.getUserType().equals(UserType.SUPER_ADMIN)) {
            String otp = new DecimalFormat("000000").format(this.rand.nextInt(999999));
            sendLoginOTP(email, otp);
            user.setLoginToken(otp);
            userRepository.save(user);
            return new ResponseEntity<>(new JwtResponse(null,"Check your email for login token"), HttpStatus.OK);
        } else throw new UnauthorizedException("Unauthorized credentials");
    }

    private void sendLoginOTP(String email, String otp) throws IOException {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setBody("Thank you for signing in to Wakacast, here is your login token \n" + otp);
        emailRequest.setSubject("Login token");
        emailRequest.setTo(email);
        emailService.sendEmail(emailRequest);
    }

    @Override
    public String confirmLoginToken(String token) {
        User user = userRepository.findUserByLoginToken(token).orElseThrow(()->
                new BadCredentialsException("Invalid token"));
        user.setLoginToken(null);
        userRepository.save(user);
        return user.getEmail();
    }

    @Override
    public String addGenre(GenreDto genreDto) {
        if(genreRepository.findGenreByGenreTitle(genreDto.getGenreTitle().toUpperCase()).isPresent()){
            throw new BadRequestException("Genre already exist");
        }else {
            Genre newGenre = mapper.map(genreDto, Genre.class);
            newGenre.setGenreTitle(genreDto.getGenreTitle().toUpperCase());
            genreRepository.save(newGenre);
        }
        return "New genre added successfully";
    }

    @Override
    public String deleteCastCallAdmin(long castCallId) {
        try {
            CastCall castCallToDelete = castCallRepository.findCastCallById(castCallId);
            castCallRepository.delete(castCallToDelete);
        } catch (Exception e) {
            throw new ResourceNotFound("cast call not found " + e.getMessage());
        }
        return "cast call deleted successfully";
    }

    @Override
    public Page<CastCallDtoAdmin> getAllCastCalls(CastCallPage castCallPage) {
        Sort sort = Sort.by(castCallPage.getSortDirection(), castCallPage.getSortBy());
        Pageable pageable = PageRequest.of(castCallPage.getPageNumber(), castCallPage.getPageSize(), sort);
        Page<CastCall> castCalls = castCallRepository.findAll(pageable);
        List<CastCallDtoAdmin> castCallDtoS = new ArrayList<>();

        for(CastCall calls: castCalls){
            CastCallDtoAdmin castCallDTOS = mapper.map(calls, CastCallDtoAdmin.class);
            castCallDtoS.add(castCallDTOS);
        }
        return new PageImpl<>(castCallDtoS);

    }

    @Override
    public String sendDailyEmails(DailyEmailDTO emailDTO) throws IOException {
        List<User> usersToReceiveEmail = userSearchCriteriaRepository.findUsers(emailDTO);
        for (User user : usersToReceiveEmail) {
            EmailRequest emailRequest = new EmailRequest();
            emailRequest.setTo(user.getEmail());
            emailRequest.setSubject(emailDTO.getEmailSubject());
            emailRequest.setBody(emailDTO.getEmailContent());
            emailService.sendEmail(emailRequest);
        }
        return "Email successfully sent";
    }

    @Override
    public String addLanguage(LanguageDto languageDto) {
        Language language = new Language();
        Optional<Language> existingLanguage = languageRepository.findLanguageByLanguageName(languageDto.getLanguage()
                .toUpperCase());
        if (existingLanguage.isPresent()) {
            throw new BadRequestException("Language already exist");
        } else {
            language.setLanguageName(languageDto.getLanguage().toUpperCase());
            languageRepository.save(language);
        }
        return "New Language added successfully";
    }

    @Override
    public String addReasonForReportingCastCall(ReasonToSaveDto reasonToSaveDto) {
        ReasonForReportingCastCall reason = new ReasonForReportingCastCall();
        Optional<ReasonForReportingCastCall> reasonsSaved = reasonForReportingCastCallRepository
                .findReasonForReportingCastCallByContent(reasonToSaveDto.getContent());
        if(reasonsSaved.isPresent()) {
            throw new BadRequestException("Reason already saved!");
        } else {
            reason.setContent(reasonToSaveDto.getContent().toUpperCase());
            reasonForReportingCastCallRepository.save(reason);
        }
        return "New reason saved successfully";
    }

    @Override
    public Page<ReportCastCallDto> getAllCastCallReportedFormatted (Long reportedCastCallId, CastCallReportPage castCallReportPage) {
        Sort sort = Sort.by(castCallReportPage.getSortDirection(), castCallReportPage.getSortBy());
        Pageable pageable = PageRequest.of(castCallReportPage.getPageNumber(), castCallReportPage.getPageSize(), sort);
        Page<ReportedCastCall> reportedCastCalls = reportCastCallRepository.findReportedCastCallsByCastCallReportedId(reportedCastCallId, pageable);
        List<ReportCastCallDto> reportFormattedList = new ArrayList<>();
        String otherReason;

        for (ReportedCastCall report : reportedCastCalls) {
            if(report.getOtherReasons() == null){
                otherReason = "";
            } else {
                otherReason = report.getOtherReasons().toUpperCase();
            }
            ReportCastCallDto eachReport = ReportCastCallDto.builder()
                    .id(report.getId())
                    .createDate(report.getCreateDate())
                    .reasonForReporting(report.getReason())
                    .projectName(report.getCastCallReported().getProjectName())
                    .projectType(report.getCastCallReported().getProjectType())
                    .reporterName(report.getCastCallReporter().getFirstName() + " "
                            + report.getCastCallReporter().getMiddleName() + " "
                            + report.getCastCallReporter().getSurname())
                    .otherReasonForReporting(otherReason)
                    .build();
            reportFormattedList.add(eachReport);
        }
        return new PageImpl<>(reportFormattedList);
    }


    @Override
    public String flagCastCallReported(long reportedCastCallId) throws IOException {
        ReportedCastCall reportedCastCall = reportCastCallRepository.findReportedCastCallById(reportedCastCallId);
        if(reportedCastCall == null) {
            throw new BadRequestException("Cast call with id: " +reportedCastCallId+" has not been reported");
        }

        CastCall castCallReported = reportedCastCall.getCastCallReported();

        if(castCallReported.isReportedEnough()) {
            throw new BadRequestException("Cast call flagged already");
        }
        castCallReported.setReportedEnough(true);
        castCallRepository.save(castCallReported);

        String castCallContact = castCallReported.getPublisher().getEmail();
        EmailRequest flagMailNotification = new EmailRequest();
        flagMailNotification.setTo(castCallContact);
        flagMailNotification.setSubject("Cast Call flag Notification");
        flagMailNotification.setBody("Your cast call with the following information has been flagged" +
                "and you will need to contact the wakacast admin to rectify any issue it is having" +
                "cast call within 7 days from the day you are notified to avoid complete delete of the cast call"
                );
        emailService.sendEmail(flagMailNotification);

        return "Cast Call flagged Successfully";
    }

    @Override
    public String unFlagCastCallReported(long reportedCastCallId) throws IOException {
        ReportedCastCall reportedCastCall = reportCastCallRepository.findReportedCastCallById(reportedCastCallId);
        if(reportedCastCall == null) {
            throw new BadRequestException("Cast call with id: " +reportedCastCallId+" has not been reported");
        }
        CastCall castCallReported = reportedCastCall.getCastCallReported();
        if(!castCallReported.isReportedEnough()) {
            throw new BadRequestException("Cast call un-flagged already");
        }
        castCallReported.setReportedEnough(false);
        castCallRepository.save(castCallReported);
        String castCallContact = castCallReported.getPublisher().getEmail();
        EmailRequest unFlagMailNotification = new EmailRequest();
        unFlagMailNotification.setTo(castCallContact);
        unFlagMailNotification.setSubject("Cast Call flag Notification");
        unFlagMailNotification.setBody("Haven't adequately reported to the admin and cleared issues that pertain to your cast call, " +
                "your cast call with the following information has been un-flagged"
        );
        emailService.sendEmail(unFlagMailNotification);

        return "Cast Call un-flagged Successfully";
    }


    @Override
    public String addRole(RoleDto roleDto) {
        if(roleRepository.findRoleByRoleTitle(roleDto.getRoleTitle().toUpperCase()).isPresent()){
            throw new BadRequestException("Role already exist");
        }else {
            Role role = mapper.map(roleDto, Role.class);
            role.setRoleTitle(roleDto.getRoleTitle().toUpperCase());
            roleRepository.save(role);
        }
        return "Role added Successfully";
    }

    @Override
    public String createNewAdmin(AdminDto adminDto) throws IOException {
        Optional<User> existingAdmin = userRepository.findUserByEmail(adminDto.getEmail());
        if (existingAdmin.isPresent()) {
            throw new BadRequestException("Admin already exist");
        } else {
            String password = adminDto.getPassword();
            adminDto.setPassword(passwordEncoder.encode(password));
            User user = mapper.map(adminDto, User.class);
            user.setUserType(UserType.ADMIN);
            userRepository.save(user);
            String content = "Thank you for signing up to WakaCast, \n" +
                    "Below are your login credentials: \n" +
                    "email : " + user.getEmail() + "\n" +
                    "password : " + password + "\n" +
                    "please click on the link below to activate your account : \n" +
                    Constants.BASE_URL + "account-verification/";
            userServices.sendVerificationEmail(user, content);
        }
        return "Admin Successfully Created";
    }

    @Override
    public String activateDeactivateUser(long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty())
            throw new ResourceNotFound("User not found");
        else {
            User user = optionalUser.get();
            user.setActive(!user.isActive());
            userRepository.save(user);
            if(user.isActive())
                return "User Activated Successfully";
        }
        return "User Deactivated Successfully";
    }

    @Override
    public String addEquipmentType(EquipmentTypeDto equipmentTypeDto) {
        if(equipmentTypeRepository.findEquipmentByEquipmentType(equipmentTypeDto.getEquipmentType().toUpperCase()).isPresent()){
            throw new BadRequestException("Equipment type already exist");
        }else {
            EquipmentType equipmentType = mapper.map(equipmentTypeDto, EquipmentType.class);
            equipmentType.setEquipmentType(equipmentTypeDto.getEquipmentType().toUpperCase());
            equipmentTypeRepository.save(equipmentType);
        }
        return "Equipment Type saved Successfully";
    }
}
