package dev.bhargav.banksecurity.repository;

import dev.bhargav.banksecurity.entity.Investment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestmentRepository extends JpaRepository<Investment,Long> {
}
