package com.example.pkglab4.controller;

import com.example.pkglab4.model.req.CircleRequest;
import com.example.pkglab4.model.req.LineRequest;
import com.example.pkglab4.model.res.RasterResponse;
import com.example.pkglab4.service.RasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/raster")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RasterController {

    private final RasterService rasterService;

    @PostMapping("/dda")
    public RasterResponse rasterizeDDA(@RequestBody LineRequest request) {
        return rasterService.rasterizeDDA(request);
    }

    @PostMapping("/cda")
    public RasterResponse rasterizeCDA(@RequestBody LineRequest request) {
        return rasterService.rasterizeCDA(request);
    }

    @PostMapping("/bresenham/line")
    public RasterResponse rasterizeBresenhamLine(@RequestBody LineRequest request) {
        return rasterService.rasterizeBresenhamLine(request);
    }

    @PostMapping("/bresenham/circle")
    public RasterResponse rasterizeBresenhamCircle(@RequestBody CircleRequest request) {
        return rasterService.rasterizeBresenhamCircle(request);
    }
}