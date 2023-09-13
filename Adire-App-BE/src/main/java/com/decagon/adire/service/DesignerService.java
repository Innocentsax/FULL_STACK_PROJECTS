package com.decagon.adire.service;

import com.decagon.adire.dto.request.*;
import com.decagon.adire.dto.request.DesignerDTO;
import com.decagon.adire.dto.response.DesignerResponseDTO;
import com.decagon.adire.dto.response.LoginResponse;
import com.decagon.adire.dto.response.UserResponseDto;
import com.decagon.adire.entity.Designer;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.GeneralSecurityException;


public interface DesignerService {
   Designer findByEmail(String email);
   LoginResponse login(LoginDTO request);
   DesignerResponseDTO updateDesignerDetials(UpdateDesigner updateUserDto);

   DesignerResponseDTO createUser(DesignerDTO designerDTO);

   UserResponseDto saveOAuth2User(Designer request);

   DesignerResponseDTO getDesigner(String Id);

   String updatePassword(UpdatePasswordDto updatePasswordDto);

   void deleteDesigner(String email);
   LoginResponse loginWithGoogle(Oauth2Request request) throws GeneralSecurityException, IOException;
   DesignerResponseDTO getDesignerProfile(HttpServletRequest request);


}
