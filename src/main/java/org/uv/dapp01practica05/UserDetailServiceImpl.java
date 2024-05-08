package org.uv.dapp01practica05;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.uv.dapp01practica05.User.Entities.User;
import org.uv.dapp01practica05.User.Repositories.UserRepository;

import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(UserDetailModel::new).orElseThrow(()->new UsernameNotFoundException("Invalid Username"));
    }
}