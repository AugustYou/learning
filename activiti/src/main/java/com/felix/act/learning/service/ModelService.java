package com.felix.act.learning.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.felix.act.learning.dto.PageResultsDTO;
import com.felix.act.learning.exception.ErrorCode;
import com.felix.act.learning.exception.MyException;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author: felix.
 * @createTime: 2017/9/21.
 */
@Service
public class ModelService {

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    ObjectMapper objectMapper;

    public PageResultsDTO<Model> getMadelByPage(Integer page, Integer pageSize, String category) {
        PageResultsDTO result = new PageResultsDTO(page, pageSize);
        ModelQuery modelQuery = repositoryService.createModelQuery().latestVersion().orderByLastUpdateTime().desc();
        if (StringUtils.isNotBlank(category)) {
            modelQuery.modelCategory(category);
        }
        result.setTotalCount(modelQuery.count());
        result.setList(modelQuery.listPage(result.offset, result.pageSize));
        return result;
    }

    @Transactional(readOnly = false)
    public Model create(String name, String key, String desc, String category) {
        try {
            ObjectNode editorNode = objectMapper.createObjectNode();
            editorNode.put("id", "canvas");
            editorNode.put("resourceId", "canvas");
            ObjectNode properties = objectMapper.createObjectNode();
            properties.put("process_author", "taichi");
            editorNode.put("properties", properties);
            ObjectNode stencilset = objectMapper.createObjectNode();
            stencilset.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
            editorNode.put("stencilset", stencilset);

            Model model = repositoryService.newModel();
            desc = StringUtils.defaultString(desc);
            model.setKey(StringUtils.defaultString(key));
            model.setName(name);
            model.setCategory(category);
            model.setVersion(Integer.parseInt(String.valueOf(repositoryService.createModelQuery().modelKey(model.getKey()).count()+1)));

            ObjectNode modelObjectNode = objectMapper.createObjectNode();
            modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, model.getVersion());
            modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, desc);
            model.setMetaInfo(modelObjectNode.toString());

            repositoryService.saveModel(model);
            repositoryService.addModelEditorSource(model.getId(), editorNode.toString().getBytes("utf-8"));
            return model;
        } catch (UnsupportedEncodingException e) {
            throw new MyException(ErrorCode.FAIL, "modelService create model fail");
        }
    }

    @Transactional(readOnly = false)
    public void delete(String id) {
        repositoryService.deleteModel(id);
    }

    @Transactional(readOnly = false)
    public void updateCategory(String id, String category) {
        Model modelData = repositoryService.getModel(id);
        modelData.setCategory(category);
        repositoryService.saveModel(modelData);
    }

    @Transactional(readOnly = false)
    public String deploy(String id) {
        String message = "";
        try {
            // 获取模型
            Model model = repositoryService.getModel(id);
            ObjectNode objectNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(model.getId()));
            BpmnModel bpmnModel = new BpmnJsonConverter().convertToBpmnModel(objectNode);

//            byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(bpmnModel);
            String processName = model.getName();
            if (!StringUtils.endsWith(processName, ".bpmn20.xml")){
                processName += ".bpmn20.xml";
            }
            // 部署流程
            Deployment deployment = repositoryService.createDeployment().name(model.getName())
//                    .addString(processName, new String(bpmnBytes))
//                    .deploy();
                    .addBpmnModel(processName, bpmnModel).deploy();

            // 设置流程分类
            List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).list();
            if (list.size() == 0){
                throw new MyException(ErrorCode.MODEL_NOT_EXIST);
            }
            list.stream().forEach(processDefinition ->
                            repositoryService.setProcessDefinitionCategory(processDefinition.getId(), model.getCategory()));
        } catch (Exception e) {
            throw new MyException(ErrorCode.FAIL, "model service deploy model fail");
        }
        return message;
    }
}
