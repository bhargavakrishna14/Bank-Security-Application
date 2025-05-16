package dev.bhargav.banksecurity.repository;

import dev.bhargav.banksecurity.entity.Nominee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NomineeRepository extends JpaRepository<Nominee, Long> {
}
