package com.gtxc.patikacloneserver.controller;

/*
    Created by gt at 7:14 PM on Sunday, March 06, 2022.
    Project: patika-clone-server, Package: com.gtxc.patikacloneserver.controller.
*/

import com.gtxc.patikacloneserver.model.Patika;
import com.gtxc.patikacloneserver.service.PatikaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600L)
@RestController
@RequestMapping("/api/patikas")
public class PatikaController {

    private final PatikaService patikaService;

    @Autowired
    public PatikaController(PatikaService patikaService) {
        this.patikaService = patikaService;
    }

    @GetMapping("/")
    public @ResponseBody List<Patika> getAllPatikas() {
        return patikaService.getAll();
    }

    @GetMapping("/{id}")
    public @ResponseBody Patika getPatikaById(@PathVariable("id") Long id) {
        return patikaService.getById(id);
    }

    @PostMapping("/")
    public @ResponseBody List<Patika> addNewPatika(@RequestBody Patika patika) {
        patikaService.addNew(patika);
        return getAllPatikas();
    }

    @DeleteMapping("/{id}")
    public @ResponseBody List<Patika> removePatika(@PathVariable("id") Long id) {
        patikaService.removeById(id);
        return getAllPatikas();
    }

    @DeleteMapping("/remove-all")
    public @ResponseBody List<Patika> removePatikas() {
        patikaService.removeAll();
        return getAllPatikas();
    }

    @PutMapping("/{id}")
    public @ResponseBody Patika updatePatika(@RequestBody Patika patika, @PathVariable("id") Long id) {
        return patikaService.update(patika, id);
    }
}
