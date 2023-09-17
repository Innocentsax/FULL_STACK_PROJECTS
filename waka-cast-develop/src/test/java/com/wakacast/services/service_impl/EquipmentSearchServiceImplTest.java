package com.wakacast.services.service_impl;

import com.wakacast.dto.EquipmentResponseDto;
import com.wakacast.dto.EquipmentTypeDto;
import com.wakacast.dto.EquipmentTypeResponseDto;
import com.wakacast.dto.pages_criteria.EquipmentPage;
import com.wakacast.dto.search_criteria.EquipmentSearchCriteria;
import com.wakacast.models.Equipment;
import com.wakacast.models.EquipmentType;
import com.wakacast.repositories.EquipmentRepository;
import com.wakacast.repositories.EquipmentTypeRepository;
import com.wakacast.repositories.criteri_class.EquipmentSearchCriteriaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EquipmentSearchServiceImplTest {

    @Mock
    private EquipmentSearchCriteriaRepository equipmentSearchCriteriaRepository;
    @Mock
    private EquipmentRepository equipmentRepository;
    @Mock
    private ModelMapper mapper;
    @InjectMocks
    private EquipmentSearchServiceImpl equipmentSearchService;
    @Mock
    private EquipmentTypeRepository equipmentTypeRepository;



    private Page<Equipment> page;

    @BeforeEach
    void setUp() {
        EquipmentPage equipmentPage = new EquipmentPage();
        page = new Page<>() {

            @Override
            public Iterator<Equipment> iterator() {
                return null;
            }

            @Override
            public int getTotalPages() {
                return 0;
            }

            @Override
            public long getTotalElements() {
                return 0;
            }

            @Override
            public int getNumber() {
                return 0;
            }

            @Override
            public int getSize() {
                return 0;
            }

            @Override
            public int getNumberOfElements() {
                return 0;
            }

            @Override
            public List<Equipment> getContent() {
                return null;
            }

            @Override
            public boolean hasContent() {
                return false;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public boolean isFirst() {
                return false;
            }

            @Override
            public boolean isLast() {
                return false;
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Pageable nextPageable() {
                return null;
            }

            @Override
            public Pageable previousPageable() {
                return null;
            }

            @Override
            public <U> Page<U> map(Function<? super Equipment, ? extends U> converter) {
                return null;
            }
        };
    }

    @Test
    void getEquipmentWithFilter() {
        EquipmentSearchCriteria equipmentSearchCriteria = new EquipmentSearchCriteria();
        equipmentSearchCriteria.setQuantity("5");
        equipmentSearchCriteria.setCity("Benin");
        equipmentSearchCriteria.setState("Edo");
        equipmentSearchCriteria.setTypeOfAsset("House Hold");

        when(equipmentSearchCriteriaRepository.findEquipmentWithFilter(equipmentSearchCriteria)).thenReturn(page);
        Page<Equipment> resultPage = equipmentSearchService.getEquipmentWithFilter(equipmentSearchCriteria);
        assertThat(resultPage).isNotNull().isEqualTo(page);
    }

    @Test
    void getAllEquipment() {
        EquipmentPage equipmentPage = new EquipmentPage();
        Equipment pictures1 = new Equipment();
        Equipment video = new Equipment();
        Equipment pictures2 = new Equipment();
        Equipment guns = new Equipment();
        List<Equipment> page = Arrays.asList(pictures1, video, pictures2, guns);

        Sort sort = Sort.by(equipmentPage.getSortDirection(), equipmentPage.getSortBy());
        Pageable pageable = PageRequest.of(equipmentPage.getPageNumber(), equipmentPage.getPageSize(), sort);
        when(equipmentRepository.findAll(pageable)).thenReturn(new PageImpl<>(page));
        Page<EquipmentResponseDto> returnedPage = equipmentSearchService.getAllEquipment(equipmentPage);
        assertThat(returnedPage).isNotNull();
        assertThat(returnedPage.getTotalElements()).isEqualTo(page.size());
    }

    @Test
    void getEquipmentById() {
        Long equipmentId = 3L;
        Equipment equipment = new Equipment();
        EquipmentResponseDto equipmentResponseDto = new EquipmentResponseDto();
        when(equipmentRepository.findEquipmentById(equipmentId)).thenReturn(equipment);
        when(mapper.map(equipment, EquipmentResponseDto.class)).thenReturn(equipmentResponseDto);
        EquipmentResponseDto returnedEquipment = equipmentSearchService.getEquipmentById(equipmentId);
        assertThat(returnedEquipment).isNotNull();
    }

    @Test
    void getAllEquipmentTypes() {
        EquipmentType equipmentTypeDto1 = new EquipmentType();
        EquipmentType equipmentTypeDto2 = new EquipmentType();
        EquipmentType equipmentTypeDto3 = new EquipmentType();
        List<EquipmentType> equipmentTypes = Arrays.asList(equipmentTypeDto1,equipmentTypeDto2, equipmentTypeDto3);

        when(equipmentTypeRepository.findAll()).thenReturn(equipmentTypes);
        List<EquipmentTypeResponseDto> returnedList = equipmentSearchService.getAllEquipmentTypes();
        assertThat(equipmentTypes).isNotNull();
//        assertThat(equipmentTypes.size()).isEqualTo(3);
        assertThat(equipmentTypes.size()).isEqualTo(returnedList.size());
    }
}