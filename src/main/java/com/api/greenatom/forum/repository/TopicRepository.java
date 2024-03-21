package com.api.greenatom.forum.repository;

import com.api.greenatom.forum.entity.Topic;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Hidden
public interface TopicRepository extends JpaRepository<Topic, Long> {
    Topic findByTitle(String title);

}