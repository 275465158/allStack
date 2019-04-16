package com.ecms.bean;

import com.ecms.common.Pager;
import com.ecms.common.SellerOrderProgress;

import java.util.ArrayList;
import java.util.List;

public class SellerOrderTable {

    private static List<Progress> progresses;
    private Pager<SellerOrderInfo> pager;

    static {
        progresses = new ArrayList<>();
        for (SellerOrderProgress progress : SellerOrderProgress.values()) {
            Progress p = new Progress();
            p.setId(progress.getId());
            p.setName(progress.getName());
            progresses.add(p);
        }
    }

    public List<Progress> getProgresses() {
        return progresses;
    }

    public void setProgresses(List<Progress> progresses) {
        SellerOrderTable.progresses = progresses;
    }

    public Pager<SellerOrderInfo> getPager() {
        return pager;
    }

    public void setPager(Pager<SellerOrderInfo> pager) {
        this.pager = pager;
    }

    static class Progress {
        int id;
        String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
