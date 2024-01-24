package com.chatapp.backend.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.chatapp.backend.config.JwtTokenUtil;
import com.chatapp.backend.dto.MessageDTO;
import com.chatapp.backend.dto.MessageRequestDTO;
import com.chatapp.backend.dto.UserDTOForAnon;
import com.chatapp.backend.dto.UserDTOForUser;
import com.chatapp.backend.enm.UserStatus;
import com.chatapp.backend.model.AuthRequest;
import com.chatapp.backend.model.Constants;
import com.chatapp.backend.model.LatestMessage;
import com.chatapp.backend.model.Message;
import com.chatapp.backend.model.User;
import com.chatapp.backend.service.ConnectionService;
import com.chatapp.backend.service.GroupService;
import com.chatapp.backend.service.LatestMessageService;
import com.chatapp.backend.service.MessageReactionService;
import com.chatapp.backend.service.MessageService;
import com.chatapp.backend.service.RoleService;
import com.chatapp.backend.service.UserRoleService;
import com.chatapp.backend.service.UserService;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
public class AppV1Controller {
    
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    ConnectionService connectionService;
    GroupService groupService;
    LatestMessageService latestMessageService;
    MessageReactionService messageReactionService;
    MessageService messageService;
    RoleService roleService;
    UserRoleService userRoleService;
    UserService userService;
    AuthenticationManager authenticationManager;
    JwtTokenUtil jwtTokenUtil;

    public AppV1Controller(ConnectionService connectionService, 
                            GroupService groupService,
                            LatestMessageService latestMessageService, 
                            MessageReactionService messageReactionService, 
                            MessageService messageService, 
                            RoleService roleService,
                            UserRoleService userRoleService,
                            UserService userService,
                            AuthenticationManager authenticationManager,
                            JwtTokenUtil jwtTokenUtil) {
                                this.connectionService = connectionService;
                                this.groupService = groupService;
                                this.latestMessageService = latestMessageService;
                                this.messageReactionService = messageReactionService;
                                this.messageService = messageService;
                                this.roleService = roleService;
                                this.userRoleService = userRoleService;
                                this.userService = userService;
                                this.authenticationManager = authenticationManager;
                                this.jwtTokenUtil = jwtTokenUtil;
                            }
    
    @GetMapping("/public/status")
    public ResponseEntity<String> statusCheck() {
        return ResponseEntity.status(200).body("OK");
    }

    @GetMapping("/startup")
    public ResponseEntity<UserDTOForUser> startup(@RequestHeader(Constants.HEADER_STRING) String tokenHeader) {
        String token = tokenHeader.replace(Constants.TOKEN_PREFIX, "");
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.getUserByUsername(username);
        user.setStatus(UserStatus.ONLINE);
        userService.updateUser(user);
        return ResponseEntity.status(200).body(new UserDTOForUser(user));
    }

    @GetMapping("/exit")
    public ResponseEntity<String> exit(@RequestHeader(Constants.HEADER_STRING) String tokenHeader) {
        String token = tokenHeader.replace(Constants.TOKEN_PREFIX, "");
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userService.getUserByUsername(username);
        user.setStatus(UserStatus.OFFLINE);
        user.setLastActive(LocalDateTime.now());
        userService.updateUser(user);
        return ResponseEntity.status(200).body("OK");
    }
    
