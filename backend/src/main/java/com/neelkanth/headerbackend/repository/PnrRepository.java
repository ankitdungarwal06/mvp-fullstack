package com.neelkanth.headerbackend.repository;

import com.neelkanth.headerbackend.entity.Pnr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PnrRepository extends JpaRepository<Pnr, Long> {
}
