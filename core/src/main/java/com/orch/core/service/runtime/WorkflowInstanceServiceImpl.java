package com.orch.core.service.runtime;

import com.orch.common.metadata.Task;
import com.orch.common.runtime.*;
import com.orch.core.dao.WorkflowInstanceDAO;
import com.orch.core.service.thread.ExecutionException;
import com.orch.core.service.execute.ExecutorService;
import com.orch.core.service.metadata.TaskService;
import com.orch.core.service.utils.lock.LockService;
import com.orch.core.service.utils.id.IdGeneratorService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkflowInstanceServiceImpl implements WorkflowInstanceService {
    private LockService lockService;

    private TaskQueueService taskQueueService;

    private WorkflowInstanceDAO workflowInstanceDAO;

    private IdGeneratorService idGeneratorService;

    private TaskService taskService;

    private ExecutorService executorService;

    @Override
    public WorkflowInstance createInstanceWithWorkflowDef(WorkflowInstance instanceParam) {
        WorkflowInstance instance = createInstanceObject(instanceParam);
        instance.setWorkflowStatus(WorkflowInstance.WorkflowStatus.RUNNING);
        //TODO 将所有id改为Long类型
        if(workflowInstanceDAO.needIdGenerator())
            instance.setId(idGeneratorService.generateId().intValue());
        instance = workflowInstanceDAO.createWorkflowInstance(instance);
        taskQueueService.pushTaskToQueue(new TaskQueueElement(instance.getId(),instance.getStartTaskId()));
        return instance;
    }

    @Override
    public WorkflowInstanceResult createSynchronousInstance(WorkflowInstance instanceParam) {
        WorkflowInstance instance = createInstanceObject(instanceParam);
        WorkflowInstanceResult result = new WorkflowInstanceResult();
        try{
            executeInstance(instance);
            result.setSuccess(true);
            //TODO 写入结果数据
            result.setOutputData(instance.getOutputData());
        }catch (Exception e){
            result.setSuccess(false);
            result.setFailReason(e.getMessage());
        }
        return result;
    }

    private void executeInstance(WorkflowInstance instance){
        Integer taskId = instance.getStartTaskId();
        while(true){
            Task task = taskService.getTaskById(taskId);
            if("finish".equals(task.getTaskDefName()))
                break;
            //TODO 改为本地装填数据
            Job job = taskService.createJobForTask(task);

            Executor executor = executorService.getExecutorForType(job.getTaskDefType());
            if(executor == null)
                throw new ExecutionException("No executor for type [" + job.getTaskDefType() + "].");
            JobInfo jobInfo = task.getSystemTask() == true ? job : new JobProxy(job);

            ExecutionResult executionResult = new ExecutionResult();
            executionResult.setJobId(job.getId());
            executionResult.setStatus(ExecutionResult.ExecutionStatus.SUCCESSFUL);
            Map<String, Object> outputData = new HashMap<>();
            job.getOutputData().forEach((paramName, param) -> {
                outputData.put(paramName, null);
            });
            executionResult.setOutputData(outputData);
            do{
                job.setRetryTimes(job.getRetryTimes() + 1);
                job.setStartTime(System.currentTimeMillis());
                executor.execute(jobInfo, executionResult);
            }while(executionResult.getStatus() == ExecutionResult.ExecutionStatus.RETRY);
            //若执行失败，直接抛出
            if(executionResult.getStatus() == ExecutionResult.ExecutionStatus.FAILED)
                throw new ExecutionException("Failed when executing job [" + job.getId() + "].");
            //若成功，写入数据
            job.setOutputData(outputData);
            //TODO 推送实例全局变量

            job.setJobStatus(Job.JobStatus.COMPLETED);
            job.setEndTime(System.currentTimeMillis());
            if(job.getNextTaskIds() == null || job.getNextTaskIds().isEmpty())
                break;
            else if(job.getNextTaskIds().size() == 1)
                taskId = job.getNextTaskIds().get(0);
            else
                throw new ExecutionException("Parallel tasks are not supported for now!");
        }
    }

    //根据流程定义生成实例对象
    private WorkflowInstance createInstanceObject(WorkflowInstance instanceParam){
        return instanceParam;
    }

    @Override
    public void suspendWorkflowInstance(Integer workflowInstanceId) {
        try{
            lockService.acquireLockForWorkflowInstance(workflowInstanceId);
            updateStatusOfWorkflowInstance(workflowInstanceId, WorkflowInstance.WorkflowStatus.PAUSED);
        }finally {
            lockService.releaseLockForWorkflowInstance(workflowInstanceId);
        }
    }

    @Override
    public void continueWorkflowInstance(Integer workflowInstanceId) {
        try{
            lockService.acquireLockForWorkflowInstance(workflowInstanceId);
            List<Integer> taskIds = (List)this.getGlobalParamValue(workflowInstanceId, "BREAK_POINT");
            if(taskIds != null)
                taskIds.forEach(id -> taskQueueService.pushTaskToQueue(new TaskQueueElement(workflowInstanceId, id)));
            updateStatusOfWorkflowInstance(workflowInstanceId, WorkflowInstance.WorkflowStatus.RUNNING);
        }finally {
            lockService.releaseLockForWorkflowInstance(workflowInstanceId);
        }
    }

    @Override
    public void terminateWorkflowInstance(Integer workflowInstanceId) {

    }

    @Override
    public void failWorkflowInstance(Integer workflowInstanceId, String reason) {

    }

    @Override
    public void finishWorkflowInstance(Integer workflowInstanceId) {
        WorkflowInstance instance = workflowInstanceDAO.getWorkflowInstanceById(workflowInstanceId);
        this.loadWorkflowInstanceOutputData(instance);
        instance.setWorkflowStatus(WorkflowInstance.WorkflowStatus.COMPLETED);
        instance.setSuccess(true);

    }

    @Override
    public WorkflowInstance getWorkflowInstanceById(Integer workflowInstanceId) {
        return null;
    }

    private void loadWorkflowInstanceOutputData(WorkflowInstance instance){
        //从job中获取输出字段数据

    }

    @Override
    public WorkflowInstance.WorkflowStatus getStatusOfWorkflowInstance(Integer workflowInstanceId) {
        return null;
    }

    @Override
    public void updateStatusOfWorkflowInstance(Integer workflowInstanceId, WorkflowInstance.WorkflowStatus status) {

    }

    @Override
    public void createGlobalParamInWorkflowInstance(Integer workflowInstanceId, String paramName, Object value) {

    }

    @Override
    public Object getGlobalParamValue(Integer workflowInstance, String paramName) {
        return null;
    }

    @Override
    public void getInputDataOfWorkflowInstance(Integer workflowInstanceId) {

    }

    @Override
    public void updateInputDataOfWorkflowInstance(Integer workflowInstanceId, Map<String, Object> data) {

    }
}
