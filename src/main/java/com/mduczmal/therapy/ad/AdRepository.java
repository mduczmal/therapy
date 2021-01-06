package com.mduczmal.therapy.ad;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

//Repository design pattern
public interface AdRepository extends JpaRepository<Ad, UUID> {
}