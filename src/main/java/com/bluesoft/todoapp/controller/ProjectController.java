package com.bluesoft.todoapp.controller;

import com.bluesoft.todoapp.logic.ProjectService;
import com.bluesoft.todoapp.model.ProjectSteps;
import com.bluesoft.todoapp.model.projection.ProjectWriteModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @PostMapping(params = "addStep")
    String addProjectStep(@ModelAttribute("project") ProjectWriteModel current){
        current.getSteps().add(new ProjectSteps());
        return "projects";
    }

    @PostMapping
    String addProject(@ModelAttribute("project") ProjectWriteModel current, Model model){
        service.save(current);
        model.addAttribute("project",new ProjectWriteModel());
        model.addAttribute("message","Dodano projekt");
        return "projects";
    }
}
