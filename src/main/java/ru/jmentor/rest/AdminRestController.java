package ru.jmentor.rest;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.http.HttpStatus;import org.springframework.http.ResponseEntity;import org.springframework.web.bind.annotation.*;import ru.jmentor.model.User;import ru.jmentor.service.UserService;import java.util.List;@RestController@CrossOrigin@RequestMapping("/rest/admin")public class AdminRestController {    private UserService service;    @Autowired    public void setService(UserService service) { this.service = service; }    @GetMapping    public ResponseEntity<?> usersTable() {        List<User> listUsers = service.getAll();        if(listUsers.isEmpty()){            return new ResponseEntity<>(HttpStatus.NO_CONTENT);        }        return ResponseEntity.ok(listUsers);    }    @PostMapping    public void adminCreateUser(@RequestBody User user) { service.register(user); }    @DeleteMapping    public void adminDeleteUser(@RequestBody Long id) { service.delete(id); }    @PutMapping    public void adminEditUser(@RequestBody User user) {        service.edit(user);    }}