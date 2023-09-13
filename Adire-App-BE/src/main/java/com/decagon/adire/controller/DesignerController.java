package com.decagon.adire.controller;

import com.decagon.adire.dto.request.UpdateDesigner;
import com.decagon.adire.dto.response.AppResponse;
import com.decagon.adire.dto.response.DesignerResponseDTO;
import com.decagon.adire.entity.Designer;
import com.decagon.adire.repository.DesignerRepository;
import com.decagon.adire.security.jwt.TokenProvider;
import com.decagon.adire.service.DesignerService;
import com.decagon.adire.utils.SecurityConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@RestController
@RequestMapping("/api/designer")
@RequiredArgsConstructor
public class DesignerController {
    private final DesignerRepository designerRepository;
    private final DesignerService designerService;
    private ModelMapper modelMapper = new ModelMapper();


    @GetMapping(path = "/getDesigner/{id}")
    public ResponseEntity<DesignerResponseDTO> getDesignerById(@PathVariable("id") String id){
        DesignerResponseDTO viewDesigner = designerService.getDesigner(id);
        return ResponseEntity.status(HttpStatus.OK).body(viewDesigner);
    }


    @PutMapping(path = "/updateDesigner")
    public ResponseEntity<Object> updateDesigner(@RequestBody UpdateDesigner request){
        DesignerResponseDTO updateDesigner = designerService.updateDesignerDetials(request);
        return ResponseEntity.ok().body(AppResponse.buildSuccessTxn(updateDesigner));
    }

    @GetMapping("/getDesignerInfo")
    public ResponseEntity<Object> getUserInfo(HttpServletRequest request) {
        DesignerResponseDTO userResponse = designerService.getDesignerProfile(request);
        return ResponseEntity.ok().body(userResponse);
    }

    @DeleteMapping(path = "/deleteDesigner/")
    public ResponseEntity<?> deleteDesigner(HttpServletRequest request){
        ResponseEntity<Object> responseEntity = getUserInfo(request);
        DesignerResponseDTO designerResponseDTO = (DesignerResponseDTO) responseEntity.getBody();
        designerService.deleteDesigner(designerResponseDTO.getEmail());
        return ResponseEntity.ok().body(AppResponse.buildSuccessTxn("Designer deleted successfully"));
    }




    }
