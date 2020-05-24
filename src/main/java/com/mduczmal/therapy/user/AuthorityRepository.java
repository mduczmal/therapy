package com.mduczmal.therapy.user;

import org.springframework.data.repository.CrudRepository;

//Repository design pattern
public interface AuthorityRepository extends CrudRepository<Authority, Long> {
}
