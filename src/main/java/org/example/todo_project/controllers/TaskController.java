package org.example.todo_project.controllers;

import org.example.todo_project.models.Task;
import org.example.todo_project.repo.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TaskController {

    @Autowired
    TaskRepository taskRepository;

    @GetMapping("/")
    public String show(Model model) {
        model.addAttribute("tasks", taskRepository.findAll());
        return "index";
    }

    @PostMapping("/add")
    public String add(@RequestParam String title, @RequestParam(required = false) String description) {
        Task task = new Task(title, description, false);
        taskRepository.save(task);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        taskRepository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/status/{id}")
    public String changeStatus(@PathVariable Long id) {
        Task task = taskRepository.findById(id).get();
        task.setCompleted(!task.isCompleted());
        taskRepository.save(task);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Task task = taskRepository.findById(id).get();
        model.addAttribute("task", task);
        return "views/edit";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @RequestParam String title, @RequestParam String description) {
        Task task = taskRepository.findById(id).get();
        task.setTitle(title);
        task.setDescription(description);
        taskRepository.save(task);
        return "redirect:/";
    }
}