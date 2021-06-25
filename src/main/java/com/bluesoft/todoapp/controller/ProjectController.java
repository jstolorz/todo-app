package com.bluesoft.todoapp.controller;

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
}
