package com.mduczmal.therapy.user.moderator;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ModeratorRepository extends CrudRepository<Moderator, UUID> {
}
