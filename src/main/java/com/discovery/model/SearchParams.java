package com.discovery.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchParams {
    private String origin;
    private String destination;
}
