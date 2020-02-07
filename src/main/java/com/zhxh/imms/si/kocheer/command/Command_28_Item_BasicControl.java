package com.zhxh.imms.si.kocheer.command;

public class Command_28_Item_BasicControl implements Command_28_Item {
    protected int keyLock = 128;
    protected int keyMode = 128;  //键盘模式

    protected boolean useLamp_1;  //黄灯
    protected int lampMode_1;
    protected int lampValue_1;

    protected boolean useLamp_2; //绿灯
    protected int lampMode_2;
    protected int lampValue_2;

    protected boolean useLamp_3;  //红灯
    protected int lampMode_3;
    protected int lampValue_3;

    public boolean useRelay_1;
    public int relayMode_1;
    public int relayValue_1;

    public boolean useRelay_2;
    public int relayMode_2;
    public int relayValue_2;

    public boolean useRelay_3;
    public int relayMode_3;
    public int relayValue_3;

    protected int soundRepeat;
    protected int soundInterval;

    public void singleKey(){
        this.keyMode = KEY_MODE_SINGLE;
    }

    public void multiKey(){
        this.keyMode = KEY_MODE_MULTI;
    }

    public void lightRed(int keep) {
        this.useLamp_3 = true;
        this.lampMode_3 = LAMP_MODEL_DELAY_CLOSE;
        this.lampValue_3 = keep;
    }

    public void closeRed() {
        this.useLamp_3 = true;
        this.lampMode_3 = LAMP_MODE_CLOSE;
        this.lampValue_3 = 0;
    }

    public void lightGreen(int keep) {
        this.useLamp_2 = true;
        this.lampMode_2 = LAMP_MODEL_DELAY_CLOSE;
        this.lampValue_2 = keep;
    }

    public void closeGreen() {
        this.useLamp_2 = true;
        this.lampMode_2 = LAMP_MODE_CLOSE;
        this.lampValue_2 = 0;
    }

    public void lightYellow(int keep) {
        this.useLamp_1 = true;
        this.lampMode_1 = LAMP_MODEL_DELAY_CLOSE;
        this.lampValue_1 = keep;
    }

    public void closeYellow() {
        this.useLamp_1 = true;
        this.lampMode_1 = LAMP_MODE_CLOSE;
        this.lampValue_1 = 0;
    }

    public void sound(int repeat,int interval){
        this.soundRepeat = repeat;
        this.soundInterval = interval;
    }

    @Override
    public String toString() {
        return "Command_28_Item_BasicControl{" +
                "keyLock=" + keyLock +
                ", keyMode=" + keyMode +
                ", useLamp_1=" + useLamp_1 +
                ", lampMode_1=" + lampMode_1 +
                ", lampValue_1=" + lampValue_1 +
                ", useLamp_2=" + useLamp_2 +
                ", lampMode_2=" + lampMode_2 +
                ", lampValue_2=" + lampValue_2 +
                ", useLamp_3=" + useLamp_3 +
                ", lampMode_3=" + lampMode_3 +
                ", lampValue_3=" + lampValue_3 +
                ", useRelay_1=" + useRelay_1 +
                ", relayMode_1=" + relayMode_1 +
                ", relayValue_1=" + relayValue_1 +
                ", useRelay_2=" + useRelay_2 +
                ", relayMode_2=" + relayMode_2 +
                ", relayValue_2=" + relayValue_2 +
                ", useRelay_3=" + useRelay_3 +
                ", relayMode_3=" + relayMode_3 +
                ", relayValue_3=" + relayValue_3 +
                ", soundRepeat=" + soundRepeat +
                ", soundInterval=" + soundInterval +
                '}';
    }

    @Override
    public int toDeviceItemString(StringBuilder lineBuilder) {
        //键盘
        lineBuilder.append("|210");
        lineBuilder.append("|").append(keyLock).append("|").append(keyMode);

        //灯
        if (this.useLamp_3) {
            lineBuilder.append("|3|").append(lampMode_3).append("|").append(lampValue_3);
        } else {
            lineBuilder.append("|0|0|0");
        }

        if (this.useLamp_2) {
            lineBuilder.append("|2|").append(lampMode_2).append("|").append(lampValue_2);
        } else {
            lineBuilder.append("|0|0|0");
        }

        if (this.useLamp_1) {
            lineBuilder.append("|1|").append(lampMode_1).append("|").append(lampValue_1);
        } else {
            lineBuilder.append("|0|0|0");
        }

        //继电器
        lineBuilder.append("|0|0|0");
        lineBuilder.append("|0|0|0");
        lineBuilder.append("|0|0|0");

        lineBuilder.append("|").append(soundRepeat).append("|").append(soundInterval);

        return 1;
    }

    public final static int KEY_MODE_SINGLE = 128;
    public final static int KEY_MODE_MULTI = 129;

    public final static int LAMP_MODE_NO_ACTION = 0;
    public final static int LAMP_MODE_OPEN = 1;
    public final static int LAMP_MODE_CLOSE = 2;
    public final static int LAMP_MODEL_DELAY_CLOSE = 3;
    public final static int LAMP_MODEL_SPLASH = 4;
}
