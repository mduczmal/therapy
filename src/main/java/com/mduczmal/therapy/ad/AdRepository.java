package com.mduczmal.therapy.ad;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface AdRepository extends CrudRepository<Ad, UUID> {
}
