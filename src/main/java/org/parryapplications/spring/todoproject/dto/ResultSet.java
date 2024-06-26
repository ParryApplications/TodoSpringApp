package org.parryapplications.spring.todoproject.dto;

public class ResultSet<T> {
    private T data;
    private int errorCount;
    private String errorMessage;
    private int exceptionCount;
    private String exceptionMessage;
    private int successCount;
    private String successMessage;

    public ResultSet() {
    }

    public ResultSet(int successCount, String successMessage) {
        this.successCount = successCount;
        this.successMessage = successMessage;
    }

    public ResultSet(T data, int successCount, String successMessage) {
        this.data = data;
        this.successCount = successCount;
        this.successMessage = successMessage;
    }

    public ResultSet(String errorMessage, int errorCount) {
        this.errorMessage = errorMessage;
        this.errorCount = errorCount;
    }

    public ResultSet(String errorMessage, int errorCount, T data) {
        this.errorMessage = errorMessage;
        this.errorCount = errorCount;
        this.data = data;
    }

    public ResultSet(String exceptionMessage, T data, int exceptionCount) {
        this.exceptionMessage = exceptionMessage;
        this.data = data;
        this.exceptionCount = exceptionCount;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getExceptionCount() {
        return exceptionCount;
    }

    public void setExceptionCount(int exceptionCount) {
        this.exceptionCount = exceptionCount;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    @Override
    public String toString() {
        return "ResultSet{" +
                "data=" + data +
                ", errorCount=" + errorCount +
                ", errorMessage='" + errorMessage + '\'' +
                ", exceptionCount=" + exceptionCount +
                ", exceptionMessage='" + exceptionMessage + '\'' +
                ", successCount=" + successCount +
                ", successMessage='" + successMessage + '\'' +
                '}';
    }
}
