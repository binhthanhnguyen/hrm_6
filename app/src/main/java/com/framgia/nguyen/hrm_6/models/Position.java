package com.framgia.nguyen.hrm_6.models;

/**
 * Created by nguyen on 2/23/16.
 */
public enum Position {
    DEV(0, "Developer"),
    BRSE(1, "BRSE"),
    BO(2, "Back Office"),
    MANAGER(3, "Manager");
    private int mCode;
    private String mDesc;

    Position(int code, String desc){
        this.mCode = code;
        this.mDesc = desc;
    }

    public int code(){
        return mCode;
    }

    public String desc(){
        return mDesc;
    }

    public static Position parsePosition(int code){
        for (Position position: Position.values()) {
            if (position.mCode == code)
                return position;
        }
        return null;
    }

    @Override
    public String toString() {
        return mDesc;
    }
}
