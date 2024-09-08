package com.emma.temp.service;

import com.emma.temp.entities.User;
import com.emma.temp.model.request.AccountRequest;
import com.emma.temp.model.request.PasswordReset;

import java.util.List;

public interface AccountService {
    List<User> listAllUsers();

    User getUserById(Long id);

    User getUserByUsername(String username);

    User createUser(AccountRequest accountRequest);

    User updateUser(Long id, AccountRequest accountRequest);

    User updateUserByUsername(String email, AccountRequest accountRequest);

    User getUserByEmailAndPassword(String email, String password);

    void terminateAccount(Long id);

    void resetPassword(PasswordReset passwordReset);
}
