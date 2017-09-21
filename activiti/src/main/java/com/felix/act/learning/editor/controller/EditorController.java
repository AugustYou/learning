package com.felix.act.learning.editor.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.felix.act.learning.editor.service.EditorService;
import com.felix.act.learning.exception.ErrorCode;
import com.felix.act.learning.exception.MyException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author : felix.
 * @createTime : 2017/9/21.
 */
@RestController
@RequestMapping("/service")
public class EditorController {

    @Autowired
    EditorService editorService;

    @ApiOperation(value = "获取页面模板", notes = "获取页面模板")
    @GetMapping("/editor/stencilset")
    public String getStencilset() {
        return editorService.getStencilset();
    }

    @ApiOperation(value = "获取新建model的信息", notes = "获取新建model的信息")
    @GetMapping("/model/{modelId}/json")
    public ObjectNode getEditorJson(@PathVariable String modelId) {
        Optional.ofNullable(modelId).orElseThrow(() -> new MyException(ErrorCode.ERROR_ARGS));
        return editorService.getEditorJson(modelId);
    }

    @ApiOperation(value = "保存model信息", notes = "保存model信息")
    @PutMapping("/model/{modelId}/save")
    public void saveModel(@PathVariable String modelId, String name, String description,
                          String json_xml, String svg_xml) {
        Optional.ofNullable(modelId).orElseThrow(() -> new MyException(ErrorCode.ERROR_ARGS));
        editorService.saveModel(modelId, name, description, json_xml, svg_xml);
    }
}
