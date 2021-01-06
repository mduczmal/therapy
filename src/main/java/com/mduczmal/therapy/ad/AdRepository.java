package com.mduczmal.therapy.ad;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AdRepository extends JpaRepository<Ad, UUID> {
}