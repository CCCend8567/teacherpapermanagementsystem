package com.example.teacherpaper.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class TeacherResponse {

    private Long id;
    private String name;
    private String intro;
    private String department;
    private List<PaperResponse> papers;
}