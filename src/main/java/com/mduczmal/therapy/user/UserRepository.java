package com.mduczmal.therapy.user;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<SecurityDetails, String> {
}
