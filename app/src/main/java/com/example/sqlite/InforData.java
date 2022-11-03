package com.example.sqlite;

/**
 * class dùng chung để đọc dữ liệu hiển thị lên ListView Sách và tác giả
 * có thể bỏ class này viết class khác
 * Ở đây viết 1 Class có các kiểu Object
 * để nó tự hiểu mọi kiểu dữ liệu đỡ phải viết lại
 */
public class InforData {
    private Object field1;
    private Object field2;
    private Object field3;

    public Object getField1() {
        return field1;
    }

    public void setField1(Object field1) {
        this.field1 = field1;
    }

    public Object getField2() {
        return field2;
    }

    public void setField2(Object field2) {
        this.field2 = field2;
    }

    public Object getField3() {
        return field3;
    }

    public void setField3(Object field3) {
        this.field3 = field3;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return this.field1 + " - " + this.field2 + " - " + this.field3;
    }
}
