package com.mduczmal.therapy.ad.comment;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

//Repository design pattern
public interface CommentRepository extends CrudRepository<Comment, UUID> {
}
