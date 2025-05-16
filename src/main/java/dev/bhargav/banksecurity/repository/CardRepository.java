package dev.bhargav.banksecurity.repository;

import dev.bhargav.banksecurity.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    // Derived Query to fetch Card by cardNumber.
    Optional<Card> findByCardNumber(Long cardNumber);

}
