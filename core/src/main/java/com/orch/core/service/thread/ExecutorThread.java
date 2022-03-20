package com.orch.core.service.thread;

import com.orch.common.metadata.Task;
import com.orch.common.runtime.*;
import com.orch.core.service.runtime.TaskQueueElement;
import com.orch.core.service.execute.ExecutorService;
import com.orch.core.service.metadata.TaskService;
import com.orch.core.service.runtime.JobService;
import com.orch.core.service.runtime.WorkflowInstanceService;
import com.orch.core.service.utils.lock.LockService;

import java.util.*;

public class ExecutorThread implements Runnable{

    public static ExecutorService executorService;
    public static JobService jobService;
    public static TaskService taskService;
    public static WorkflowInstanceService workflowInstanceService;
    public static LockService lockService;

    private int instanceId;

    private int taskId;

    public ExecutorThread(int instanceId, int taskId) {
        this.instanceId = instanceId;
        this.taskId = taskId;
    }

    @Override
    public void run() {
        //若实例已被暂停，将taskId添加到实例全局变量的暂停点字段，然后return
        WorkflowInstance.WorkflowStatus status = null;
        try{
            lockService.acquireLockForWorkflowInstance(instanceId);
            status = workflowInstanceService.getStatusOfWorkflowInstance(instanceId);
            if(status == WorkflowInstance.WorkflowStatus.PAUSED){
                List<Integer> taskIds = (List<Integer>) workflowInstanceService.getGlobalParamValue(instanceId, "BREAK_POINT");
                if(taskIds == null)
                    taskIds = new ArrayList<>();
                if(!taskIds.contains(taskId))
                    taskIds.add(taskId);
                workflowInstanceService.createGlobalParamInWorkflowInstance(instanceId, "BREAK_POINT", taskIds);
            }
            return;
        }catch (Exception exception){
            workflowInstanceService.failWorkflowInstance(instanceId, "Failed when suspending workflow instance [" + instanceId + "].");
        }finally {
            lockService.releaseLockForWorkflowInstance(instanceId);
        }
        if(status != WorkflowInstance.WorkflowStatus.RUNNING)
            return;

        Task task = taskService.getTaskById(taskId);
        //执行任务task
        try{
            doRun(task, instanceId);
        }catch (Exception exception){
            workflowInstanceService.failWorkflowInstance(instanceId, exception.getMessage());
        }
    }

