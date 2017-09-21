package com.felix.act.learning.controller;

import com.felix.act.learning.dto.PageResultsDTO;
import com.felix.act.learning.dto.RespDTO;
import com.felix.act.learning.exception.ErrorCode;
import com.felix.act.learning.exception.MyException;
import com.felix.act.learning.service.ModelService;
import com.felix.act.learning.utils.PageUtils;
import org.activiti.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

/**
 * @author: felix.
 * @createTime: 2017/9/21.
 */
@RestController
@RequestMapping("/model")
public class ModelController {

    @Autowired
    ModelService modelService;

    @GetMapping("")
    public RespDTO getModelsByPage(@RequestParam int page, @RequestParam int pageSize, @RequestParam(required = false) String category) {
        PageUtils.check(page, pageSize);
        PageResultsDTO<Model> result = modelService.getMadelByPage(page, pageSize, category);
        return RespDTO.onSuc(result);
    }

    @PostMapping("")
    public RespDTO createModel(@RequestParam String name, @RequestParam String key,
                               @RequestParam String desc, @RequestParam String category) {
        if (null == name || null == key || null == category) {
            throw new MyException(ErrorCode.ERROR_ARGS);
        }
        Model model = modelService.create(name, key, desc, category);
        return RespDTO.onSuc(model.getId());
    }

    @DeleteMapping("/{id}")
    public RespDTO delete(@PathVariable("id") String id) {
        Optional.ofNullable(id).orElseThrow(() -> new MyException(ErrorCode.ERROR_ARGS));
        modelService.delete(id);
        return RespDTO.onSuc(null);
    }

    @PutMapping("/{id}")
    public RespDTO updateCategory(@PathVariable("id") String id, @RequestParam String category) {
        if (null == id || null == category) {
            throw new MyException(ErrorCode.ERROR_ARGS);
        }
        modelService.updateCategory(id, category);
        return RespDTO.onSuc(null);
    }

    @PutMapping("/deployment/{id}")
    public RespDTO deploy(@PathVariable("id") String id) {
        modelService.deploy(id);
        return RespDTO.onSuc(null);
    }
}