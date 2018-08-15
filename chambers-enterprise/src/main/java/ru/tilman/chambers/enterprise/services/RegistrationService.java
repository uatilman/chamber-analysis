package ru.tilman.chambers.enterprise.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.tilman.chambers.enterprise.entity.security.User;
import ru.tilman.chambers.enterprise.repository.RoleRepository;
import ru.tilman.chambers.enterprise.repository.UserRepository;
import ru.tilman.chambers.enterprise.entity.security.Role;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegistrationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public RegistrationService(
            @Qualifier("userRepository") UserRepository userRepository,
            @Qualifier("roleRepository") RoleRepository roleRepository
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public Iterable<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void createUser(User user) {
        setBaseUserProperties(user);
        userRepository.save(user);
    }

    public Role findRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    public boolean isLoginExist(String login) {
        return findUserByLogin(login) != null;
    }

    public void setBaseUserProperties(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setEnabled(true);
        user.setRoleList(getBaseRoleList());
    }

    public List<Role> getBaseRoleList() {
        Role role = findRoleByName("user"); // TODO: 07.08.18 вынести базувую роль в настройки
        List<Role> roleList = new ArrayList<>();
        roleList.add(role);
        return roleList;
    }
}
