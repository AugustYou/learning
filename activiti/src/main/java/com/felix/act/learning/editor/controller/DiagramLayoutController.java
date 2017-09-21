package com.felix.act.learning.editor.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.felix.act.learning.editor.service.DiagramLayoutService;
import com.felix.act.learning.exception.ErrorCode;
import com.felix.act.learning.exception.MyException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author : felix.
 * @createTime : 2017/9/21.
 */
@RestController
public class DiagramLayoutController extends DiagramLayoutBaseController {

    @Autowired
    DiagramLayoutService diagramLayoutService;

    @ApiOperation(value = "", notes = "")
    @GetMapping("/process-instance/{processInstanceId}/highlights")
    public ObjectNode getHighlighted(@PathVariable String processInstanceId) {
        Optional.ofNullable(processInstanceId).orElseThrow(() -> new MyException(ErrorCode.ERROR_ARGS));
        return diagramLayoutService.getHighlighted(processInstanceId);
    }

    @ApiOperation(value = "", notes = "")
    @GetMapping("/process-instance/{processInstanceId}/diagram-layout")
    public ObjectNode getDiagramByPid(@PathVariable String processInstanceId) {
        Optional.ofNullable(processInstanceId).orElseThrow(() -> new MyException(ErrorCode.ERROR_ARGS));
        return getDiagramNode(processInstanceId, null);
    }

    @ApiOperation(value = "", notes = "")
    @GetMapping("/process-definition/{processDefinitionId}/diagram-layout")
    public ObjectNode getDiagramByPdid(@PathVariable String processDefinitionId) {
        Optional.ofNullable(processDefinitionId).orElseThrow(() -> new MyException(ErrorCode.ERROR_ARGS));
        return getDiagramNode(null, processDefinitionId);
    }
}
