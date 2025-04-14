package lt.codeacademy.testdatatool.controller;

import lombok.RequiredArgsConstructor;
import lt.codeacademy.testdatatool.entity.UserData;
import lt.codeacademy.testdatatool.service.UserDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@RequestMapping("/api/user-data")
@RequiredArgsConstructor
public class UserDataController {
    private final UserDataService userDataService;

    @PostMapping
    public ResponseEntity<UserData> addUserData(@RequestBody UserData userData) {
        UserData newUserData = userDataService.addUserData(userData);
        return new ResponseEntity<>(newUserData, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserData>> getUserData() {
        return new ResponseEntity<>(userDataService.getUserData(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserData> getUserDataById(@PathVariable Long id) {
        try {
            UserData userData = userDataService.getUserDataById(id);
            return new ResponseEntity<>(userData, HttpStatus.OK);
        } catch (HttpClientErrorException.NotFound e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
