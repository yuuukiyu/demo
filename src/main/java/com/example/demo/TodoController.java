package com.example.demo;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.TodoItem;
import com.example.demo.entity.User;
import com.example.demo.repository.TodoRepository;
import com.example.demo.repository.UserRepository;

@Controller
public class TodoController {

	@PostMapping("/todo/delete")
	public String deleteTask(@RequestParam("id") Long id) {
	    todoRepository.deleteById(id);
	    return "redirect:/todo";
	}

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TodoRepository todoRepository;
    @PostMapping("/todo/add")
    public String addTask(@RequestParam String task,
                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate deadline,
                          Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username).orElseThrow();

        TodoItem item = new TodoItem();
        item.setTask(task);
        item.setDeadline(deadline);
        item.setUser(user); // ✅ この行が必要
        todoRepository.save(item);
        return "redirect:/todo";
    }
    @GetMapping("/todo")
    public String showTodoList(@RequestParam(name = "sort", defaultValue = "asc") String sort,
                               Model model, Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username).orElseThrow();

        List<TodoItem> todoList = todoRepository.findByUser(user);

        if ("desc".equals(sort)) {
            todoList.sort((a, b) -> b.getDeadline().compareTo(a.getDeadline()));
        } else {
            todoList.sort((a, b) -> a.getDeadline().compareTo(b.getDeadline()));
        }

        model.addAttribute("todoList", todoList);
        return "todo";
    }

    @PostMapping("/todo/update")
    public String updateTask(@RequestParam("id") Long id,
                             @RequestParam("task") String task,
                             @RequestParam("deadline") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate deadline,
                             Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username).orElseThrow();

        TodoItem item = todoRepository.findById(id).orElseThrow();
        if (item.getUser().getId().equals(user.getId())) {
            item.setTask(task);
            item.setDeadline(deadline);
            todoRepository.save(item);
        }

        return "redirect:/todo";
    }
}
