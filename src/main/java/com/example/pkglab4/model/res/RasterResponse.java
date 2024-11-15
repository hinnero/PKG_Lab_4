package com.example.pkglab4.model.res;

import com.example.pkglab4.model.Point;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RasterResponse {
    private List<Point> points;
    private long computationTimeMillis;
}