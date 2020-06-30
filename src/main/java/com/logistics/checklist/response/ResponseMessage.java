package com.logistics.checklist.response;

public class ResponseMessage {
    private boolean error;

    private String message;
  
    public ResponseMessage(boolean error, String message) {
      this.error = error;
      this.message = message;
    }
    
    public boolean getError() {
      return error;
    }
  
    public void setError(boolean error) {
      this.error = error;
    }

    public String getMessage() {
      return message;
    }
  
    public void setMessage(String message) {
      this.message = message;
    }
  
  }