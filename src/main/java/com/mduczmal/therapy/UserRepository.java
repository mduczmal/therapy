package com.mduczmal.therapy;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<SecurityDetails, String> {
}
