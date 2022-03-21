package com.gtxc.patikacloneserver.repository;

/*
    Created by gt at 2:46 PM on Sunday, March 06, 2022.
    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.repository.
*/

import com.gtxc.patikacloneserver.exceptions.SQLite3Exception;
import com.gtxc.patikacloneserver.helper.DBConnector;
import com.gtxc.patikacloneserver.helper.SQLStatement;
import com.gtxc.patikacloneserver.model.Patika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PatikaRepository implements SimpleCrudRepository<Patika, Long> {

    private static final Logger log = LoggerFactory.getLogger(PatikaRepository.class);

    @Override
    public Optional<Patika> findById(Long id) {
        Optional<Patika> optionalPatika = Optional.empty();
        try (DBConnector dbConnector = new DBConnector();
             PreparedStatement preparedStatement = dbConnector.getConnection().prepareStatement(SQLStatement.prQueryPatikaById)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    optionalPatika = Optional.of(fillPatikaInfo(resultSet));
                    log.info("query by id : " + optionalPatika.orElse(null));
                } else {
                    log.warn("Could not found patika : " + id);
                }
            }
            return optionalPatika;
        } catch (SQLException e) {
            String dbError = "Failed find patika by id : " + id + " : " + e.getMessage();
            log.warn(dbError);
            throw new SQLite3Exception(dbError);
        }
    }

    @Override
    public List<Patika> findAll() {
        List<Patika> patikaList = new ArrayList<>();
        Patika patika;
        try (DBConnector dbConnector = new DBConnector();
             Statement statement = dbConnector.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(SQLStatement.queryAllPatikas)) {
            while (resultSet.next()) {
                patika = fillPatikaInfo(resultSet);
                patikaList.add(patika);
            }
            if (patikaList.isEmpty()) {
                log.warn("Could not found any patika");
            } else {
                log.info("query : " + patikaList);
            }
            return patikaList;
        } catch (SQLException e) {
            String dbError = "Failed find all patikas : " + e.getMessage();
            log.warn(dbError);
            throw new SQLite3Exception(dbError);
        }
    }

    @Override
    public Optional<Patika> save(Patika patika) {
        try (DBConnector dbConnector = new DBConnector();
            PreparedStatement preparedStatement = dbConnector.getConnection().prepareStatement(SQLStatement.prAddNewPatika)) {
            if (patika.getId() != null) {
                preparedStatement.setLong(1, patika.getId());
            } else {
                preparedStatement.setNull(1, java.sql.Types.NULL);
            }
            preparedStatement.setString(2, patika.getName());
            if (preparedStatement.executeUpdate() <= 0) {
                return Optional.empty();
            }
            Optional<Patika> saved = findByName(patika.getName());
            log.info("save : " + saved.orElse(null));
            return saved;
        } catch (SQLException e) {
            String dbError = "Failed save patika : " + patika + " : " + e.getMessage();
            log.warn(dbError);
            throw new SQLite3Exception(dbError);
        }
    }

    @Override
    public Long count() {
        long rowCount = 0;
        try (DBConnector dbConnector = new DBConnector();
             Statement statement = dbConnector.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(SQLStatement.countPatikaRows)) {
            if (resultSet.next()) {
                rowCount = resultSet.getLong(1);
            }
            log.info("Patika table row count : " + rowCount);
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
            statement.execute(SQLStatement.dropTable("patika"));
            statement.execute(SQLStatement.createPatikaTable);
            log.info("Patika table all rows deleted");
        } catch (SQLException e) {
            String dbError = "Failed delete all : " + e.getMessage();
            log.warn(dbError);
            throw new SQLite3Exception(dbError);
        }
    }

    @Override
    public void deleteById(Long id) {
        try (DBConnector dbConnector = new DBConnector();
             PreparedStatement preparedStatement = dbConnector.getConnection().prepareStatement(SQLStatement.prDeletePatikaById)) {
            preparedStatement.setLong(1, id);
            if (preparedStatement.executeUpdate() <= 0) {
                log.warn("Could not delete patika : " + id);
            } else {
                log.info("Patika deleted : " + id);
            }
        } catch (SQLException e) {
            String dbError = "Failed delete patika by id : " + e.getMessage();
            log.warn(dbError);
            throw new SQLite3Exception(dbError);
        }
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    public boolean existsByName(String name) {
        return findByName(name).isPresent();
    }

    private Patika fillPatikaInfo(ResultSet resultSet) {
        Patika patika = new Patika();
        try {
            patika.setId(resultSet.getLong("ID"));
            patika.setName(resultSet.getString("NAME"));
            return patika;
        } catch (SQLException e) {
            String dbError = "Failed fill patika info: " + e.getMessage();
            log.warn(dbError);
            throw new SQLite3Exception(dbError);
        }
    }

    public Optional<Patika> findByName(String name) {
        Optional<Patika> optionalPatika = Optional.empty();
        try (DBConnector dbConnector = new DBConnector();
             PreparedStatement preparedStatement = dbConnector.getConnection().prepareStatement(SQLStatement.prQueryPatikaByName)) {
            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    optionalPatika = Optional.of(fillPatikaInfo(resultSet));
                    log.info("query by patika name : " + optionalPatika.orElse(null));
                } else {
                    log.warn("Could not found patika : " + name);
                }
            }
            return optionalPatika;
        } catch (SQLException e) {
            String dbError = "Failed find by patika name : " + e.getMessage();
            log.warn(dbError);
            throw new SQLite3Exception(dbError);
        }
    }
}
