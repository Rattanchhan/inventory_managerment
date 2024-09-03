package com.inventory_managerment.feature.user;
import com.inventory_managerment.feature.user.dto.UserRequest;
import com.inventory_managerment.feature.user.dto.UserResponse;
import com.inventory_managerment.feature.user.dto.UserUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserServices userServices;
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UserResponse createUser(@Valid @RequestBody UserRequest userRequest) throws Exception {
        return userServices.addUser(userRequest);
    }
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/{id}/find")
    public UserResponse getUser(@PathVariable String id){
        return userServices.getUserById(Long.parseLong(id));
    }
    @PatchMapping("/{id}/update")
    public UserResponse updateUser(@PathVariable String id, @Valid @RequestBody UserUpdateRequest userUpdateRequest){
        return userServices.updateUser(Long.parseLong(id),userUpdateRequest);
    }
    @GetMapping
    public Page<UserResponse> getUsers(@RequestParam(required = false,value = "pageNumber",defaultValue="0") String page, @RequestParam(required = false,value ="pageSize",defaultValue = "25") String size){
        return userServices.getUsers(Integer.parseInt(page),Integer.parseInt(size));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteUser(@PathVariable String id){
        return userServices.deleteUser(Long.parseLong(id));
    }
}
