package com.bluesoft.todoapp.controller;

import com.bluesoft.todoapp.logic.ProjectService;
import com.bluesoft.todoapp.model.Project;
import com.bluesoft.todoapp.model.ProjectSteps;
import com.bluesoft.todoapp.model.projection.ProjectWriteModel;
import io.micrometer.core.annotation.Timed;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/projects")
class ProjectController {

    private final ProjectService service;

    ProjectController(final ProjectService service) {
        this.service = service;
    }


    @GetMapping
    String showProject(Model model){
        ProjectWriteModel writeModel = new ProjectWriteModel();
        writeModel.setDescription("To html");
        model.addAttribute("project", writeModel);
        return "projects";
    }

    @PostMapping
    String addProject(
            @ModelAttribute("project") @Valid ProjectWriteModel current,
            BindingResult bindingResult,
            Model model)
    {
        if(bindingResult.hasErrors()){
            return "projects";
        }

        service.save(current);
        model.addAttribute("project", new ProjectWriteModel());
        model.addAttribute("project", new ProjectWriteModel());
        model.addAttribute("message","Dodano projekt!");
        return "projects";
    }

    @PostMapping(params = "addStep")
    String addProjectStep(@ModelAttribute("project") ProjectWriteModel current){
        current.getSteps().add(new ProjectSteps());
        return "projects";
    }

    @PostMapping("/fake/{id}")
    String createGroupFake(
            @ModelAttribute("project") ProjectWriteModel current,
            Model model,
            @PathVariable int id,
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime deadline
    ){
        return createGroup(current, model, id, deadline);
    }

    @Timed(value = "project.create.group", histogram = true, percentiles = {0.5, 0.95, 0.99})
    @PostMapping("/{id}")
    String createGroup(
            @ModelAttribute("project") ProjectWriteModel current,
            Model model,
            @PathVariable int id,
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime deadline
            ){
        try{
            service.createGroup(id, deadline);
            model.addAttribute("message","Dodano grupe!");
        }catch(IllegalStateException | IllegalArgumentException e){
             model.addAttribute("message","Błąd podczas tworzenia grupy");
        }
        return "projects";
    }

    @ModelAttribute("projects")
    List<Project> getProjects(){
        return service.readAll();
    }
}
