package com.example.money_way.repository;
import com.example.money_way.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Optional<Wallet> findByUserId(Long userId);
    Optional<Wallet> findByVirtualAccountRef(String virtualAccountRef);

}
