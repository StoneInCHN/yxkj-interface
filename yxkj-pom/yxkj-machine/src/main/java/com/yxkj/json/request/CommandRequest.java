package com.yxkj.json.request;


public class CommandRequest {

  private Long commandId;
  private Boolean isSuccess;
  private String extMsg;


  public Long getCommandId() {
    return commandId;
  }

  public void setCommandId(Long commandId) {
    this.commandId = commandId;
  }

  public Boolean getSuccess() {
    return isSuccess;
  }

  public void setSuccess(Boolean success) {
    isSuccess = success;
  }

		public String getExtMsg() {
				return extMsg;
		}

		public void setExtMsg(String extMsg) {
				this.extMsg = extMsg;
		}
}
