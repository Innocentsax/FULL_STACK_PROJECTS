package com.example.cedarxpressliveprojectjava010.service.implementation;

import com.example.cedarxpressliveprojectjava010.repository.BlacklistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Date;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BlacklistServiceImplTest {
    @Mock private BlacklistRepository blacklistRepo;
    @InjectMocks
    private BlacklistServiceImpl blacklistService;

    private String token;
    private Date expiryDate;

    @BeforeEach
    public void setUp(){
        token = "aa.bb.9%%%";
        expiryDate = new Date(12345);
        System.out.println(expiryDate);
    }


    @DisplayName("Unit for blacklisting a token")
    @Test
    public void ShouldSaveAndReturnInstanceOfBlacklist(){
        var response = blacklistService.blackListToken(token, expiryDate);

        verify(blacklistRepo, times(1)).save(any());
    }

    @DisplayName("Unit for checking for if token blacklisted")
    @Test
    public void ShouldCallTheExistByToken(){
        blacklistService.isTokenBlackListed(token);

        verify(blacklistRepo, times(1)).existsByToken(token);
    }
}