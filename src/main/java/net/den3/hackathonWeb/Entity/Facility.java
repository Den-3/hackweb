package net.den3.hackathonWeb.Entity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Facility {
    A("生協"),
    B("食堂"),
    C("2号館"),
    D("3号館"),
    E("4号館"),
    F("5号館"),
    G("6号館"),
    H("8号館"),
    I("図書館"),
    J("視聴覚室"),
    K("斎藤記念館"),
    L("大学会館"),
    M("先端工学研究機構棟"),
    N("体育館"),
    O("第2体育館"),
    P("総合グラウンド"),
    Q("第2グラウンド"),
    R("テニスコート"),
    S("多目的コート"),
    T("弓道場"),
    U("アーチェリー場"),
    V("グリーンコート"),
    W("国際学生寮"),
    X("第1学生クラブハウス"),
    Y("第2学生クラブハウス1階"),
    Z("第2学生クラブハウス2階"),
    AA("第3学生クラブハウス");
    
    String mes;
    public static final List<String> facilitiesString = Stream.of(Facility.values()).map(Facility::getCode).collect(Collectors.toList());

    public static final List<Facility> facilities = Arrays.asList(Facility.values());

    Facility(String mes){
        this.mes = mes;
    }

    public String getCode() {
        return this.mes;
    }

    public static Optional<Facility> getFacility(String mes){
        for (Facility f : Facility.values()) {
            if (f.mes.equals(mes)) {
                return Optional.of(f);
            }
        }
        return Optional.empty();
    }
}
