package com.mduczmal.therapy.ad;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

//Repository design pattern
public interface AdRepository extends CrudRepository<Ad, UUID> {
}
