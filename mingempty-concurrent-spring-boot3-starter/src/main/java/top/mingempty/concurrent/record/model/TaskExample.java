package top.mingempty.concurrent.record.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TaskExample() {
        oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andTaskIdIsNull() {
            addCriterion("task_id is null");
            return (Criteria) this;
        }

        public Criteria andTaskIdIsNotNull() {
            addCriterion("task_id is not null");
            return (Criteria) this;
        }

        public Criteria andTaskIdEqualTo(String value) {
            addCriterion("task_id =", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdNotEqualTo(String value) {
            addCriterion("task_id <>", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdGreaterThan(String value) {
            addCriterion("task_id >", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdGreaterThanOrEqualTo(String value) {
            addCriterion("task_id >=", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdLessThan(String value) {
            addCriterion("task_id <", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdLessThanOrEqualTo(String value) {
            addCriterion("task_id <=", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdLike(String value) {
            addCriterion("task_id like", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdNotLike(String value) {
            addCriterion("task_id not like", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdIn(List<String> values) {
            addCriterion("task_id in", values, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdNotIn(List<String> values) {
            addCriterion("task_id not in", values, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdBetween(String value1, String value2) {
            addCriterion("task_id between", value1, value2, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdNotBetween(String value1, String value2) {
            addCriterion("task_id not between", value1, value2, "taskId");
            return (Criteria) this;
        }

        public Criteria andPoolNameIsNull() {
            addCriterion("pool_name is null");
            return (Criteria) this;
        }

        public Criteria andPoolNameIsNotNull() {
            addCriterion("pool_name is not null");
            return (Criteria) this;
        }

        public Criteria andPoolNameEqualTo(String value) {
            addCriterion("pool_name =", value, "poolName");
            return (Criteria) this;
        }

        public Criteria andPoolNameNotEqualTo(String value) {
            addCriterion("pool_name <>", value, "poolName");
            return (Criteria) this;
        }

        public Criteria andPoolNameGreaterThan(String value) {
            addCriterion("pool_name >", value, "poolName");
            return (Criteria) this;
        }

        public Criteria andPoolNameGreaterThanOrEqualTo(String value) {
            addCriterion("pool_name >=", value, "poolName");
            return (Criteria) this;
        }

        public Criteria andPoolNameLessThan(String value) {
            addCriterion("pool_name <", value, "poolName");
            return (Criteria) this;
        }

        public Criteria andPoolNameLessThanOrEqualTo(String value) {
            addCriterion("pool_name <=", value, "poolName");
            return (Criteria) this;
        }

        public Criteria andPoolNameLike(String value) {
            addCriterion("pool_name like", value, "poolName");
            return (Criteria) this;
        }

        public Criteria andPoolNameNotLike(String value) {
            addCriterion("pool_name not like", value, "poolName");
            return (Criteria) this;
        }

        public Criteria andPoolNameIn(List<String> values) {
            addCriterion("pool_name in", values, "poolName");
            return (Criteria) this;
        }

        public Criteria andPoolNameNotIn(List<String> values) {
            addCriterion("pool_name not in", values, "poolName");
            return (Criteria) this;
        }

        public Criteria andPoolNameBetween(String value1, String value2) {
            addCriterion("pool_name between", value1, value2, "poolName");
            return (Criteria) this;
        }

        public Criteria andPoolNameNotBetween(String value1, String value2) {
            addCriterion("pool_name not between", value1, value2, "poolName");
            return (Criteria) this;
        }

        public Criteria andTaskClassIsNull() {
            addCriterion("task_class is null");
            return (Criteria) this;
        }

        public Criteria andTaskClassIsNotNull() {
            addCriterion("task_class is not null");
            return (Criteria) this;
        }

        public Criteria andTaskClassEqualTo(String value) {
            addCriterion("task_class =", value, "taskClass");
            return (Criteria) this;
        }

        public Criteria andTaskClassNotEqualTo(String value) {
            addCriterion("task_class <>", value, "taskClass");
            return (Criteria) this;
        }

        public Criteria andTaskClassGreaterThan(String value) {
            addCriterion("task_class >", value, "taskClass");
            return (Criteria) this;
        }

        public Criteria andTaskClassGreaterThanOrEqualTo(String value) {
            addCriterion("task_class >=", value, "taskClass");
            return (Criteria) this;
        }

        public Criteria andTaskClassLessThan(String value) {
            addCriterion("task_class <", value, "taskClass");
            return (Criteria) this;
        }

        public Criteria andTaskClassLessThanOrEqualTo(String value) {
            addCriterion("task_class <=", value, "taskClass");
            return (Criteria) this;
        }

        public Criteria andTaskClassLike(String value) {
            addCriterion("task_class like", value, "taskClass");
            return (Criteria) this;
        }

        public Criteria andTaskClassNotLike(String value) {
            addCriterion("task_class not like", value, "taskClass");
            return (Criteria) this;
        }

        public Criteria andTaskClassIn(List<String> values) {
            addCriterion("task_class in", values, "taskClass");
            return (Criteria) this;
        }

        public Criteria andTaskClassNotIn(List<String> values) {
            addCriterion("task_class not in", values, "taskClass");
            return (Criteria) this;
        }

        public Criteria andTaskClassBetween(String value1, String value2) {
            addCriterion("task_class between", value1, value2, "taskClass");
            return (Criteria) this;
        }

        public Criteria andTaskClassNotBetween(String value1, String value2) {
            addCriterion("task_class not between", value1, value2, "taskClass");
            return (Criteria) this;
        }

        public Criteria andTaskStatusIsNull() {
            addCriterion("task_status is null");
            return (Criteria) this;
        }

        public Criteria andTaskStatusIsNotNull() {
            addCriterion("task_status is not null");
            return (Criteria) this;
        }

        public Criteria andTaskStatusEqualTo(String value) {
            addCriterion("task_status =", value, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskStatusNotEqualTo(String value) {
            addCriterion("task_status <>", value, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskStatusGreaterThan(String value) {
            addCriterion("task_status >", value, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskStatusGreaterThanOrEqualTo(String value) {
            addCriterion("task_status >=", value, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskStatusLessThan(String value) {
            addCriterion("task_status <", value, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskStatusLessThanOrEqualTo(String value) {
            addCriterion("task_status <=", value, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskStatusLike(String value) {
            addCriterion("task_status like", value, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskStatusNotLike(String value) {
            addCriterion("task_status not like", value, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskStatusIn(List<String> values) {
            addCriterion("task_status in", values, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskStatusNotIn(List<String> values) {
            addCriterion("task_status not in", values, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskStatusBetween(String value1, String value2) {
            addCriterion("task_status between", value1, value2, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andTaskStatusNotBetween(String value1, String value2) {
            addCriterion("task_status not between", value1, value2, "taskStatus");
            return (Criteria) this;
        }

        public Criteria andRecordTimeIsNull() {
            addCriterion("record_time is null");
            return (Criteria) this;
        }

        public Criteria andRecordTimeIsNotNull() {
            addCriterion("record_time is not null");
            return (Criteria) this;
        }

        public Criteria andRecordTimeEqualTo(LocalDateTime value) {
            addCriterion("record_time =", value, "recordTime");
            return (Criteria) this;
        }

        public Criteria andRecordTimeNotEqualTo(LocalDateTime value) {
            addCriterion("record_time <>", value, "recordTime");
            return (Criteria) this;
        }

        public Criteria andRecordTimeGreaterThan(LocalDateTime value) {
            addCriterion("record_time >", value, "recordTime");
            return (Criteria) this;
        }

        public Criteria andRecordTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("record_time >=", value, "recordTime");
            return (Criteria) this;
        }

        public Criteria andRecordTimeLessThan(LocalDateTime value) {
            addCriterion("record_time <", value, "recordTime");
            return (Criteria) this;
        }

        public Criteria andRecordTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("record_time <=", value, "recordTime");
            return (Criteria) this;
        }

        public Criteria andRecordTimeIn(List<LocalDateTime> values) {
            addCriterion("record_time in", values, "recordTime");
            return (Criteria) this;
        }

        public Criteria andRecordTimeNotIn(List<LocalDateTime> values) {
            addCriterion("record_time not in", values, "recordTime");
            return (Criteria) this;
        }

        public Criteria andRecordTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("record_time between", value1, value2, "recordTime");
            return (Criteria) this;
        }

        public Criteria andRecordTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("record_time not between", value1, value2, "recordTime");
            return (Criteria) this;
        }

        public Criteria andCompletionTimeIsNull() {
            addCriterion("completion_time is null");
            return (Criteria) this;
        }

        public Criteria andCompletionTimeIsNotNull() {
            addCriterion("completion_time is not null");
            return (Criteria) this;
        }

        public Criteria andCompletionTimeEqualTo(LocalDateTime value) {
            addCriterion("completion_time =", value, "completionTime");
            return (Criteria) this;
        }

        public Criteria andCompletionTimeNotEqualTo(LocalDateTime value) {
            addCriterion("completion_time <>", value, "completionTime");
            return (Criteria) this;
        }

        public Criteria andCompletionTimeGreaterThan(LocalDateTime value) {
            addCriterion("completion_time >", value, "completionTime");
            return (Criteria) this;
        }

        public Criteria andCompletionTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("completion_time >=", value, "completionTime");
            return (Criteria) this;
        }

        public Criteria andCompletionTimeLessThan(LocalDateTime value) {
            addCriterion("completion_time <", value, "completionTime");
            return (Criteria) this;
        }

        public Criteria andCompletionTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("completion_time <=", value, "completionTime");
            return (Criteria) this;
        }

        public Criteria andCompletionTimeIn(List<LocalDateTime> values) {
            addCriterion("completion_time in", values, "completionTime");
            return (Criteria) this;
        }

        public Criteria andCompletionTimeNotIn(List<LocalDateTime> values) {
            addCriterion("completion_time not in", values, "completionTime");
            return (Criteria) this;
        }

        public Criteria andCompletionTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("completion_time between", value1, value2, "completionTime");
            return (Criteria) this;
        }

        public Criteria andCompletionTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("completion_time not between", value1, value2, "completionTime");
            return (Criteria) this;
        }

        public Criteria andTraceIdIsNull() {
            addCriterion("trace_id is null");
            return (Criteria) this;
        }

        public Criteria andTraceIdIsNotNull() {
            addCriterion("trace_id is not null");
            return (Criteria) this;
        }

        public Criteria andTraceIdEqualTo(String value) {
            addCriterion("trace_id =", value, "traceId");
            return (Criteria) this;
        }

        public Criteria andTraceIdNotEqualTo(String value) {
            addCriterion("trace_id <>", value, "traceId");
            return (Criteria) this;
        }

        public Criteria andTraceIdGreaterThan(String value) {
            addCriterion("trace_id >", value, "traceId");
            return (Criteria) this;
        }

        public Criteria andTraceIdGreaterThanOrEqualTo(String value) {
            addCriterion("trace_id >=", value, "traceId");
            return (Criteria) this;
        }

        public Criteria andTraceIdLessThan(String value) {
            addCriterion("trace_id <", value, "traceId");
            return (Criteria) this;
        }

        public Criteria andTraceIdLessThanOrEqualTo(String value) {
            addCriterion("trace_id <=", value, "traceId");
            return (Criteria) this;
        }

        public Criteria andTraceIdLike(String value) {
            addCriterion("trace_id like", value, "traceId");
            return (Criteria) this;
        }

        public Criteria andTraceIdNotLike(String value) {
            addCriterion("trace_id not like", value, "traceId");
            return (Criteria) this;
        }

        public Criteria andTraceIdIn(List<String> values) {
            addCriterion("trace_id in", values, "traceId");
            return (Criteria) this;
        }

        public Criteria andTraceIdNotIn(List<String> values) {
            addCriterion("trace_id not in", values, "traceId");
            return (Criteria) this;
        }

        public Criteria andTraceIdBetween(String value1, String value2) {
            addCriterion("trace_id between", value1, value2, "traceId");
            return (Criteria) this;
        }

        public Criteria andTraceIdNotBetween(String value1, String value2) {
            addCriterion("trace_id not between", value1, value2, "traceId");
            return (Criteria) this;
        }

        public Criteria andSpanIdIsNull() {
            addCriterion("span_id is null");
            return (Criteria) this;
        }

        public Criteria andSpanIdIsNotNull() {
            addCriterion("span_id is not null");
            return (Criteria) this;
        }

        public Criteria andSpanIdEqualTo(String value) {
            addCriterion("span_id =", value, "spanId");
            return (Criteria) this;
        }

        public Criteria andSpanIdNotEqualTo(String value) {
            addCriterion("span_id <>", value, "spanId");
            return (Criteria) this;
        }

        public Criteria andSpanIdGreaterThan(String value) {
            addCriterion("span_id >", value, "spanId");
            return (Criteria) this;
        }

        public Criteria andSpanIdGreaterThanOrEqualTo(String value) {
            addCriterion("span_id >=", value, "spanId");
            return (Criteria) this;
        }

        public Criteria andSpanIdLessThan(String value) {
            addCriterion("span_id <", value, "spanId");
            return (Criteria) this;
        }

        public Criteria andSpanIdLessThanOrEqualTo(String value) {
            addCriterion("span_id <=", value, "spanId");
            return (Criteria) this;
        }

        public Criteria andSpanIdLike(String value) {
            addCriterion("span_id like", value, "spanId");
            return (Criteria) this;
        }

        public Criteria andSpanIdNotLike(String value) {
            addCriterion("span_id not like", value, "spanId");
            return (Criteria) this;
        }

        public Criteria andSpanIdIn(List<String> values) {
            addCriterion("span_id in", values, "spanId");
            return (Criteria) this;
        }

        public Criteria andSpanIdNotIn(List<String> values) {
            addCriterion("span_id not in", values, "spanId");
            return (Criteria) this;
        }

        public Criteria andSpanIdBetween(String value1, String value2) {
            addCriterion("span_id between", value1, value2, "spanId");
            return (Criteria) this;
        }

        public Criteria andSpanIdNotBetween(String value1, String value2) {
            addCriterion("span_id not between", value1, value2, "spanId");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(LocalDateTime value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(LocalDateTime value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(LocalDateTime value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(LocalDateTime value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<LocalDateTime> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<LocalDateTime> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(LocalDateTime value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(LocalDateTime value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(LocalDateTime value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(LocalDateTime value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<LocalDateTime> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<LocalDateTime> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorIsNull() {
            addCriterion("create_operator is null");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorIsNotNull() {
            addCriterion("create_operator is not null");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorEqualTo(String value) {
            addCriterion("create_operator =", value, "createOperator");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorNotEqualTo(String value) {
            addCriterion("create_operator <>", value, "createOperator");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorGreaterThan(String value) {
            addCriterion("create_operator >", value, "createOperator");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorGreaterThanOrEqualTo(String value) {
            addCriterion("create_operator >=", value, "createOperator");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorLessThan(String value) {
            addCriterion("create_operator <", value, "createOperator");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorLessThanOrEqualTo(String value) {
            addCriterion("create_operator <=", value, "createOperator");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorLike(String value) {
            addCriterion("create_operator like", value, "createOperator");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorNotLike(String value) {
            addCriterion("create_operator not like", value, "createOperator");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorIn(List<String> values) {
            addCriterion("create_operator in", values, "createOperator");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorNotIn(List<String> values) {
            addCriterion("create_operator not in", values, "createOperator");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorBetween(String value1, String value2) {
            addCriterion("create_operator between", value1, value2, "createOperator");
            return (Criteria) this;
        }

        public Criteria andCreateOperatorNotBetween(String value1, String value2) {
            addCriterion("create_operator not between", value1, value2, "createOperator");
            return (Criteria) this;
        }

        public Criteria andUpdateOperatorIsNull() {
            addCriterion("update_operator is null");
            return (Criteria) this;
        }

        public Criteria andUpdateOperatorIsNotNull() {
            addCriterion("update_operator is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateOperatorEqualTo(String value) {
            addCriterion("update_operator =", value, "updateOperator");
            return (Criteria) this;
        }

        public Criteria andUpdateOperatorNotEqualTo(String value) {
            addCriterion("update_operator <>", value, "updateOperator");
            return (Criteria) this;
        }

        public Criteria andUpdateOperatorGreaterThan(String value) {
            addCriterion("update_operator >", value, "updateOperator");
            return (Criteria) this;
        }

        public Criteria andUpdateOperatorGreaterThanOrEqualTo(String value) {
            addCriterion("update_operator >=", value, "updateOperator");
            return (Criteria) this;
        }

        public Criteria andUpdateOperatorLessThan(String value) {
            addCriterion("update_operator <", value, "updateOperator");
            return (Criteria) this;
        }

        public Criteria andUpdateOperatorLessThanOrEqualTo(String value) {
            addCriterion("update_operator <=", value, "updateOperator");
            return (Criteria) this;
        }

        public Criteria andUpdateOperatorLike(String value) {
            addCriterion("update_operator like", value, "updateOperator");
            return (Criteria) this;
        }

        public Criteria andUpdateOperatorNotLike(String value) {
            addCriterion("update_operator not like", value, "updateOperator");
            return (Criteria) this;
        }

        public Criteria andUpdateOperatorIn(List<String> values) {
            addCriterion("update_operator in", values, "updateOperator");
            return (Criteria) this;
        }

        public Criteria andUpdateOperatorNotIn(List<String> values) {
            addCriterion("update_operator not in", values, "updateOperator");
            return (Criteria) this;
        }

        public Criteria andUpdateOperatorBetween(String value1, String value2) {
            addCriterion("update_operator between", value1, value2, "updateOperator");
            return (Criteria) this;
        }

        public Criteria andUpdateOperatorNotBetween(String value1, String value2) {
            addCriterion("update_operator not between", value1, value2, "updateOperator");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}