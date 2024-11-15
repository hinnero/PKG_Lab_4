package com.example.pkglab4.service;

import com.example.pkglab4.model.Point;
import com.example.pkglab4.model.req.CircleRequest;
import com.example.pkglab4.model.req.LineRequest;
import com.example.pkglab4.model.res.RasterResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RasterService {

    public RasterResponse rasterizeDDA(LineRequest request) {
        long startTime = System.currentTimeMillis();

        List<Point> points = new ArrayList<>();

        int x0 = request.getX0();
        int y0 = request.getY0();
        int x1 = request.getX1();
        int y1 = request.getY1();

        int dx = x1 - x0;
        int dy = y1 - y0;

        int steps = Math.max(Math.abs(dx), Math.abs(dy));

        float xInc = dx / (float) steps;
        float yInc = dy / (float) steps;

        float x = x0;
        float y = y0;
        for (int i = 0; i <= steps; i++) {
            points.add(new Point(Math.round(x), Math.round(y)));
            x += xInc;
            y += yInc;
        }

        long endTime = System.currentTimeMillis();
        return new RasterResponse(points, endTime - startTime);
    }

    public RasterResponse rasterizeCDA(LineRequest request) {
        long startTime = System.currentTimeMillis();

        List<Point> points = new ArrayList<>();

        int x0 = request.getX0();
        int y0 = request.getY0();
        int x1 = request.getX1();
        int y1 = request.getY1();

        int dx = x1 - x0;
        int dy = y1 - y0;

        if (Math.abs(dy) <= Math.abs(dx)) {
            if (x0 > x1) {
                int temp = x0;
                x0 = x1;
                x1 = temp;
                temp = y0;
                y0 = y1;
                y1 = temp;
                dx = x1 - x0;
                dy = y1 - y0;
            }
            float m = dy / (float) dx;
            float b = y0 - m * x0;
            for (int x = x0; x <= x1; x++) {
                int y = Math.round(m * x + b);
                points.add(new Point(x, y));
            }
        } else {
            if (y0 > y1) {
                int temp = x0;
                x0 = x1;
                x1 = temp;
                temp = y0;
                y0 = y1;
                y1 = temp;
                dy = y1 - y0;
                dx = x1 - x0;
            }
            float m_inv = dx / (float) dy;
            float b_inv = x0 - m_inv * y0;
            for (int y = y0; y <= y1; y++) {
                int x = Math.round(m_inv * y + b_inv);
                points.add(new Point(x, y));
            }
        }

        long endTime = System.currentTimeMillis();
        return new RasterResponse(points, endTime - startTime);
    }

    public RasterResponse rasterizeBresenhamLine(LineRequest request) {
        long startTime = System.currentTimeMillis();

        List<Point> points = new ArrayList<>();

        int x0 = request.getX0();
        int y0 = request.getY0();
        int x1 = request.getX1();
        int y1 = request.getY1();

        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);

        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;

        int err = dx - dy;

        while (true) {
            points.add(new Point(x0, y0));
            if (x0 == x1 && y0 == y1) break;
            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x0 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y0 += sy;
            }
        }

        long endTime = System.currentTimeMillis();
        return new RasterResponse(points, endTime - startTime);
    }

    public RasterResponse rasterizeBresenhamCircle(CircleRequest request) {
        long startTime = System.currentTimeMillis();

        List<Point> points = new ArrayList<>();

        int x0 = request.getX0();
        int y0 = request.getY0();
        int radius = request.getRadius();

        int x = radius;
        int y = 0;
        int err = 1 - radius;  // Ошибка изначально устанавливается как 1 - radius

        while (x >= y) {
            // Добавляем все восемь симметричных точек
            points.add(new Point(x0 + x, y0 + y));
            points.add(new Point(x0 + y, y0 + x));
            points.add(new Point(x0 - y, y0 + x));
            points.add(new Point(x0 - x, y0 + y));
            points.add(new Point(x0 - x, y0 - y));
            points.add(new Point(x0 - y, y0 - x));
            points.add(new Point(x0 + y, y0 - x));
            points.add(new Point(x0 + x, y0 - y));

            y++;

            if (err <= 0) {
                err += 2 * y + 1;
            } else {
                x--;
                err += 2 * (y - x) + 1;
            }
        }

        long endTime = System.currentTimeMillis();
        return new RasterResponse(points, endTime - startTime);
    }

}