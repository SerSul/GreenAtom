package com.api.greenatom.forum.service.impl;

import com.api.greenatom.forum.dto.request.TopicRequestDTO;
import com.api.greenatom.forum.dto.response.MessageResponseDTO;
import com.api.greenatom.forum.dto.response.TopicResponseDTO;
import com.api.greenatom.forum.entity.Message;
import com.api.greenatom.forum.entity.Topic;
import com.api.greenatom.forum.exception.TopicAlreadyExistsException;
import com.api.greenatom.forum.repository.TopicRepository;
import com.api.greenatom.forum.service.TopicService;
import com.api.greenatom.security.entity.User;
import com.api.greenatom.security.repository.UserRepository;
import com.api.greenatom.security.service.UserService;
import com.api.greenatom.security.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Hidden
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    /**
     * Конструктор для сервиса топиков.
     * @param topicRepository репозиторий для взаимодействия с топиками.
     * @param userRepository репозиторий для взаимодействия с пользователями.
     * @param userService сервис для работы с пользователями.
     */
    @Autowired
    public TopicServiceImpl(TopicRepository topicRepository, UserRepository userRepository, UserServiceImpl userService) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    /**
     * Создает новый топик с начальным сообщением.
     * @param topicRequestDTO DTO для создания топика.
     * @return DTO созданного топика.
     * @throws TopicAlreadyExistsException если топик с таким названием уже существует.
     */
    @Override
    public TopicResponseDTO createTopic(TopicRequestDTO topicRequestDTO) {
        User user = userService.getCurrentUser();

        if (topicRepository.findByTitle(topicRequestDTO.getTitle()) != null) {
            throw new TopicAlreadyExistsException("Топик с таким названием уже существует: " + topicRequestDTO.getTitle());
        }

        Topic topic = new Topic();
        topic.setTitle(topicRequestDTO.getTitle());

        Message message = new Message();
        message.setUser(user);
        message.setText(topicRequestDTO.getMessage());
        topic.getMessages().add(message);

        Topic savedTopic = topicRepository.save(topic);

        return savedTopic.toTopicResponseDTO();
    }


    /**
     * Обновляет существующий топик.
     * @param id Идентификатор топика, который нужно обновить.
     * @param topicDetails Обновленные данные топика.
     * @return Обновленный топик.
     * @throws ResourceNotFoundException если топик с указанным id не найден.
     */
    @Override
    public Topic updateTopic(Long id, Topic topicDetails) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Топика с таким id не найдено :: " + id));
        topic.setTitle(topicDetails.getTitle());
        // Обновите другие поля при необходимости
        return topicRepository.save(topic);
    }

    /**
     * Удаляет топик по идентификатору.
     * @param id Идентификатор топика для удаления.
     * @throws ResourceNotFoundException если топик с указанным id не найден.
     */
    @Override
    public void deleteTopic(Long id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Топика с таким id не найдено :: " + id));
        topicRepository.delete(topic);
    }

    /**
     * Возвращает топик по его идентификатору.
     * @param id Идентификатор топика.
     * @return Топик.
     * @throws ResourceNotFoundException если топик с указанным id не найден.
     */
    @Override
    public Topic getTopicById(Long id) {
        return topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Топика с таким id не найдено :: " + id));
    }
    /**
     * Возвращает список всех топиков.
     * @return Список DTO топиков.
     */
    @Override
    public List<TopicResponseDTO> getAllTopics() {
        List<Topic> topics = topicRepository.findAll();
        // Преобразовываем список топиков в список DTO
        List<TopicResponseDTO> topicResponseDTOS = topics.stream()
                .map(Topic::toTopicResponseDTO)
                .collect(Collectors.toList());
        return topicResponseDTOS;
    }

}
