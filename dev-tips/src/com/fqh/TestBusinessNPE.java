package com.fqh;

import com.fqh.utils.OptionalUtil;

import java.util.Date;

/**
 * @author ikun
 * @version v1.0.0
 * @since 2024/2/20 23:20
 **/
public class TestBusinessNPE {

    public static void main(String[] args) {
        PII pii = new PII();

        PII res = new PII();
        res.setCreateTime(OptionalUtil.orNull(pii::getCreateTime));
        res.setAmount(OptionalUtil.or(() -> pii.amount, 0L));
        System.out.println(res);
    }


    static class PII {
        private Date createTime;
        private Long amount;

        public PII() {}

        public PII(Date createTime, Long amount) {
            this.createTime = createTime;
            this.amount = amount;
        }

        public Date getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Date createTime) {
            this.createTime = createTime;
        }

        public Long getAmount() {
            return amount;
        }

        public void setAmount(Long amount) {
            this.amount = amount;
        }

        @Override
        public String toString() {
            return "PII{" +
                    "createTime=" + createTime +
                    ", amount=" + amount +
                    '}';
        }
    }
}
