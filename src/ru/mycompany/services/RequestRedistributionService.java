package ru.mycompany.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mycompany.model.entity.Request;
import ru.mycompany.model.entity.RequestStatus;
import ru.mycompany.model.entity.UserStatus;
import ru.mycompany.model.entity.User;
import ru.mycompany.repository.RequestRepository;
import ru.mycompany.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class RequestRedistributionService {

    private final UserRepository userRepository;

    private final RequestRepository requestRepository;

    @Transactional
    public void redistributeRequestsOnUserStatusChange(User user) {
        if (user.getStatus() == UserStatus.OFFLINE) {
            List<User> onlineUsers = userRepository.findByStatus(UserStatus.ONLINE);
            if (!onlineUsers.isEmpty()) {
                Collections.shuffle(onlineUsers);
                List<Request> requests = requestRepository.findByStatus(RequestStatus.OPEN);
                int totalRequests = requests.size();
                int usersCount = onlineUsers.size();
                int requestsPerUser = totalRequests / usersCount;
                int extraRequests = totalRequests % usersCount;

                for (int i = 0; i < usersCount; i++) {
                    int requestsForCurrentUserLimit = i < extraRequests ? requestsPerUser + 1 : requestsPerUser;
                    List<Request> requestsForCurrentUser = requests.stream()
                            .limit(requestsForCurrentUserLimit)
                            .toList();
                    for (Request request : requestsForCurrentUser) {
                        request.setResponsible(onlineUsers.get(i));
                        requestRepository.save(request);
                    }
                    requests = requests.stream()
                            .skip(requestsForCurrentUser.size())
                            .toList();
                }
            } else {
                log.warn("no online users found");
            }
        } else {
            log.warn("user {} - {} is online", user.getId(), user.getLogin());
        }
    }

}