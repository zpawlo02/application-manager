package com.pawelzielinski.applicationmanager.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PaginatedApplicationResponse {
    private List<Application> applicationList;
    private Long numberOfItems;
    private int numberOfPages;
}
