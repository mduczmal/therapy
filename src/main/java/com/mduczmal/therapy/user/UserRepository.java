package com.mduczmal.therapy.user;

import org.springframework.data.repository.CrudRepository;

//Repository design pattern
public interface UserRepository extends CrudRepository<SecurityDetails, String> {
}
