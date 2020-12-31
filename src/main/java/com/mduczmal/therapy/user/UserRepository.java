package com.mduczmal.therapy.user;

import org.springframework.data.repository.CrudRepository;

//Repository design pattern
//Interface segregation principle - ten interfjes dostarcza metod pozwalających zapisywać i modyfikować dane
//dotyczące autoryzacji. Za szczegółowe dane terapeutów odpowiada interfejs TherapistRepository
public interface UserRepository extends CrudRepository<UserAccount, String> {
}
