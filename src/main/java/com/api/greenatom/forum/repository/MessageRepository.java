package com.api.greenatom.forum.repository;

import com.api.greenatom.forum.entity.Message;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Hidden
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByTopicId(Long topic_id);

}