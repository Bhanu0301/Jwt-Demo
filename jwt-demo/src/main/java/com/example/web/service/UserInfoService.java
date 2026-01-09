package com.example.web.service;

import com.example.web.entity.UserInfo;
import com.example.web.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    private final PasswordEncoder encoder;
    private final UserInfoRepository userRepository;
    @Autowired
    public UserInfoService(PasswordEncoder encoder, UserInfoRepository userRepository) {
        this.encoder = encoder;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Optional<UserInfo> userInfo = userRepository.findByEmail(username);
        if(userInfo.isEmpty()){
            throw new UsernameNotFoundException("User not found for email : "+ username);
        }

        //now we got userInfo object
        UserInfo user = userInfo.get();
        List<GrantedAuthority> authorities =
                List.of(new SimpleGrantedAuthority(user.getRoles()));
        //Spring security understands UserDetails
        return new User(user.getEmail(),user.getPassword(),authorities);
//        return new User(user.getEmail(), user.getPassword(), user.getRoles());
    }
    public String addUser(UserInfo userInfo){
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        userRepository.save(userInfo);
        return "User added successfully";
    }
}
