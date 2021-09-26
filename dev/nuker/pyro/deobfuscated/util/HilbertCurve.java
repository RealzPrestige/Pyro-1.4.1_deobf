// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.util;

import java.util.ArrayList;
import java.util.List;

public class HilbertCurve
{
    public static Point fromD(final int n, final int d) {
        final Point p = new Point(0, 0);
        int t = d;
        for (int s = 1; s < n; s <<= 1) {
            final boolean rx = (t & 0x2) != 0x0;
            final boolean ry = ((t ^ (rx ? 1 : 0)) & 0x1) != 0x0;
            p.rot(s, rx, ry);
            final Point point = p;
            point.x += (rx ? s : 0);
            final Point point2 = p;
            point2.y += (ry ? s : 0);
            t >>>= 2;
        }
        return p;
    }
    
    public static List<Point> getPointsForCurve(final int n) {
        final List<Point> points = new ArrayList<Point>();
        for (int d = 0; d < n * n; ++d) {
            final Point p = fromD(n, d);
            points.add(p);
        }
        return points;
    }
    
    public static class Point
    {
        public int x;
        public int y;
        
        public Point(final int x, final int y) {
            this.x = x;
            this.y = y;
        }
        
        @Override
        public String toString() {
            return "(" + this.x + ", " + this.y + ")";
        }
        
        public void rot(final int n, final boolean rx, final boolean ry) {
            if (!ry) {
                if (rx) {
                    this.x = n - 1 - this.x;
                    this.y = n - 1 - this.y;
                }
                final int t = this.x;
                this.x = this.y;
                this.y = t;
            }
        }
        
        public int calcD(final int n) {
            int d = 0;
            for (int s = n >>> 1; s > 0; s >>>= 1) {
                final boolean rx = (this.x & s) != 0x0;
                final boolean ry = (this.y & s) != 0x0;
                d += s * s * ((rx ? 3 : 0) ^ (ry ? 1 : 0));
                this.rot(s, rx, ry);
            }
            return d;
        }
    }
}
