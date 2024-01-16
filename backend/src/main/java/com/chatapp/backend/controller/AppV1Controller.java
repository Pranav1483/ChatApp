package com.chatapp.backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatapp.backend.service.ConnectionService;
import com.chatapp.backend.service.GroupService;
import com.chatapp.backend.service.MessageReactionService;
import com.chatapp.backend.service.MessageService;
import com.chatapp.backend.service.RoleService;
import com.chatapp.backend.service.UserRoleService;
import com.chatapp.backend.service.UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
public class AppV1Controller {
    
    ConnectionService connectionService;
    GroupService groupService;
    MessageReactionService messageReactionService;
    MessageService messageService;
    RoleService roleService;
    UserRoleService userRoleService;
    UserService userService;

    public AppV1Controller(ConnectionService connectionService, 
                            GroupService groupService, 
                            MessageReactionService messageReactionService, 
                            MessageService messageService, 
                            RoleService roleService,
                            UserRoleService userRoleService,
                            UserService userService) {
                                this.connectionService = connectionService;
                                this.groupService = groupService;
                                this.messageReactionService = messageReactionService;
                                this.messageService = messageService;
                                this.roleService = roleService;
                                this.userRoleService = userRoleService;
                                this.userService = userService;
                            }
    

}
