package com.framgia.nguyen.hrm_6.models;

/**
 * Created by nguyen on 2/23/16.
 */
public enum Status {
    TRAINEE(0, "Trainee"),
    INTERN(1, "Internship"),
    OFFICIAL_STAFF(2, "Official Staff");
    private int mCode;
    private String mDesc;

    Status(int code, String desc){
        this.mCode = code;
        this.mDesc = desc;
    }

    public int code(){
        return mCode;
    }

    public String desc(){
        return mDesc;
    }

    public static Status parseStatus(int code){
        for (Status status: Status.values()){
            if(status.mCode == code)
                return status;
        }
        return null;
    }

    @Override
    public String toString() {
        return mDesc;
    }
}