    @PostMapping("/public/user")
    public ResponseEntity<?> createUser(@RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldErrors().get(0);
            String errMsg = fieldError.getDefaultMessage();
            return ResponseEntity.status(400).body(errMsg);
        }
        try {
            userService.saveUser(user);
            return ResponseEntity.status(200).body(new UserDTOForUser(user));
        } catch (DataIntegrityViolationException e) {
            String errMsg = "";
            String violatedField = e.getMessage();
            if (violatedField.contains("username")) {
                errMsg = "Username already exists";
            }
            else if (violatedField.contains("email")) {
                errMsg = "Email already exists";
            }
            return ResponseEntity.status(409).body(errMsg);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("");
        }
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username, @RequestHeader(Constants.HEADER_STRING) String tokenHeader) {
        String token = tokenHeader.replace(Constants.TOKEN_PREFIX, "");
        String decoded_username = jwtTokenUtil.getUsernameFromToken(token);
        if (username.equals(decoded_username)) {
            UserDTOForUser res = new UserDTOForUser(userService.getUserByUsername(username));
            return ResponseEntity.status(200).body(res);
        } else {
            UserDTOForAnon res = new UserDTOForAnon(userService.getUserByUsername(username));
            return ResponseEntity.status(200).body(res);
        }
    }

    @PostMapping("/public/login")
    public ResponseEntity<String> loginUser(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            if (authentication.isAuthenticated()) return ResponseEntity.status(200).body(jwtTokenUtil.generateToken(authRequest.getUsername()));
            else return ResponseEntity.status(401).body("Bad Creds");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Bad Creds");
        } catch (Exception e){
            return ResponseEntity.status(403).body(e.getMessage());
        }     
    }

    @GetMapping("/sse/connect/{username}")
    public SseEmitter connect(@PathVariable String username) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        try {
            emitter.send(SseEmitter.event().name("INIT"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        emitters.put(username, emitter);
        emitter.onTimeout(() -> emitters.remove(username, emitter));
        emitter.onCompletion(() -> emitters.remove(username, emitter));
        return emitter;
    }

    @PostMapping("/sse/user")
    public void dispatchEvent(@RequestBody MessageRequestDTO messageRequestDTO) throws IOException {
        Message message = messageService.DTOtoMessage(messageRequestDTO.getMessageDTO(), messageRequestDTO.getToId(), messageRequestDTO.getToGroup());
        messageService.saveMessage(message);
        SseEmitter emitter = emitters.get(message.getUserTo().getUsername());
        emitter.send(new MessageDTO(message));
    }

    @GetMapping("/message/{id}")
    public ResponseEntity<Message> getMessage(@PathVariable Long id) {
        Message message = messageService.getMessage(id);
        return ResponseEntity.status(200).body(message);
    }

    @GetMapping("/message/user/{id}")
    public ResponseEntity<List<MessageDTO>> getUserMessages(@PathVariable String id) {
        String[] ids = id.split("-");
        Long userFromId = Long.parseLong(ids[0]);
        Long userToId = Long.parseLong(ids[1]);
        User userFrom = userService.getUserById(userFromId);
        User usetTo = userService.getUserById(userToId);
        List<Message> messages = messageService.getUserConvo(userFrom, usetTo, 0, 100);
        List<MessageDTO> messageDTOs = new ArrayList<>();
        for (int i = messages.size() - 1; i >= 0; i--) {
            messageDTOs.add(new MessageDTO(messages.get(i)));
        }
        return ResponseEntity.status(200).body(messageDTOs);
    }

    @GetMapping("/latestmessage/{id}")
    public ResponseEntity<String> addLatestMessage(@PathVariable String id) {
        String[] ids = id.split("-");
        Long userFromId = Long.parseLong(ids[0]);
        Long userToId = Long.parseLong(ids[1]);
        Long messageId = Long.parseLong(ids[2]);
        User userFrom = userService.getUserById(userFromId);
        User userTo = userService.getUserById(userToId);
        Message message = messageService.getMessage(messageId);
        try {
            LatestMessage latestMessage = latestMessageService.getByUsers(userFrom, userTo);
            latestMessage.setMessage(message);
            latestMessageService.saveLatestMessage(latestMessage);
            return ResponseEntity.status(200).body("OK");
        } catch(Exception e) {
            LatestMessage latestMessage = new LatestMessage();
            latestMessage.setUser(userFrom);
            latestMessage.setInboxUser(userTo);
            latestMessage.setMessage(message);
            latestMessageService.saveLatestMessage(latestMessage);
            return ResponseEntity.status(200).body("OK");
        }
    }

    @GetMapping("/inbox/{username}")
    public ResponseEntity<List<UserDTOForUser>> getUserInbox(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        List<LatestMessage> inboxMessages = latestMessageService.getByUser(user);
        List<UserDTOForUser> res = new ArrayList<>();
        Collections.sort(inboxMessages, (o1, o2) -> o2.getMessage().getCreatedAt().compareTo(o1.getMessage().getCreatedAt()));
        for (LatestMessage latestmessage: inboxMessages) {
            if (latestmessage.getUser().getUsername().equals(username)) {
                res.add(new UserDTOForUser(latestmessage.getInboxUser()));
            }
            else {
                res.add(new UserDTOForUser(latestmessage.getUser()));
            }
        }
        return ResponseEntity.status(200).body(res); 
    }
}
