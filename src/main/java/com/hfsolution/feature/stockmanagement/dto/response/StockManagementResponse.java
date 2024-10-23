package com.hfsolution.feature.stockmanagement.dto.response;




import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.hfsolution.app.dto.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockManagementResponse extends BaseResponse {

  @JsonInclude(Include.NON_NULL)
  private Object data;


}
