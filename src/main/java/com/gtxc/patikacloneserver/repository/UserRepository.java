package com.gtxc.patikacloneserver.repository;

/*
    Created by gt at 3:38 PM on Wednesday, March 02, 2022.
    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.repository.
*/

import com.gtxc.patikacloneserver.exceptions.SQLite3Exception;
import com.gtxc.patikacloneserver.helper.DBConnector;
import com.gtxc.patikacloneserver.helper.SQLStatement;
import com.gtxc.patikacloneserver.model.Role;
import com.gtxc.patikacloneserver.model.RoleType;
import com.gtxc.patikacloneserver.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.*;

@Repository
public class UserRepository implements SimpleCrudRepository<User, Long> {

    private static final Logger log = LoggerFactory.getLogger(UserRepository.class);

    private final RoleRepository roleRepository;

    @Autowired
    public UserRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<User> findById(Long id) {
        Optional<User> optionalUser = Optional.empty();
        try (DBConnector dbConnector = new DBConnector();
             PreparedStatement preparedStatement = dbConnector.getConnection().prepareStatement(SQLStatement.prQueryUserById)) {
             preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    optionalUser = Optional.of(fillUserInfo(id, resultSet));
                    log.info("query by id : " + optionalUser.orElse(null));
                } else {
                    log.warn("Could not found user : " + id);
                }
            }
            return optionalUser;
        } catch (SQLException e) {
            String dbError = "Failed find user by id : " + id + " : " + e.getMessage();
            log.warn(dbError);
            throw new SQLite3Exception(dbError);
        }
    }

    @Override
    public List<User> findAll() {
        List<User> userList = new ArrayList<>();
        User user;
        try (DBConnector dbConnector = new DBConnector();
             Statement statement = dbConnector.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(SQLStatement.queryAllUsers)) {
            while (resultSet.next()) {
                user = fillUserInfo(resultSet.getLong("ID"), resultSet);
                userList.add(user);
            }
            if (userList.isEmpty()) {
                log.warn("Could not found any user");
            } else {
                log.info("query : " + userList);
            }
            return userList;
        } catch (SQLException e) {
            String dbError = "Failed find all users : " + e.getMessage();
            log.warn(dbError);
            throw new SQLite3Exception(dbError);
        }
    }

    @Override
    public Optional<User> save(User user) {
        try (DBConnector dbConnector = new DBConnector();
             PreparedStatement preparedStatement = dbConnector.getConnection().prepareStatement(SQLStatement.prAddNewUser)) {
            if (user.getId() != null) {
                preparedStatement.setLong(1, user.getId());
            } else {
                preparedStatement.setNull(1, java.sql.Types.NULL);
            }
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setString(4, user.getUsername());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getPassword());
            if (preparedStatement.executeUpdate() <= 0) {
                return Optional.empty();
            }
            Optional<User> saved = findByUsername(user.getUsername());
            saved.ifPresent(s -> saveUserRoles(s.getId(), user.getRoles()));
            saved = findByUsername(user.getUsername());
            log.info("save : " + saved.orElse(null));
            return saved;
        } catch (SQLException e) {
            String dbError = "Failed save user : " + e.getMessage() + " : " + user;
            log.warn(dbError);
            throw new SQLite3Exception(dbError);
        }
    }

    @Override
    public Long count() {
        long rowCount = 0;
        try (DBConnector dbConnector = new DBConnector();
             Statement statement = dbConnector.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(SQLStatement.countUserRows)) {
            if (resultSet.next()) {
                rowCount = resultSet.getLong(1);
            }
            log.info("User table row count : " + rowCount);
            return rowCount;
        } catch (SQLException e) {
            String dbError = "Failed get row count : " + e.getMessage();
            log.warn(dbError);
            throw new SQLite3Exception(dbError);
        }
    }

    @Override
    public void deleteAll() {
        try (DBConnector dbConnector = new DBConnector();
             Statement statement = dbConnector.getConnection().createStatement()) {
            statement.execute(SQLStatement.dropTable("user"));
            statement.execute(SQLStatement.createUserTAble);
            log.info("User table all rows deleted");
        } catch (SQLException e) {
            String dbError = "Failed delete all : " + e.getMessage();
            log.warn(dbError);
            throw new SQLite3Exception(dbError);
        }
    }

    @Override
    public void deleteById(Long id) {
        try (DBConnector dbConnector = new DBConnector();
             PreparedStatement preparedStatement = dbConnector.getConnection().prepareStatement(SQLStatement.prDeleteUserById)) {
            preparedStatement.setLong(1, id);
            if (preparedStatement.executeUpdate() <= 0) {
                log.warn("Could not delete user : " + id);
            } else {
                log.info("User deleted : " + id);
            }
        } catch (SQLException e) {
            String dbError = "Failed delete user by id : " + e.getMessage();
            log.warn(dbError);
            throw new SQLite3Exception(dbError);
        }
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    public boolean existsByUsername(String username) {
        return findByUsername(username).isPresent();
    }

    public boolean existsByEmail(String email) {
        return findByEmail(email).isPresent();
    }

    private User fillUserInfo(Long id, ResultSet resultSet) {
        User user = new User();
        try {
            user.setId(resultSet.getLong("ID"));
            user.setFirstName(resultSet.getString("FIRST_NAME"));
            user.setLastName(resultSet.getString("LAST_NAME"));
            user.setUsername(resultSet.getString("USERNAME"));
            user.setEmail(resultSet.getString("EMAIL"));
            user.setPassword(resultSet.getString("PASSWORD"));
            user.setRoles(findRolesById(id));
            return user;
        } catch (SQLException e) {
            String dbError = "Failed fill user info : " + e.getMessage();
            log.warn(dbError);
            throw new SQLite3Exception(dbError);
        }
    }

    public Optional<User> findByUsername(String username) {
        Optional<User> optionalUser = Optional.empty();
        try (DBConnector dbConnector = new DBConnector();
             PreparedStatement preparedStatement = dbConnector.getConnection().prepareStatement(SQLStatement.prQueryUserByUsername)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {

                    optionalUser = Optional.of(fillUserInfo(resultSet.getLong("ID"), resultSet));
                    log.info("query by username : " + optionalUser.orElse(null));
                } else {
                    log.warn("Could not found username : " + username);
                }
            }
            return optionalUser;
        } catch (SQLException e) {
            String dbError = "Failed find by username : " + e.getMessage();
            log.warn(dbError);
            throw new SQLite3Exception(dbError);
        }
    }

    public Optional<User> findByEmail(String email) {
        Optional<User> optionalUser = Optional.empty();
        try (DBConnector dbConnector = new DBConnector();
             PreparedStatement preparedStatement = dbConnector.getConnection().prepareStatement(SQLStatement.prQueryUserByEmail)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    optionalUser = Optional.of(fillUserInfo(resultSet.getLong("ID"), resultSet));
                    log.info("query by email : " + optionalUser.orElse(null));
                } else {
                    log.warn("Could not found email : " + email);
                }
            }
            return optionalUser;
        } catch (SQLException e) {
            String dbError = "Failed find by email : " + e.getMessage();
            log.warn(dbError);
            throw new SQLite3Exception(dbError);
        }
    }

    public Set<Role> findRolesById(Long id) {
        Set<Role> roles = new HashSet<>();
        try (DBConnector dbConnector = new DBConnector();
             PreparedStatement preparedStatement = dbConnector.getConnection().prepareStatement(SQLStatement.prQueryUserRolesById)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    roles.add(new Role(resultSet.getLong("ROLE_ID"), RoleType.valueOf(resultSet.getString("ROLE_TYPE"))));
                }
            }
            return roles;
        } catch (SQLException e) {
            String dbError = "Failed find user roles by id : " + e.getMessage();
            log.warn(dbError);
            throw new SQLite3Exception(dbError);
        }
    }

    public void saveUserRoles(Long id, Set<Role> roles) {
        try (DBConnector dbConnector = new DBConnector();
             PreparedStatement preparedStatement = dbConnector.getConnection().prepareStatement(SQLStatement.prAssignRoleToUser)) {
            for (Role role : roles) {
                preparedStatement.setLong(1, id);
                Optional<Role> foundRole = roleRepository.findByRoleType(role.getRoleType().name());
                long foundId = -1;
                if (foundRole.isPresent()) {
                    foundId = foundRole.get().getId();
                }
                preparedStatement.setLong(2, foundId);
                preparedStatement.executeUpdate();
                preparedStatement.clearParameters();
            }
        } catch (SQLException e) {
            String dbError = "Failed save user roles : " + e.getMessage();
            log.warn(dbError);
            throw new SQLite3Exception(dbError);
        }
    }
}
