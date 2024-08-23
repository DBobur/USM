package uz.pro.usm.service;

import uz.pro.usm.domain.dto.request.GroupRequest;
import uz.pro.usm.domain.dto.response.GroupResponse;
import uz.pro.usm.domain.entity.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pro.usm.domain.entity.user.User;
import uz.pro.usm.repository.GroupRepository;
import uz.pro.usm.repository.user.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    @Transactional
    public GroupResponse createGroup(GroupRequest groupRequest) {
        User creator = userRepository.findById(groupRequest.getCreatorId())
                .orElseThrow(() -> new RuntimeException("Creator not found"));

        User mentor = userRepository.findById(groupRequest.getMentorId())
                .orElseThrow(() -> new RuntimeException("Mentor not found"));

        Set<User> users = new HashSet<>();
        for (Long userId : groupRequest.getUserIds()) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
            users.add(user);
        }

        Group group = new Group();
        group.setName(groupRequest.getName());
        group.setDescription(groupRequest.getDescription());
        group.setCreator(creator);
        group.setMentor(mentor);
        group.setUsers(users);

        group = groupRepository.save(group);

        return mapToResponse(group);
    }

    @Transactional(readOnly = true)
    public GroupResponse getGroupById(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found with ID: " + id));
        return mapToResponse(group);
    }

    @Transactional
    public GroupResponse updateGroup(Long id, GroupRequest groupRequest) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found with ID: " + id));

        group.setName(groupRequest.getName());
        group.setDescription(groupRequest.getDescription());

        User mentor = userRepository.findById(groupRequest.getMentorId())
                .orElseThrow(() -> new RuntimeException("Mentor not found"));
        group.setMentor(mentor);

        Set<User> users = groupRequest.getUserIds().stream()
                .map(userId -> userRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId)))
                .collect(Collectors.toSet());
        group.setUsers(users);

        group = groupRepository.save(group);
        return mapToResponse(group);
    }

    @Transactional
    public void deleteGroup(Long id) {
        if (!groupRepository.existsById(id)) {
            throw new RuntimeException("Group not found with ID: " + id);
        }
        groupRepository.deleteById(id);
    }

    @Transactional
    public GroupResponse addUsersToGroup(Long groupId, List<Long> userIds) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found with id " + groupId));

        List<User> users = userRepository.findAllById(userIds);

        if (users.isEmpty()) {
            throw new RuntimeException("No users found with the provided IDs");
        }

        group.getUsers().addAll(users);
        groupRepository.save(group);

        return mapToResponse(group);
    }

    @Transactional
    public GroupResponse assignMentorToGroup(Long groupId, Long mentorId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found with id " + groupId));

        User mentor = userRepository.findById(mentorId)
                .orElseThrow(() -> new RuntimeException("Mentor not found with id " + mentorId));

        group.setMentor(mentor);
        groupRepository.save(group);

        return mapToResponse(group);
    }

    private GroupResponse mapToResponse(Group group) {
        return new GroupResponse(
                group.getId(),
                group.getName(),
                group.getDescription(),
                group.getCreator().getId(),
                group.getMentor().getId(),
                group.getUsers().stream().map(User::getId).collect(Collectors.toSet()),
                group.getCreatedTime(),
                group.getUpdatedTime()
        );
    }
}
