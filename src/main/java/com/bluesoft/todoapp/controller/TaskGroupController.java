package com.bluesoft.todoapp.controller;

import com.bluesoft.todoapp.logic.TaskGroupService;
import com.bluesoft.todoapp.model.Task;
import com.bluesoft.todoapp.model.projection.GroupReadModel;
import com.bluesoft.todoapp.model.projection.GroupWriteModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/groups")
class TaskGroupController {

    private final TaskGroupService service;

    TaskGroupController(final TaskGroupService service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    String getAllGroups(Model model){
        model.addAttribute("group", new GroupWriteModel());
        return "groups";
    }

    @PostMapping(params = "addTask",produces = MediaType.TEXT_HTML_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String addTasks(@ModelAttribute("group") @Valid GroupWriteModel current){
        return "groups";
    }

    @PostMapping(produces = MediaType.TEXT_HTML_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String addGroup(
            @ModelAttribute("group") @Valid GroupWriteModel current,
            BindingResult bindingResult,
            Model model
    ){

        if(bindingResult.hasErrors()){
            return "groups";
        }

        service.createGroup(current);
        model.addAttribute("group",new GroupWriteModel());
        model.addAttribute("groups",getGroups());
        model.addAttribute("message","Dodano grupę!");
        return "groups";
    }

//    @PostMapping(params = "addTask")
//    String addGroupTask(
//            @ModelAttribute("group") GroupWriteModel current
//            ){
//        current.getTasks().add(new GroupTaskWriteModel());
//        return "groups";
//    }

    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<GroupReadModel>> readAllGroups(){
        return ResponseEntity.ok(service.readAll());
    }

    @ResponseBody
    @GetMapping( path = "/{id}/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<Task>> readAllTasksFromGroup(@PathVariable int id){
        return ResponseEntity.ok(service.findAllTaskFromGroupById(id));
    }

    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<GroupReadModel> createGroup(@RequestBody @Valid GroupWriteModel toCreate){
        final GroupReadModel group = service.createGroup(toCreate);
        return ResponseEntity.created(URI.create("/" + group.getId())).body(group);
    }

    @Transactional
    @ResponseBody
    @PatchMapping(path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> toggleGroup(@PathVariable int id){
        service.toggleGroup(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<String> handleIllegalStateException(IllegalStateException e){
        return ResponseEntity.badRequest().build();
    }

    @ModelAttribute("groups")
    List<GroupReadModel> getGroups(){
        return service.readAll();
    }

}
