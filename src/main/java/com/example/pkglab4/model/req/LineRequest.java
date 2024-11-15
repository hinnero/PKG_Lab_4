package com.example.pkglab4.model.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LineRequest {
    private int x0;
    private int y0;
    private int x1;
    private int y1;
}