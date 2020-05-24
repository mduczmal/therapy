package com.mduczmal.therapy.ad.comment;

import org.springframework.data.repository.CrudRepository;

//Repository design pattern
public interface CommentRepository extends CrudRepository<Comment, Long> {
}
