package com.mduczmal.therapy;

import org.springframework.data.repository.CrudRepository;
import java.util.UUID;

public interface TherapistRepository extends CrudRepository<Therapist, UUID> {
}
