package com.mduczmal.therapy.therapist;

import org.springframework.data.repository.CrudRepository;
import java.util.UUID;

//Repository design pattern
public interface TherapistRepository extends CrudRepository<Therapist, UUID> {
}