    private void doRun(Task task, Integer workflowInstanceId){
        Job job = null;
        try{
            /** 应转移至jobService
             *  创建对象前判断是否已经有job数据
             *  装载数据是只覆盖数据引用表达式, nextTaskIds重新复制
             */
            job = taskService.createJobForTask(task);
            job.setWorkflowInstanceId(workflowInstanceId);
            //TODO 装载input和executor参数，

            jobService.createJob(job);
        }catch (Exception exception){
            throw new ExecutionException("Failed when creating task [" + task.getTaskName() + "].");
        }

        Executor executor = executorService.getExecutorForType(job.getTaskDefType());
        if(executor == null)
            throw new ExecutionException("No executor for type [" + job.getTaskDefType() + "].");

        JobInfo jobInfo = executor.isSystemTaskExecutor() ? job : new JobProxy(job);

        ExecutionResult executionResult = new ExecutionResult();
        executionResult.setJobId(job.getId());
        executionResult.setStatus(ExecutionResult.ExecutionStatus.SUCCESSFUL);
        Map<String, Object> outputData = new HashMap<>();
        job.getOutputData().forEach((paramName, param) -> {
            outputData.put(paramName, null);
        });
        executionResult.setOutputData(outputData);
        //TODO 任务类中是否同步、是否系统任务弃用
        boolean sync = executor.isSynchronous();
        Job.JobStatus jobStatus = sync ?  Job.JobStatus.EXECUTING :  Job.JobStatus.WAITING;
        job.setJobStatus(jobStatus);
        jobService.updateJobStatusById(job.getId(), Job.JobStatus.EXECUTING);
        do{
            job.setRetryTimes(job.getRetryTimes() + 1);
            job.setStartTime(System.currentTimeMillis());
            executor.execute(jobInfo, executionResult);
        }while(executionResult.getStatus() == ExecutionResult.ExecutionStatus.RETRY);

        if(executionResult.getStatus() == ExecutionResult.ExecutionStatus.FAILED)
            throw new ExecutionException("Failed when executing job [" + job.getId() + "].");
        else if(sync){
            try{
                jobService.submitJobExecutionResult(job, executionResult, task);
            }catch (Exception exception){
                throw new ExecutionException("Failed when submitting result of job [" + job.getId() + "].");
            }
        }

    }
//    public void run(Job job){
////        //加锁， 若未获取到，策略？
////        Job job = jobQueueService.popFromExecuteQueue();
////        if(job == null)
////            throw new NoSuchDataException("Job");
//
//
//        //判断工作流实例是否被终止或暂停
//
//
//        //装填job输入数据
//        job.getInputData().forEach((paramName, param)->{
//            if(param.getValue() != null || param.getValueSource() == null)
//                return;
//            Job sourceJob = jobService.getJobByInstanceIdAndTaskname(job.getWorkflowInstanceId(), param.getValueSource().getTaskName());
//
//            Object sourceData = sourceJob.getOutputData();
//            List<String> sourceFields = param.getValueSource().getFields();
//            for(String field : sourceFields)
//                sourceData = ((Map<String, Object>)sourceData).get(field);
//
//            List<String> targetFields = param.getFields();
//            if(targetFields.size() == 1)
//                param.setValue(sourceData);
//            else{
//                Map<String, Object> targetContainer = (Map<String, Object>) job.getInputData().get(targetFields.get(0));
//                for(int i = 1; i != targetFields.size() - 1; i++)
//                    targetContainer = (Map<String, Object>) targetContainer.get(targetFields.get(i));
//                targetContainer.put(targetFields.get(targetFields.size()-1), sourceData);
//            }
//        });
//
//        //根据taskDefType获取对应的executor
//        Executor executor = executorService.getExecutorForType(job.getTaskDefType());
//        JobInfo jobInfo = job;
//        //创建ExecutorResult对象
//        ExecutionResult executionResult = new ExecutionResult();
//        executionResult.setJobId(job.getId());
//        executionResult.setStatus(ExecutionResult.ExecutionStatus.SUCCESSFUL);
//        Map<String, Object> outputData = new HashMap<>();
//        job.getOutputData().forEach((paramName, param) -> {
//            outputData.put(paramName, null);
//        });
//        executionResult.setOutputData(outputData);
//
//        if(job.getRetryTimes() == null)
//            job.setRetryTimes(0);
//
//        //任务同步执行
//        if(job.getSynchronous() == null || job.getSynchronous()){
//            //执行前将状态更新为 EXECUTING
//            jobService.updateJobStatusById(job.getId(), Job.JobStatus.EXECUTING);
//            do{
//                job.setRetryTimes(job.getRetryTimes() + 1);
//                job.setStartTime(System.currentTimeMillis());
//                executor.execute(jobInfo, executionResult);
//                job.setEndTime(System.currentTimeMillis());
//            }while(executionResult.getStatus() == ExecutionResult.ExecutionStatus.RETRY);
//            if(executionResult.getStatus() == ExecutionResult.ExecutionStatus.SUCCESSFUL){
//                //将执行结果写入job
//                //job.getOutputParams()
//            }
//        }
//
//
//
//
//
//        if(job.getSynchronous() == null || job.getSynchronous())
//            job.setJobStatus(Job.JobStatus.EXECUTING);
//        else
//            job.setJobStatus(Job.JobStatus.WAITING);
//        jobService.updateJobStatusById(job.getId(), job.getJobStatus());
//
//        //
//        do{
//            job.setRetryTimes(job.getRetryTimes() + 1);
//            job.setStartTime(System.currentTimeMillis());
//            executor.execute(jobInfo, executionResult);
//        }while(executionResult.getStatus() == ExecutionResult.ExecutionStatus.RETRY);
//
//
//        if(executionResult.getStatus() == ExecutionResult.ExecutionStatus.FAILED)
//            workflowInstanceService.failWorkflowInstance(job.getWorkflowInstanceId());
//
//    }




}
