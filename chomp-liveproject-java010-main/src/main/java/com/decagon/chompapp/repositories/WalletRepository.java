package com.decagon.chompapp.repositories;

import com.decagon.chompapp.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet,Long> {
   Wallet findWalletByUser_Email(String email);

   @Query("SELECT w From Wallet w WHERE " + "Lower(" + " w.user.email)" + "=:email")
   Optional<Wallet> findWalletByUser_Email2(String email);
}
