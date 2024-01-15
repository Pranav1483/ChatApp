package com.chatapp.backend.service;

import com.chatapp.backend.model.Group;

public interface GroupService {
    
    Group getGroup(Long id);
    Group saveGroup(Group group);
    void updateGroup(Group group);

}
