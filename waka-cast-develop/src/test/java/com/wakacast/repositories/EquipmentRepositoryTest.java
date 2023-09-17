package com.wakacast.repositories;

import com.wakacast.models.Equipment;
import com.wakacast.models.EquipmentImage;
import com.wakacast.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class EquipmentRepositoryTest {
    private final EquipmentRepository equipmentRepository;
    private final EquipmentImageRepository equipmentImageRepository;
    private final UserRepository userRepository;
    private Equipment newEquipment;
    private EquipmentImage equipmentImage;
    private User user;

    @Autowired
    public EquipmentRepositoryTest(EquipmentRepository equipmentRepository, EquipmentImageRepository equipmentImageRepository, UserRepository userRepository) {
        this.equipmentRepository = equipmentRepository;
        this.equipmentImageRepository = equipmentImageRepository;
        this.userRepository = userRepository;
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setEmail("Og@gmail.com");
        newEquipment = new Equipment();
        newEquipment.setEquipmentName("Camera");
        equipmentImage = new EquipmentImage("url");
        Set<EquipmentImage> equipmentImages = new HashSet<>();
        equipmentImages.add(equipmentImage);
        newEquipment.setEquipmentImages(equipmentImages);
        newEquipment.setEquipmentOwner(user);
    }

    @Test
    void saveEquipment() {
        userRepository.save(user);
        equipmentImageRepository.save(equipmentImage);
        Equipment savedEquipment = equipmentRepository.save(newEquipment);
        assertNotNull(savedEquipment);
        assertEquals(savedEquipment, newEquipment);
    }

    @Test
    void findEquipmentByIdAndEquipmentOwnerEmail() {
        equipmentRepository.save(newEquipment);
        Equipment equipment = equipmentRepository.findEquipmentByIdAndEquipmentOwnerEmail(newEquipment.getId(), user.getEmail()).get();
        assertNotNull(equipment);
        assertEquals(equipment, newEquipment);
    }
}