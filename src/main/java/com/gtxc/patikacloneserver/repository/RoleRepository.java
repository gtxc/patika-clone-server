package com.gtxc.patikacloneserver.repository;

/*
    Created by gt at 12:07 PM on Tuesday, March 08, 2022.
    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.repository.
*/

import com.gtxc.patikacloneserver.exceptions.SQLite3Exception;
import com.gtxc.patikacloneserver.helper.DBConnector;
import com.gtxc.patikacloneserver.helper.SQLStatement;
import com.gtxc.patikacloneserver.model.Role;
import com.gtxc.patikacloneserver.model.RoleType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class RoleRepository implements SimpleCrudRepository<Role, Long> {

    private static final Logger log = LoggerFactory.getLogger(RoleRepository.class);

    @Override
    public Optional<Role> findById(Long id) {
        Optional<Role> optionalRole = Optional.empty();
        try (DBConnector dbConnector = new DBConnector();
             PreparedStatement preparedStatement = dbConnector.getConnection().prepareStatement(SQLStatement.prQueryRoleById)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    optionalRole = Optional.of(fillRoleInfo(resultSet));
                    log.info("query by id : " + (optionalRole.orElse(null)));
                } else {
                    log.warn("Could not found role : " + id);
                }
            }
            return optionalRole;
        } catch (SQLException e) {
            String dbError = "Failed find role by id : " + id + " : " + e.getMessage();
            log.warn(dbError);
            throw new SQLite3Exception(dbError);
        }
    }

    @Override
    public List<Role> findAll() {
        List<Role> roleList = new ArrayList<>();
        Role role;
        try (DBConnector dbConnector = new DBConnector();
             Statement statement = dbConnector.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(SQLStatement.queryAllRoles)) {
            while (resultSet.next()) {
                role = fillRoleInfo(resultSet);
                roleList.add(role);
            }
            if (roleList.isEmpty()) {
                log.warn("Could not found any user");
            } else {
                log.info("query : " + roleList);
            }
            return roleList;
        } catch (SQLException e) {
            String dbError = "Failed find all roles : " + e.getMessage();
            log.warn(dbError);
            throw new SQLite3Exception(dbError);
        }
    }

    @Override
    public Optional<Role> save(Role role) {
        try (DBConnector dbConnector = new DBConnector();
             PreparedStatement preparedStatement = dbConnector.getConnection().prepareStatement(SQLStatement.prAddNewRole)) {
            if (role.getId() != null) {
                preparedStatement.setLong(1, role.getId());
            } else {
                preparedStatement.setNull(1, java.sql.Types.NULL);
            }
            preparedStatement.setString(2, role.getRoleType().name());
            if (preparedStatement.executeUpdate() <= 0) {
                return Optional.empty();
            }
            Optional<Role> saved = findByRoleType(role.getRoleType().name());
            log.info("save : " + saved.orElse(null));
            return saved;
        } catch (SQLException e) {
            String dbError = "Failed save role : " + role + " : " + e.getMessage();
            log.warn(dbError);
            throw new SQLite3Exception(dbError);
        }
    }

    @Override
    public Long count() {
        long rowCount = 0;
        try (DBConnector dbConnector = new DBConnector();
             Statement statement = dbConnector.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(SQLStatement.countRoleRows)) {
            if (resultSet.next()) {
                rowCount = resultSet.getLong(1);
            }
            log.info("Role table row count : " + rowCount);
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
            statement.execute(SQLStatement.dropTable("role"));
            statement.execute(SQLStatement.createRoleTable);
            log.info("Role table all rows deleted");
        } catch (SQLException e) {
            String dbError = "Failed delete all : " + e.getMessage();
            log.warn(dbError);
            throw new SQLite3Exception(dbError);
        }
    }

    @Override
    public void deleteById(Long id) {
        try (DBConnector dbConnector = new DBConnector();
             PreparedStatement preparedStatement = dbConnector.getConnection().prepareStatement(SQLStatement.prDeleteRoleById)) {
            preparedStatement.setLong(1, id);
            if (preparedStatement.executeUpdate() <= 0) {
                log.warn("Could not delete role : " + id);
            } else {
                log.info("Role deleted : " + id);
            }
        } catch (SQLException e) {
            String dbError = "Failed delete role by id : " + e.getMessage();
            log.warn(dbError);
            throw new SQLite3Exception(dbError);
        }
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    public boolean existsByRoleType(RoleType roleType) {
        return findByRoleType(roleType.name()).isPresent();
    }

    private Role fillRoleInfo(ResultSet resultSet) {
        Role role = new Role();
        try {
            role.setId(resultSet.getLong("ID"));
            role.setRoleType(RoleType.valueOf(resultSet.getString("ROLE_TYPE")));
            return role;
        } catch (SQLException e) {
            String dbError = "Failed fill user info : " + e.getMessage();
            log.warn(dbError);
            throw new SQLite3Exception(dbError);
        }
    }

    public Optional<Role> findByRoleType(String roleType) {
        Optional<Role> optionalRole = Optional.empty();
        try (DBConnector dbConnector = new DBConnector();
             PreparedStatement preparedStatement = dbConnector.getConnection().prepareStatement(SQLStatement.prQueryRoleByRoleType)) {
            preparedStatement.setString(1, roleType);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    optionalRole = Optional.of(fillRoleInfo(resultSet));
                    log.info("query by role type : " + optionalRole.orElse(null));
                } else {
                    log.warn("Could not found role : " + roleType);
                }
            }
            return optionalRole;
        } catch (SQLException e) {
            String dbError = "Failed find by role type : " + e.getMessage();
            log.warn(dbError);
            throw new SQLite3Exception(dbError);
        }
    }
}
