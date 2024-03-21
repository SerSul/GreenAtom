package com.api.greenatom.forum.repository;

import com.api.greenatom.forum.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {}