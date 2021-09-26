// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.managers;

import dev.nuker.pyro.deobfuscated.util.Timer;
import dev.nuker.pyro.deobfuscated.main.Pyro;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;

public class NotificationManager
{
    public final List<Notification> Notifications;
    
    public NotificationManager() {
        this.Notifications = new CopyOnWriteArrayList<Notification>();
    }
    
    public void AddNotification(final String p_Title, final String p_Description) {
        this.Notifications.add(new Notification(p_Title, p_Description));
    }
    
    public static NotificationManager Get() {
        return Pyro.GetNotificationManager();
    }
    
    public class Notification
    {
        private String Title;
        private String Description;
        private Timer timer;
        private Timer DecayTimer;
        private int DecayTime;
        private int X;
        private int Y;
        
        public Notification(final String p_Title, final String p_Description) {
            this.timer = new Timer();
            this.DecayTimer = new Timer();
            this.Title = p_Title;
            this.Description = p_Description;
            this.DecayTime = 2500;
            this.timer.reset();
            this.DecayTimer.reset();
        }
        
        public void OnRender() {
            if (this.timer.passed(this.DecayTime - 500)) {
                --this.Y;
            }
        }
        
        public boolean IsDecayed() {
            return this.DecayTimer.passed(this.DecayTime);
        }
        
        public String GetDescription() {
            return this.Description;
        }
        
        public String GetTitle() {
            return this.Title;
        }
        
        public int GetX() {
            return this.X;
        }
        
        public void SetX(final int x) {
            this.X = x;
        }
        
        public int GetY() {
            return this.Y;
        }
        
        public void SetY(final int y) {
            this.Y = y;
        }
    }
}
