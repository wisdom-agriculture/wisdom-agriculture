package com.yaohy.intelligentfarmingbackend.pojo.dto.result;

import com.yaohy.intelligentfarmingbackend.pojo.domain.Operation;
import lombok.Data;

import java.util.List;

@Data
public class OperationListResult {

    private List<OperationResult> operationResultList;


}
