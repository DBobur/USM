package uz.pro.usm.controller;

import uz.pro.usm.domain.dto.request.GroupRequest;
import uz.pro.usm.domain.dto.response.GroupResponse;
import uz.pro.usm.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @PostMapping
    public ResponseEntity<GroupResponse> createGroup(@RequestBody GroupRequest groupRequest) {
        return new ResponseEntity<>(groupService.createGroup(groupRequest), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MANAGER')")
    @GetMapping("/{id}")
    public ResponseEntity<GroupResponse> getGroupById(@PathVariable Long id) {
        return ResponseEntity.ok(groupService.getGroupById(id));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<GroupResponse> updateGroup(
            @PathVariable Long id, @RequestBody GroupRequest groupRequest) {
        return ResponseEntity.ok(groupService.updateGroup(id, groupRequest));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        groupService.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }

    // Guruhga userlar biriktirish
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @PostMapping("/{id}/users")
    public ResponseEntity<GroupResponse> addUsersToGroup(
            @PathVariable Long id, @RequestBody List<Long> userIds) {
        GroupResponse updatedGroup = groupService.addUsersToGroup(id, userIds);
        return ResponseEntity.ok(updatedGroup);
    }

    // Guruhga mentor biriktirish
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    @PostMapping("/{id}/mentor")
    public ResponseEntity<GroupResponse> assignMentorToGroup(
            @PathVariable Long id, @RequestBody Long mentorId) {
        GroupResponse updatedGroup = groupService.assignMentorToGroup(id, mentorId);
        return ResponseEntity.ok(updatedGroup);
    }
}
