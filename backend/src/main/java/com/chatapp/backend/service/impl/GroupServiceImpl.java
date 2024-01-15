package com.chatapp.backend.service.impl;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.chatapp.backend.model.Group;
import com.chatapp.backend.repository.GroupRepository;
import com.chatapp.backend.service.GroupService;

@Service
public class GroupServiceImpl implements GroupService{
    
    private GroupRepository groupRepository;

    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public Group getGroup(Long id) {
        Optional<Group> group = groupRepository.findById(id);
        if (group.isPresent()) return group.get();
        else throw new NoSuchElementException("No Group found");
    }

    @Override
    public Group saveGroup(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public void updateGroup(Group group) {
        groupRepository.save(group);
    }

}
