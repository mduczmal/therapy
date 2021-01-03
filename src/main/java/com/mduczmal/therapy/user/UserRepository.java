package com.mduczmal.therapy.user;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

//Repository design pattern
//Interface segregation principle - ten interfjes dostarcza metod pozwalających zapisywać i modyfikować szczegółowe dane
//użytkowników. Za dane dotyczące autoryzacji odpowiada interfejs UserAccountRepository
public interface UserRepository extends CrudRepository<User, UUID> {
}
