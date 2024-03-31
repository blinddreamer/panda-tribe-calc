package com.example.pandatribe.models.results;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AppraisalResult {
    List<AppraisalResultEntity> appraisals;
}
