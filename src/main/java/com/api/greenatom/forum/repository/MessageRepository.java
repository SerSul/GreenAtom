package com.api.greenatom.forum.repository;

import com.api.greenatom.forum.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByTopicId(Long topic_id);

}