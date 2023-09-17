package com.wakacast.services.service_impl;

import com.wakacast.dto.CastCallDto;
import com.wakacast.dto.ReasonForReportingCastCallDto;
import com.wakacast.dto.pages_criteria.ApplicantPage;
import com.wakacast.dto.pages_criteria.CastCallPage;
import com.wakacast.dto.response_dtos.UserResponseDto;
import com.wakacast.enums.UserType;
import com.wakacast.exceptions.BadRequestException;
import com.wakacast.exceptions.ResourceNotFound;
import com.wakacast.exceptions.UnauthorizedException;
import com.wakacast.exceptions.UserWithEmailNotFound;
import com.wakacast.models.CastCall;
import com.wakacast.models.ReasonForReportingCastCall;
import com.wakacast.models.ReportedCastCall;
import com.wakacast.models.User;
import com.wakacast.repositories.CastCallRepository;
import com.wakacast.repositories.ReasonForReportingCastCallRepository;
import com.wakacast.repositories.ReportCastCallRepository;
import com.wakacast.repositories.UserRepository;
import com.wakacast.requests.EmailRequest;
import com.wakacast.services.CastCallService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.wakacast.global_constants.Constants.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CastCallServiceImpl implements CastCallService {
    private final UserRepository userRepository;
    private final CastCallRepository castCallRepository;
    private final ModelMapper mapper;
    private final EmailService emailService;
    private final ReportCastCallRepository reportCastCallRepository;
    private final ReasonForReportingCastCallRepository reasonForReportingCastCallRepository;


    @Override
    public String postCastCall(CastCallDto castCallDto) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String email = userDetails.getUsername();
        User user = userRepository.findUserByEmail(email).orElseThrow(() ->
                new UserWithEmailNotFound(USER_NOT_FOUND));
        List<CastCall> castCalls = castCallRepository.findCastCallByPublisherId(user.getId());

        boolean status;
        status = isUserAuthorizedToPostCastCall(user, castCalls);
        if(!status) throw new UnauthorizedException("User not authorized to access this feature");

        CastCall newCastCall = mapper.map(castCallDto, CastCall.class);
        newCastCall.setPublisher(user);
        newCastCall.setReportedEnough(false);
        castCallRepository.save(newCastCall);
        return "cast call posted successfully";
    }

    private boolean isUserAuthorizedToPostCastCall(User user, List<CastCall> castCalls) {
        boolean status = false;
        if (user.getUserPersonas().stream()
                .anyMatch(persona -> persona.getPersona().equalsIgnoreCase("Producer"))) {
            if (user.getUserType().equals(UserType.SUBSCRIBED) || (castCalls == null || castCalls.isEmpty()))
                status = true;
            else {
                int count = 0;
                if (castCalls.stream().anyMatch(castCall -> castCall.getPostExpiryDate().isAfter(LocalDate.now())))
                    count++;
                if (count == 0) status = true;
            }
        }
        return status;
    }

    @Override
    @CachePut(cacheNames = "cast-call", key = "#castCallId")
    public String editCastCall(CastCallDto castCallDto, long castCallId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String email = userDetails.getUsername();
        CastCall existingCastCall = castCallRepository
                .findCastCallByPublisherEmailAndId(email, castCallId);
        User user = existingCastCall.getPublisher();
        if (user.getUserType().equals(UserType.UNSUBSCRIBED)) {
            List<CastCall> castCalls = castCallRepository.findCastCallByPublisherId(user.getId());
            boolean flag = castCalls.stream()
                    .filter(castCall -> castCall.getPostExpiryDate().isAfter(LocalDate.now()))
                    .findFirst().filter(castCall -> castCall.equals(existingCastCall))
                    .isPresent();
            if (!flag) throw new BadRequestException("You already have an active cast");
        }
        mapper.map(castCallDto, existingCastCall);
        castCallRepository.save(existingCastCall);
        return "cast call edited successfully";
    }

    @Override
    @CacheEvict(cacheNames = "cast-call", key = "#castCallId")
    public String deleteCastCall(long castCallId) {
        try{
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            String email = userDetails.getUsername();
            CastCall existingCastCall = castCallRepository.findCastCallByPublisherEmailAndId(email, castCallId);
            castCallRepository.delete(existingCastCall);
        } catch (Exception ex) {
            throw new ResourceNotFound("cast call not found");
        }
        return "cast call deleted successfully";
    }


    @Override
    @Cacheable(cacheNames = "cast-call")
    public Page<CastCall> getAllActiveCastCalls(CastCallPage castCallPage) {
        Sort sort = Sort.by(castCallPage.getSortDirection(), castCallPage.getSortBy());
        Pageable pageable = PageRequest.of(castCallPage.getPageNumber(), castCallPage.getPageSize(), sort);
        Page<CastCall> castCalls = castCallRepository.findCastCallsByPostExpiryDateBefore(pageable);
        return castCalls;
    }

    private Page<CastCallDto> mapCastCastToCastCallDTO(Page<CastCall> castCalls) {
        List<CastCallDto> castCallDtoList =  new ArrayList<>();
        for(CastCall castCall : castCalls){
            CastCallDto castCallDto = mapper.map(castCall, CastCallDto.class);
            castCallDtoList.add(castCallDto);
        }

        return new PageImpl<>(castCallDtoList);
    }

    @Override
    public String applyForCastCall(long castCallId) throws IOException {
        String msg;
        String email = UserServicesImpl.getAuthenticatedUserEmail();
        User user = userRepository.findUserByEmail(email).orElseThrow(() ->
                new UserWithEmailNotFound(USER_NOT_FOUND));

        boolean status = false;
        status = isUserAuthorizedToApply(user);
        if (!status) {
            throw new UnauthorizedException("User not authorized to access this feature");
        } else {
            msg = getCastCallApplyingFor(castCallId, user);
        }
        return msg;
    }

    private String getCastCallApplyingFor(long castCallId, User user) throws IOException {
        String msg;
        CastCall castCallApplyingFor = castCallRepository.findCastCallById(castCallId);
        if(castCallApplyingFor != null){
            Set<User> applicants;

            if (castCallApplyingFor.getApplicants() == null || castCallApplyingFor.getApplicants().isEmpty()) {
                applicants = new HashSet<>();
            } else {
            applicants = castCallApplyingFor.getApplicants();
            }
            msg = apply(user, castCallApplyingFor, applicants);
        } else {
            throw new ResourceNotFound("Cast call with id "+ castCallId + " does not exist");
        }
        return msg;
    }

    private String apply(User user, CastCall castCallApplyingFor, Set<User> applicants) throws IOException {
        String msg;
        if(!applicants.contains(user)){
                applicants.add(user);
                castCallApplyingFor.setApplicants(applicants);

                msg = "Your application was received successfully";
                castCallRepository.save(castCallApplyingFor);

                EmailRequest applicationMailNotification = new EmailRequest();
                applicationMailNotification.setTo(user.getEmail());
                applicationMailNotification.setSubject("Successfully applied");
                applicationMailNotification.setBody("Thanks for your interest in the role of "
                        + castCallApplyingFor.getTalentSkill() + " of "
                        + castCallApplyingFor.getProjectName()
                        + " Your application was successfully received and is under review. " +
                        "The Producer will reach out to you " +
                        "if successfully chosen for the next stage");
                emailService.sendEmail(applicationMailNotification);
        } else throw new BadRequestException("User already applied");
        return msg;
    }

    private boolean isUserAuthorizedToApply(User user) {
        boolean status = false;
        if (user.getUserPersonas().stream().anyMatch(persona -> persona.getPersona().equalsIgnoreCase("Talent"))) {
            if (user.getUserType().equals(UserType.SUBSCRIBED)) status = true;
            else throw new UnauthorizedException("You are not permitted to use this resource");
        }
        return status;
    }

    @Override
    @Cacheable(cacheNames = "cast-call")
    public Page<CastCallDto> getAllCastCallsByAProducer(CastCallPage castCallPage) {
        String email = UserServicesImpl.getAuthenticatedUserEmail();
        Sort sort = Sort.by(castCallPage.getSortDirection(), castCallPage.getSortBy());
        Pageable pageable = PageRequest.of(castCallPage.getPageNumber(), castCallPage.getPageSize(), sort);
        Page<CastCall> castCallList = castCallRepository.findCastCallsByPublisherEmail(email, pageable);
        return mapCastCastToCastCallDTO(castCallList);
    }

    @Override
    public String reportCastCall(long castCallId, ReasonForReportingCastCallDto reasonForReportingCastCallDto) throws IOException {
        String msg;
        String email = UserServicesImpl.getAuthenticatedUserEmail();
        User user = userRepository.findUserByEmail(email).orElseThrow(() ->
                new UserWithEmailNotFound(USER_NOT_FOUND));
        CastCall castCallToReport = castCallRepository.findCastCallById(castCallId);

        Optional<ReportedCastCall> castCallReportedAlready = reportCastCallRepository.findByCastCallReportedAndCastCallReporter(castCallToReport, user);

        if(castCallReportedAlready.isPresent()) {
            throw new BadRequestException("You have reported this cast call already ");
        }

        ReportedCastCall reportedCastCall = new ReportedCastCall();
        Optional<ReasonForReportingCastCall> optionalReason = reasonForReportingCastCallRepository
                .findReasonForReportingCastCallByContent(reasonForReportingCastCallDto.getContent().toUpperCase());
        if (optionalReason.isPresent()) {
            ReasonForReportingCastCall reason = optionalReason.get();
            reportedCastCall.setReason(reason.getContent());
        } else {
            reportedCastCall.setOtherReasons(reasonForReportingCastCallDto.getOtherReasons().toUpperCase());
        }

        reportedCastCall.setCastCallReported(castCallToReport);
        reportedCastCall.setCastCallReporter(user);
        reportCastCallRepository.save(reportedCastCall);
        castCallToReport.setReportedCount(castCallToReport.getReportedCount() + 1);
        castCallRepository.save(castCallToReport);
        sendCastCallReportNotificationToAdmins(castCallToReport);
        msg = "Cast call reported successfully";
        return msg;
    }

    @Override
    public CastCallDto getCastCallById(Long id) {
        CastCall returnedCastCall = castCallRepository.findCastCallById(id);
        return mapper.map(returnedCastCall, CastCallDto.class);
    }

    @Override
    public Page<UserResponseDto> getCastCallApplicants(long castCallId, ApplicantPage applicantPage) {
        CastCall castCallById = castCallRepository.findCastCallById(castCallId);
        List<UserResponseDto> userResponseDtos = castCallById.getApplicants().stream()
                .sorted(Comparator.comparing(User::getFirstName)).map(user -> mapper.map(user, UserResponseDto.class))
                .collect(Collectors.toList());

        Pageable pageable = PageRequest.of(applicantPage.getPageNumber(), applicantPage.getPageSize());
        int size = userResponseDtos.size();
        int start = Math.min((int)pageable.getOffset(), size);
        int end = Math.min((start + applicantPage.getPageSize()), size);
        userResponseDtos = userResponseDtos.subList(start, end);

        return new PageImpl<>(userResponseDtos, pageable, size);
    }

    private void sendCastCallReportNotificationToAdmins(CastCall castCallToFlag) throws IOException {
        if (castCallToFlag.getReportedCount() == 10 || castCallToFlag.getReportedCount() == 15) {
            List<User> admins = userRepository.findUserByUserType(UserType.ADMIN);
            for (User admin : admins) {
                EmailRequest adminCastCallReportedAlert = new EmailRequest();
                adminCastCallReportedAlert.setTo(admin.getEmail());
                adminCastCallReportedAlert.setSubject("Review required on a cast call");
                adminCastCallReportedAlert.setBody("The cast call with the below credentials needs your urgent attention. " +
                        "It has received the maximum flag count to be considered for further actions." +
                        "Cast Call ID: " + castCallToFlag.getId() +
                        "Cast call Publisher: " + castCallToFlag.getPublisher() +
                        "Cast call posted date: " + castCallToFlag.getCreateDate() +
                        "Cast call Number of Count: " + castCallToFlag.getReportedCount());
                emailService.sendEmail(adminCastCallReportedAlert);
            }
        }
    }
}