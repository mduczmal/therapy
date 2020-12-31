package com.mduczmal.therapy.user.therapist;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

//Repository design pattern
//Interface segregation principle - ten interfjes dostarcza metod pozwalających zapisywać i modyfikować szczegółowe dane
//terapeutów. Za dane dotyczące autoryzacji odpowiada interfejs UserRepository
public interface TherapistRepository extends CrudRepository<com.mduczmal.therapy.user.therapist.Therapist, UUID> {
}
