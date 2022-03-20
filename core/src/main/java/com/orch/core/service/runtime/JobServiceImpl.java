package com.orch.core.service.runtime;

import com.orch.common.metadata.Task;
import com.orch.common.runtime.ExecutionResult;
import com.orch.common.runtime.Job;
import com.orch.common.runtime.WorkflowInstance;
import com.orch.core.dao.JobDAO;
import com.orch.core.service.metadata.TaskService;
import com.orch.core.service.utils.lock.LockService;
import com.orch.core.service.utils.id.IdGeneratorService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JobServiceImpl implements JobService {

    private WorkflowInstanceService workflowInstanceService;

    private TaskQueueService taskQueueService;

    private TaskService taskService;

    private LockService lockService;

    private JobDAO jobDAO;

    private IdGeneratorService idGeneratorService;

    @Override
    public Job getJobById(Integer jobId) {
        return null;
    }

    @Override
    public Job getJobByInstanceIdAndTaskName(Integer instanceId, String taskName) {
        return null;
    }

    @Override
    public void updateJobStatusById(Integer jobId, Job.JobStatus jobStatus) {

    }

    @Override
    public void updateJob(Job job) {

    }

    @Override
    public void submitJobExecutionResult(Job job, ExecutionResult result) {
        Task task = taskService.getTaskById(job.getTaskId());
        this.submitJobExecutionResult(job, result, task);
    }

    @Override
    //有异常抛出
    //同步任务提交执行结果，task无需重新查询
    public void submitJobExecutionResult(Job job, ExecutionResult result, Task task) {
        final int instanceId = job.getWorkflowInstanceId();
        Map<String, Object> outputData = result.getOutputData();

        //push到实例全局数据
        task.getPushParams().forEach(pushParameter -> {
            Object data = outputData.get(pushParameter.getSourceRef().getParamName());
            if(pushParameter.getSourceRef().getFields() != null){
                for(String field : pushParameter.getSourceRef().getFields())
                    data = ((Map<String, Object>)data).get(field);
            }
            Object globalValue = workflowInstanceService.getGlobalParamValue(instanceId, pushParameter.getTargetRef().getParamName());
            List<String> targetFields = pushParameter.getTargetRef().getFields();
            if(targetFields == null){
                globalValue = data;
            }else{
                int i;
                Object parent = globalValue;
                for(i = 0; i != targetFields.size()-1; i++)
                    parent = ((Map<String, Object>)parent).get(targetFields.get(i));
                ((Map<String, Object>)parent).put(targetFields.get(i), data);
            }
            workflowInstanceService.createGlobalParamInWorkflowInstance(instanceId, pushParameter.getTargetRef().getParamName(), globalValue);
        });

        //TODO: 把outputData按照task.outputParameters的格式保存

        //更新job数据
        job.setOutputData(outputData);
        job.setJobStatus(Job.JobStatus.COMPLETED);
        job.setEndTime(System.currentTimeMillis());
        this.updateJob(job);

        //decide下一个task
        this.decideNextJob(job);
    }

    @Override
    //异常抛出
    public void decideNextJob(Job job) {
        if(job.getNextTaskIds() == null || job.getNextTaskIds().isEmpty())
            return;
        final int instanceId = job.getWorkflowInstanceId();
        try {
            lockService.acquireLockForWorkflowInstance(instanceId);
            WorkflowInstance.WorkflowStatus status = workflowInstanceService.getStatusOfWorkflowInstance(instanceId);
            if (status == WorkflowInstance.WorkflowStatus.RUNNING) {
                //若在开始状态，将下一步的任务push进队列
                job.getNextTaskIds().forEach(nextTaskId -> {
                    taskQueueService.pushTaskToQueue(new TaskQueueElement(instanceId, nextTaskId));
                });
            } else if (status == WorkflowInstance.WorkflowStatus.PAUSED) {
                //若在暂停状态，则将下一步任务暂存于global变量
                List<Integer> taskIds = (List)workflowInstanceService.getGlobalParamValue(instanceId, "BREAK_POINT");
                List<Integer> nextTaskIds = job.getNextTaskIds();
                if(taskIds == null)
                    taskIds = new ArrayList<>();
                for(Integer id : nextTaskIds)
                    taskIds.add(id);
                taskIds.stream().distinct();
                workflowInstanceService.createGlobalParamInWorkflowInstance(instanceId, "BREAK_POINT", taskIds);
            }
        } finally {
            lockService.releaseLockForWorkflowInstance(instanceId);
        }
    }

    @Override
    public Job createJob(Job job) {
        if(jobDAO.needIdGenerator())
            //TODO id类型
            job.setId(idGeneratorService.generateId().intValue());
        return jobDAO.createJob(job);
    }
}
