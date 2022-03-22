package com.gtxc.patikacloneserver.service;

/*
    Created by gt at 5:50 PM on Sunday, March 06, 2022.
    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.service.
*/

import com.gtxc.patikacloneserver.exceptions.EntityNotFoundException;
import com.gtxc.patikacloneserver.model.Patika;
import com.gtxc.patikacloneserver.repository.PatikaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatikaService implements SimpleEntityService<Patika, Long> {

    public static final Logger log = LoggerFactory.getLogger(PatikaService.class);
    private final PatikaRepository patikaRepository;

    @Autowired
    public PatikaService(PatikaRepository patikaRepository) {
        this.patikaRepository = patikaRepository;
    }

    @Override
    public Patika getById(Long id) {
        return patikaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Could not found patika " + id
                ));
    }

    @Override
    public List<Patika> getAll() {
        List<Patika> patikas = patikaRepository.findAll();
        if (patikas.isEmpty()) {
            throw new EntityNotFoundException("Could not found any patika");
        }
        return patikas;
    }

    @Override
    public Patika addNew(Patika patika) {
        if (patika.getId() != null && patikaRepository.existsById(patika.getId())) {
            String unavailable = "Patika id '" + patika.getId() + "' is already taken";
            log.warn(unavailable);
            throw new IllegalArgumentException(unavailable);
        } else if (patika.getName() != null && patikaRepository.existsByName(patika.getName())) {
            String unavailable = "Patika name '" + patika.getName() + "' is already taken";
            log.warn(unavailable);
            throw new IllegalArgumentException(unavailable);
        }
        Patika newPatika = patikaRepository.save(patika);
        if (newPatika.getId() != null) {
            return newPatika;
        } else {
            String retrieveError = "Error while getting added patika : " + patika;
            log.warn(retrieveError);
            throw new EntityNotFoundException("Error while getting added patika");
        }
    }

    @Override
    public void removeById(Long id) {
        if (patikaRepository.existsById(id)) {
            patikaRepository.deleteById(id);
        }
    }

    @Override
    public void removeAll() {
        if (!patikaRepository.findAll().isEmpty()) {
            patikaRepository.deleteAll();
        }
    }

    @Override
    public Patika update(Patika patika, Long id) {
        Patika oldPatika = getById(id);
        oldPatika.setId(id);
        oldPatika.setName(patika.getName() != null && !patika.getName().isEmpty() ? patika.getName() : oldPatika.getName());
        removeById(id);
        return addNew(oldPatika);
    }

    public Patika getByName(String name) {
        return patikaRepository.findByName(name).orElse(null);
    }
}
