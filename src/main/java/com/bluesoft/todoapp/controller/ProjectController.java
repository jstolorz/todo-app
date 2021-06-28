package com.bluesoft.todoapp.controller;

import com.bluesoft.todoapp.logic.ProjectService;
import com.bluesoft.todoapp.model.Project;
import com.bluesoft.todoapp.model.ProjectSteps;
import com.bluesoft.todoapp.model.projection.ProjectWriteModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
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
        model.addAttribute("message","Dodano projekt!");
        return "projects";
    }

    @PostMapping(params = "addStep")
    String addProjectStep(@ModelAttribute("project") ProjectWriteModel current){
        current.getSteps().add(new ProjectSteps());
        return "projects";
    }

    @ModelAttribute("projects")
    List<Project> getProjects(){
        return service.readAll();
    }
}
