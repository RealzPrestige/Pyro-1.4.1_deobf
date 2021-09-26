// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.util.colors;

import dev.nuker.pyro.deobfuscated.util.Timer;
import java.util.ArrayList;

public class SalRainbowUtil
{
    private ArrayList<Integer> CurrentRainbowIndexes;
    private ArrayList<Integer> RainbowArrayList;
    private Timer RainbowSpeed;
    private int m_Timer;
    
    public SalRainbowUtil(final int p_Timer) {
        this.CurrentRainbowIndexes = new ArrayList<Integer>();
        this.RainbowArrayList = new ArrayList<Integer>();
        this.RainbowSpeed = new Timer();
        this.m_Timer = p_Timer;
        for (int l_I = 0; l_I < 360; ++l_I) {
            this.RainbowArrayList.add(ColorUtil.GetRainbowColor((float)l_I, 90.0f, 50.0f, 1.0f).getRGB());
            this.CurrentRainbowIndexes.add(l_I);
        }
    }
    
    public int GetRainbowColorAt(int p_Index) {
        if (p_Index > this.CurrentRainbowIndexes.size() - 1) {
            p_Index = this.CurrentRainbowIndexes.size() - 1;
        }
        return this.RainbowArrayList.get(this.CurrentRainbowIndexes.get(p_Index));
    }
    
    public void SetTimer(final int p_NewTimer) {
        this.m_Timer = p_NewTimer;
    }
    
    public void OnRender() {
        if (this.RainbowSpeed.passed(this.m_Timer)) {
            this.RainbowSpeed.reset();
            this.MoveListToNextColor();
        }
    }
    
    private void MoveListToNextColor() {
        if (this.CurrentRainbowIndexes.isEmpty()) {
            return;
        }
        this.CurrentRainbowIndexes.remove(this.CurrentRainbowIndexes.get(0));
        int l_Index = this.CurrentRainbowIndexes.get(this.CurrentRainbowIndexes.size() - 1) + 1;
        if (l_Index >= this.RainbowArrayList.size() - 1) {
            l_Index = 0;
        }
        this.CurrentRainbowIndexes.add(l_Index);
    }
}
