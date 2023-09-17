package com.wakacast.services.service_impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.wakacast.configurations.security_configs.JwtTokenUtil;
import com.wakacast.dto.*;
import com.wakacast.dto.search_criteria.RoleMatchSearchCriteria;
import com.wakacast.enums.PortfolioType;
import com.wakacast.enums.UserType;
import com.wakacast.event.OnUserLogoutSuccessEvent;
import com.wakacast.exceptions.BadRequestException;
import com.wakacast.exceptions.ResourceNotFound;
import com.wakacast.exceptions.UnauthorizedException;
import com.wakacast.exceptions.UserWithEmailNotFound;
import com.wakacast.global_constants.Constants;
import com.wakacast.models.*;
import com.wakacast.repositories.*;
import com.wakacast.requests.EmailRequest;
import com.wakacast.services.UserServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.management.relation.RoleNotFoundException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.wakacast.enums.UserType.SUPER_ADMIN;
import static com.wakacast.enums.UserType.UNSUBSCRIBED;
import static com.wakacast.global_constants.Constants.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServicesImpl implements UserServices {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final ModelMapper mapper;
    private final GenreRepository genreRepository;
    private final RoleRepository roleRepository;
    private final SpokenLanguageRepository languageRepository;
    private final Cloudinary cloudinary;
    private final PortfolioRepository portfolioRepository;
    private final UserPersonaRepository userPersonaRepository;
    private final EquipmentRepository equipmentRepository;
    private final EquipmentImageRepository equipmentImageRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final SearchAgentRepository searchAgentRepository;
    private static final String EQUIPMENT_NOT_FOUND = "Equipment/facility not found";

    @Override
    public String signup(SignUpDto signUpDto) throws IOException {
        log.info("Initializing signup with request" + signUpDto);
        Optional<User> optionalUser = userRepository.findUserByEmail(signUpDto.getEmail());
        if (optionalUser.isEmpty()) {
            signUpDto.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
            signUpDto.setUserPersona(signUpDto.getUserPersona().toUpperCase());
            signUpDto.setEmail(signUpDto.getEmail().toLowerCase());
            Optional<UserPersona> optionalUserPersona = userPersonaRepository.findUserPersonaByPersona(signUpDto.getUserPersona());
            UserPersona userPersona;
            if (optionalUserPersona.isEmpty()) {
                userPersona = new UserPersona();
                userPersona.setPersona(signUpDto.getUserPersona());
                userPersonaRepository.save(userPersona);
            } else userPersona = optionalUserPersona.get();

            Set<UserPersona> userPersonas = new HashSet<>();
            userPersonas.add(userPersona);
            User user = mapper.map(signUpDto, User.class);
            user.setUserType(UNSUBSCRIBED);
            user.setUserPersonas(userPersonas);
            userRepository.save(user);
            String content = "Thank you for signing up to WakaCast, " +
                    "please click on the link below to activate your account : \n" +
                    Constants.BASE_URL + "account-verification/";
            sendVerificationEmail(user, content);
        } else {
            log.info("Email: " + signUpDto.getEmail() + " already exist");
            throw new BadRequestException("Email: " + signUpDto.getEmail() + " already exist");
        }
        log.info("Registration successful");
        return "Registration successful, check your email for verification";
    }

    protected void sendVerificationEmail(User user, String content) throws IOException {
        UserDetails userDetails = new MyUserDetails(user);
        String token = jwtTokenUtil.generateToken(userDetails);
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTo(user.getEmail());
        emailRequest.setSubject("Account Verification");
        emailRequest.setBody(content + token);
        emailService.sendEmail(emailRequest);
    }

    @Override
    public void verifyAccount(String token) {
        String email = jwtTokenUtil.getUserEmailFromToken(token);
        User user = userRepository.findUserByEmail(email).orElseThrow(()->
                new UserWithEmailNotFound(USER_NOT_FOUND));
        user.setAccountVerified(true);
        userRepository.save(user);
    }

    @Override
    public UserDto getLoggedInUser() {
        String email = getAuthenticatedUserEmail();
        User user = userRepository.findUserByEmail(email).orElseThrow(()->
                new UserWithEmailNotFound(USER_NOT_FOUND));
        return mapper.map(user, UserDto.class);
    }

    @Override
    public String setUpProfile(ProfileSetupDto setupDto) {
        String email = getAuthenticatedUserEmail();
        User updatedUser = userRepository.findUserByEmail(email).orElseThrow(()->
                new UserWithEmailNotFound(USER_NOT_FOUND));
            setPersona(setupDto, updatedUser);
            setRole(setupDto, updatedUser);
            setGenre(setupDto, updatedUser);
            setSpokenLanguage(setupDto, updatedUser);
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            modelMapper.map(setupDto, updatedUser);
            userRepository.save(updatedUser);
        return "Profile successfully updated";
    }

    private void setPersona(ProfileSetupDto setupDto, User updatedUser) {
        for (String persona : setupDto.getUserPersonas()) {
            Optional<UserPersona> optionalUserPersona = userPersonaRepository.findUserPersonaByPersona(persona.toUpperCase());
            UserPersona userPersona;
            if (optionalUserPersona.isEmpty()) {
                userPersona = new UserPersona();
                userPersona.setPersona(persona.toUpperCase());
                userPersonaRepository.save(userPersona);
            } else {
                userPersona = optionalUserPersona.get();
            }
            Set<UserPersona> userPersonas = updatedUser.getUserPersonas();
            userPersonas.add(userPersona);
            updatedUser.setUserPersonas(userPersonas);
        }
    }

    private void setSpokenLanguage(ProfileSetupDto setupDto, User updatedUser) {
        for (SpokenLanguageDto languageDto : setupDto.getSpokenLanguages()) {
            SpokenLanguage spokenLanguage = mapper.map(languageDto, SpokenLanguage.class);
            languageRepository.save(spokenLanguage);
            Set<SpokenLanguage> spokenLanguages;
            if (updatedUser.getSpokenLanguages() == null || updatedUser.getSpokenLanguages().isEmpty() ) {
                spokenLanguages = new HashSet<>();
            } else {
                spokenLanguages = updatedUser.getSpokenLanguages();
            }
            spokenLanguages.add(spokenLanguage);
            updatedUser.setSpokenLanguages(spokenLanguages);
        }
    }

    private void setGenre(ProfileSetupDto setupDto, User updatedUser) {
        for (String genreTitle: setupDto.getGenres()) {
            Optional<Genre> optionalGenre = genreRepository.findGenreByGenreTitle(genreTitle);
            Genre genre;
            if (optionalGenre.isPresent()) {
                genre = optionalGenre.get();
            } else {
                genre = new Genre();
                genre.setGenreTitle(genreTitle.toUpperCase());
                genreRepository.save(genre);
            }
            Set<Genre> genres;
            if (updatedUser.getGenres() == null || updatedUser.getGenres().isEmpty()) {
                genres = new HashSet<>();
            } else {
                genres = updatedUser.getGenres();
            }
            genres.add(genre);
            updatedUser.setGenres(genres);
        }
    }

    private void setRole(ProfileSetupDto setupDto, User updatedUser) {
        for (String roleTitle: setupDto.getRoles()) {
            Optional<Role> optionalRole = roleRepository.findRoleByRoleTitle(roleTitle.toUpperCase());
            Role role;
            if (optionalRole.isPresent()) {
                role = optionalRole.get();
            } else {
                role = new Role();
                role.setRoleTitle(roleTitle.toUpperCase());
                roleRepository.save(role);
            }
            Set<Role> roles;
            if (updatedUser.getRoles() == null || updatedUser.getRoles().isEmpty()) {
                roles = new HashSet<>();
            } else {
                roles = updatedUser.getRoles();
            }
            roles.add(role);
            updatedUser.setRoles(roles);
        }
    }

    @Override
    public String uploadProfilePic(MultipartFile image) throws IOException {
        String email = getAuthenticatedUserEmail();
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String pictureUrl = uploadPicture(image);
            user.setProfilePictureUrl(pictureUrl);
            userRepository.save(user);
            return "Profile picture uploaded successfully";
        } else throw new ResourceNotFound(USER_NOT_FOUND);
    }

    @Override
    public String uploadPortfolio(MultipartFile portfolioFile, PortfolioDto portfolioDto) throws IOException {
        String email = getAuthenticatedUserEmail();
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        if (optionalUser.isPresent() && optionalUser.get().getUserType().equals(UserType.SUBSCRIBED)) {
            User user = optionalUser.get();
            Set<Portfolio> portfolios = user.getPortfolios();
            if (portfolios == null || portfolios.isEmpty())
                portfolios = new HashSet<>();
            portfolioUpload(portfolioFile, portfolioDto, portfolios, user);
        } else
            throw new UnauthorizedException("Kindly subscribe to upload portfolio");
        return "Portfolio uploaded successfully";
    }

    @Override
    public String postEquipmentForLease(EquipmentRequestedDto equipmentRequestedDto) {
        String email = getAuthenticatedUserEmail();
        User user = userRepository.findUserByEmail(email).orElseThrow(()->
                new UserWithEmailNotFound(USER_NOT_FOUND));
        Equipment newEquipment = mapper.map(equipmentRequestedDto, Equipment.class);

        newEquipment.setEquipmentName(equipmentRequestedDto.getEquipmentName().toUpperCase());
        newEquipment.setState(equipmentRequestedDto.getState().toUpperCase());
        newEquipment.setCity(equipmentRequestedDto.getCity().toUpperCase());
        newEquipment.setEquipmentOwner(user);

        equipmentRepository.save(newEquipment);
        return "Equipment listed successfully";
    }

    @Override
    public String uploadEquipmentImage(long equipmentId, MultipartFile image) throws IOException {
        String email = getAuthenticatedUserEmail();
        Equipment equipment = equipmentRepository.findEquipmentByIdAndEquipmentOwnerEmail(equipmentId, email).orElseThrow(()->
                new ResourceNotFound(EQUIPMENT_NOT_FOUND));
        String equipmentPictureUrl = uploadPicture(image);
        EquipmentImage equipmentImage = new EquipmentImage(equipmentPictureUrl);
        equipmentImageRepository.save(equipmentImage);
        Set<EquipmentImage> equipmentImages = equipment.getEquipmentImages();
        if (equipmentImages == null || equipmentImages.isEmpty())
            equipmentImages = new HashSet<>();
        equipmentImages.add(equipmentImage);
        equipment.setEquipmentImages(equipmentImages);
        equipmentRepository.save(equipment);
        return "Image uploaded successfully";
    }

    @Override
    public String editPostedEquipment(long equipmentId, String equipmentName) {
        String email = getAuthenticatedUserEmail();
        Equipment equipment = equipmentRepository.findEquipmentByIdAndEquipmentOwnerEmail(equipmentId, email).orElseThrow(()->
                new ResourceNotFound(EQUIPMENT_NOT_FOUND));
        equipment.setEquipmentName(equipmentName.toUpperCase());
        equipmentRepository.save(equipment);
        return "Equipment updated successfully";
    }

    protected static String getAuthenticatedUserEmail() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return userDetails.getUsername();
    }

    @Override
    public String deletePostedEquipment(long equipmentId) {
        String email = getAuthenticatedUserEmail();
        Equipment equipment = equipmentRepository.findEquipmentByIdAndEquipmentOwnerEmail(equipmentId, email).orElseThrow(()->
                new ResourceNotFound(EQUIPMENT_NOT_FOUND));
        equipmentRepository.delete(equipment);
        return "Equipment/facility deleted successfully";
    }

    @Override
    public String deleteUserAccount() {
        String email = getAuthenticatedUserEmail();
        User existingUser = userRepository.findUserByEmail(email).orElseThrow(() ->
                new UserWithEmailNotFound(USER_NOT_FOUND));
        userRepository.delete(existingUser);
        return "User account deleted successfully";
    }

    @Override
    public String logout(LogOutRequestDto logOutRequest) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        String email = userDetails.getUsername();
        OnUserLogoutSuccessEvent logoutSuccessEvent = new OnUserLogoutSuccessEvent(email, logOutRequest.getToken(), logOutRequest);
        applicationEventPublisher.publishEvent(logoutSuccessEvent);
        return "User has successfully logged out from the system!";
    }

    @Override
    public String setUpSearchAgent(RoleMatchSearchCriteria roleMatchSearchCriteria) {
        String email = getAuthenticatedUserEmail();
        User user = userRepository.findUserByEmail(email).orElseThrow(() ->
                new UserWithEmailNotFound(USER_NOT_FOUND));
        boolean flag = false;
        for (UserPersona persona : user.getUserPersonas()) {
            if (persona.getPersona().equalsIgnoreCase("Talent")) {
                flag = true;
                break;
            }
        }
        if (!flag) throw new BadRequestException("Only a talent can set up search agent");
        SearchAgent newSearchAgent = mapper.map(roleMatchSearchCriteria, SearchAgent.class);
        newSearchAgent.setUser(user);
        searchAgentRepository.save(newSearchAgent);
        return "Search Agent set up successfully";
    }

    @Override
    public String editSearchAgent(long searchAgentId, RoleMatchSearchCriteria roleMatchSearchCriteria) {
        String email = getAuthenticatedUserEmail();
        SearchAgent searchAgent = searchAgentRepository.findSearchAgentByIdAndUserEmail(searchAgentId, email).orElseThrow(()->
                new ResourceNotFound("No search agent found")
        );
        mapper.map(roleMatchSearchCriteria, searchAgent);
        searchAgentRepository.save(searchAgent);
        return "Search Agent updated successfully";
    }

    @Override
    public String removeSearchAgent(long searchAgentId) {
        String email = getAuthenticatedUserEmail();
        SearchAgent searchAgent = searchAgentRepository.findSearchAgentByIdAndUserEmail(searchAgentId, email).orElseThrow(()->
                new ResourceNotFound("No search agent found")
        );
        searchAgentRepository.delete(searchAgent);
        return "Search Agent removed successfully";
    }

    @Override
    public String addSuperAdmin(SignUpDto signUpDto) throws IOException {
        Optional<User> optionalUser = userRepository.findUserByEmail(signUpDto.getEmail());
        if (optionalUser.isEmpty()) {
            User superAdmin = new User();
            superAdmin.setActive(true);
            superAdmin.setUserType(SUPER_ADMIN);
            superAdmin.setEmail(signUpDto.getEmail());
            superAdmin.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
            superAdmin.setAccountVerified(true);
            superAdmin.setFirstName("SUPER_ADMIN");

            userRepository.save(superAdmin);

            String content = "Welcome to WakaCast, " +
                    "please use the following details to sign in as a SUPER-ADMIN : \n"
                    +"username: " +signUpDto.getEmail() +"\n"
                    +"password: " +signUpDto.getPassword();

            EmailRequest emailRequest = new EmailRequest(signUpDto.getEmail(), "Login Details", content);
            emailService.sendEmail(emailRequest);

            return "Successfully added a Super_Admin";
        }else {
            return "Admin Exists";
        }
    }

    @Override
    @Transactional
    public String addRoleToUser(RoleUserDto roleUserDto) throws RoleNotFoundException {
        Optional<User> optionalUser = userRepository.findUserByEmail(roleUserDto.getUserEmail());
        Optional<Role> optionalRole = roleRepository.findRoleByRoleTitle(roleUserDto.getRoleTitle());
        if(optionalRole.isEmpty())
            throw new RoleNotFoundException();
        if(optionalUser.isEmpty())
            throw new ResourceNotFound("User not found");
        Role role = optionalRole.get();
        optionalUser.get().getRoles().add(role);
        return "Role " +roleUserDto.getRoleTitle() + " added successfully to user " +roleUserDto.getUserEmail();
    }

    @Override
    public UserDto getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty())
            throw new ResourceNotFound("User not found");
        return mapper.map(optionalUser.get(), UserDto.class);
    }


    private void portfolioUpload(MultipartFile portfolioFile, PortfolioDto portfolioDto, Set<Portfolio> portfolios, User user) throws IOException {
        Portfolio portfolio = mapper.map(portfolioDto, Portfolio.class);
        String url;
        if (portfolio.getPortfolioType().equals(PortfolioType.VIDEO) || portfolio.getPortfolioType().equals(PortfolioType.AUDIO)) {
            url = uploadAudioVideoPortfolio(portfolioFile);
        } else if (portfolio.getPortfolioType().equals(PortfolioType.PICTURE)) {
            url = uploadPicture(portfolioFile);
        }
        else throw new BadRequestException("Please select a portfolio type");
        portfolio.setPortfolioUrl(url);
        portfolioRepository.save(portfolio);
        portfolios.add(portfolio);
        user.setPortfolios(portfolios);
        userRepository.save(user);
    }

    public String uploadAudioVideoPortfolio(MultipartFile video) throws IOException {
        try {
            File uploadedFile = convertMultiPartToFile(video);
            Map uploadResult = cloudinary.uploader().upload(uploadedFile, ObjectUtils.asMap("resource_type", "video"));
            boolean isDeleted = uploadedFile.delete();

            if (isDeleted){
                log.info("File successfully deleted");
            }else
                log.info("File doesn't exist");
            return  uploadResult.get("url").toString();
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    public String uploadPicture(MultipartFile picture) throws IOException {
        try {
            File uploadedFile = convertMultiPartToFile(picture);
            Map uploadResult = cloudinary.uploader().upload(uploadedFile, ObjectUtils.asMap("use_filename", true, "unique_filename", true));
            boolean isDeleted = uploadedFile.delete();

            if (isDeleted){
                log.info("File successfully deleted");
            }else
                log.info("File doesn't exist");
            return  uploadResult.get("url").toString();
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    private File convertMultiPartToFile(MultipartFile image) throws IOException {
        String file =  image.getOriginalFilename();
        assert file != null;
        File convFile = new File(file);
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(image.getBytes());
        }
        return convFile;
    }
}
